#![no_std]
#![no_main]
#![feature(alloc_error_handler)]
#![allow(clippy::option_map_unit_fn)]

extern crate panic_halt;

use core::iter::Peekable;
use cortex_m::{asm::delay, peripheral::DWT};
use embedded_hal::digital::v2::OutputPin;
use stm32f1xx_hal::usb::{Peripheral, UsbBus, UsbBusType};
use stm32f1xx_hal::{gpio, prelude::*};

use usb_device::bus;
use usb_device::prelude::*;

use alloc_cortex_m::CortexMHeap;
use library::commands::{Command, ParseStrExt, ParsedCommandsIterator};

#[global_allocator]
static ALLOCATOR: CortexMHeap = CortexMHeap::empty();

pub mod hid {
    use library::consts::*;
    use library::keys::*;

    use usb_device::class_prelude::*;
    use usb_device::control::Request;
    use usb_device::Result;

    pub const USB_CLASS_HID: u8 = 0x03;

    pub fn report(key: UsbKey) -> [u8; 8] {
        [
            key.modifiers.bits(), // mods
            0x00,                 // reserverd
            *key.codes.get(0).unwrap_or(&KeyCode::default()) as u8,
            *key.codes.get(1).unwrap_or(&KeyCode::default()) as u8,
            *key.codes.get(2).unwrap_or(&KeyCode::default()) as u8,
            *key.codes.get(3).unwrap_or(&KeyCode::default()) as u8,
            *key.codes.get(4).unwrap_or(&KeyCode::default()) as u8,
            *key.codes.get(5).unwrap_or(&KeyCode::default()) as u8,
        ]
    }

    pub struct HIDClass<'a, B: UsbBus> {
        report_if: InterfaceNumber,
        report_ep: EndpointIn<'a, B>,
    }

    impl<B: UsbBus> HIDClass<'_, B> {
        /// Creates a new HIDClass with the provided UsbBus and max_packet_size in bytes. For
        /// full-speed devices, max_packet_size has to be one of 8, 16, 32 or 64.
        pub fn new(alloc: &UsbBusAllocator<B>) -> HIDClass<'_, B> {
            HIDClass {
                report_if: alloc.interface(),
                report_ep: alloc.interrupt(8, 10),
            }
        }

        pub fn write_key(&mut self, key: UsbKey) -> Result<()> {
            self.report_ep.write(&report(key)).map(|_| ())
        }

        pub fn write_key_release(&mut self) -> Result<()> {
            self.write_key(UsbKey::default())
        }

        fn check_is_request_right(&self, req: &Request) -> bool {
            req.request_type == control::RequestType::Class
                && req.recipient == control::Recipient::Interface
                && req.index == u8::from(self.report_if) as u16
        }
    }

    impl<B: UsbBus> UsbClass<B> for HIDClass<'_, B> {
        fn get_configuration_descriptors(&self, writer: &mut DescriptorWriter) -> Result<()> {
            writer.interface(
                self.report_if,
                USB_CLASS_HID,
                USB_SUBCLASS_NONE,
                USB_INTERFACE_KEYBOARD, // USB_INTERFACE_MOUSE,
            )?;

            let descr_len: u16 = REPORT_DESCR.len() as u16;
            writer.write(
                0x21,
                &[
                    0x01,                   // bcdHID
                    0x01,                   // bcdHID
                    0x00,                   // bContryCode
                    0x01,                   // bNumDescriptors
                    0x22,                   // bDescriptorType
                    descr_len as u8,        // wDescriptorLength
                    (descr_len >> 8) as u8, // wDescriptorLength
                ],
            )?;

            writer.endpoint(&self.report_ep)?;

            Ok(())
        }

        fn control_out(&mut self, xfer: ControlOut<B>) {
            if !self.check_is_request_right(&xfer.request()) {
                xfer.reject().ok();
            }
        }

        fn control_in(&mut self, xfer: ControlIn<B>) {
            let req = xfer.request();

            if req.request_type == control::RequestType::Standard {
                match (req.recipient, req.request) {
                    (control::Recipient::Interface, control::Request::GET_DESCRIPTOR) => {
                        let (dtype, _index) = req.descriptor_type_index();
                        if dtype == 0x21 {
                            // HID descriptor
                            cortex_m::asm::bkpt();
                            let descr_len: u16 = REPORT_DESCR.len() as u16;

                            // HID descriptor
                            let descr = &[
                                0x09,                   // length
                                0x21,                   // descriptor type
                                0x01,                   // bcdHID
                                0x01,                   // bcdHID
                                0x00,                   // bCountryCode
                                0x01,                   // bNumDescriptors
                                0x22,                   // bDescriptorType
                                descr_len as u8,        // wDescriptorLength
                                (descr_len >> 8) as u8, // wDescriptorLength
                            ];

                            xfer.accept_with(descr).ok();
                            return;
                        } else if dtype == 0x22 {
                            // Report descriptor
                            xfer.accept_with(REPORT_DESCR).ok();
                            return;
                        }
                    }
                    _ => {
                        return;
                    }
                };
            }

            if self.check_is_request_right(&req) {
                match req.request {
                    REQ_GET_REPORT => {
                        // USB host requests for report
                        // I'm not sure what should we do here, so just send empty report
                        xfer.accept_with(&report(UsbKey::default())).ok();
                    }
                    _ => {
                        xfer.reject().ok();
                    }
                }
            }
        }
    }
}

use hid::HIDClass;
use rtfm::cyccnt::U32Ext;

type LED = gpio::gpioc::PC13<gpio::Output<gpio::PushPull>>;

const ONE_SECOND: u32 = 48_000_000;
const STARTUP_DELAY: u32 = ONE_SECOND;
const TYPING_DELAY: u32 = 0;

#[rtfm::app(device = stm32f1xx_hal::stm32, peripherals = true, monotonic = rtfm::cyccnt::CYCCNT)]
const APP: () = {
    struct Resources {
        #[init(false)]
        is_key_pressed: bool,
        command_iterator: Peekable<ParsedCommandsIterator<'static>>,

        led: LED,
        usb_dev: UsbDevice<'static, UsbBusType>,
        hid: HIDClass<'static, UsbBusType>,
    }

    #[init(schedule = [on_tick])]
    fn init(mut cx: init::Context) -> init::LateResources {
        static mut USB_BUS: Option<bus::UsbBusAllocator<UsbBusType>> = None;

        unsafe { ALLOCATOR.init(cortex_m_rt::heap_start() as usize, 4 * 1024) }

        cx.core.DCB.enable_trace();
        DWT::unlock();
        cx.core.DWT.enable_cycle_counter();

        let mut flash = cx.device.FLASH.constrain();
        let mut rcc = cx.device.RCC.constrain();

        let mut gpioc = cx.device.GPIOC.split(&mut rcc.apb2);
        let led = gpioc.pc13.into_push_pull_output(&mut gpioc.crh);

        let clocks = rcc
            .cfgr
            .use_hse(8.mhz())
            .sysclk(48.mhz())
            .pclk1(24.mhz())
            .freeze(&mut flash.acr);

        assert!(clocks.usbclk_valid());

        let mut gpioa = cx.device.GPIOA.split(&mut rcc.apb2);

        // BluePill board has a pull-up resistor on the D+ line.
        // Pull the D+ pin down to send a RESET condition to the USB bus.
        let mut usb_dp = gpioa.pa12.into_push_pull_output(&mut gpioa.crh);
        usb_dp.set_low().ok();
        delay(clocks.sysclk().0 / 100);

        let usb_dm = gpioa.pa11;
        let usb_dp = usb_dp.into_floating_input(&mut gpioa.crh);

        let usb = Peripheral {
            usb: cx.device.USB,
            pin_dm: usb_dm,
            pin_dp: usb_dp,
        };

        *USB_BUS = Some(UsbBus::new(usb));

        let hid = HIDClass::new(USB_BUS.as_ref().unwrap());

        let usb_dev = UsbDeviceBuilder::new(USB_BUS.as_ref().unwrap(), UsbVidPid(0xc410, 0x0000))
            .manufacturer("ATMK")
            .product("BadUSB")
            .serial_number("TEST")
            .device_class(0)
            .build();

        cx.schedule.on_tick(cx.start + STARTUP_DELAY.cycles()).ok();

        init::LateResources {
            command_iterator: "test".to_parsed_commands_iterator().unwrap().peekable(),
            led,
            usb_dev,
            hid,
        }
    }

    #[task(resources = [command_iterator, is_key_pressed, hid, led], schedule = [on_tick])]
    fn on_tick(cx: on_tick::Context) {
        let on_tick::Resources {
            command_iterator,
            is_key_pressed,
            hid,
            led,
        } = cx.resources;

        let on_tick::Context {
            schedule,
            scheduled,
            ..
        } = cx;

        if !*is_key_pressed {
            led.set_high().ok();

            command_iterator
                .peek()
                .cloned()
                .map(|command| match command {
                    Command::Key(key) => {
                        hid.write_key(key).ok().map(|_| {
                            *is_key_pressed = true;
                            command_iterator.next();
                        });

                        schedule.on_tick(scheduled + TYPING_DELAY.cycles()).ok();
                    }
                    Command::DelayMs(msecs) => {
                        schedule
                            .on_tick(scheduled + (ONE_SECOND / 1000 * msecs).cycles())
                            .ok();
                    }
                });
        } else {
            led.set_low().ok();
            hid.write_key_release()
                .map(|_| {
                    *is_key_pressed = false;
                })
                .ok();
        }
    }

    #[task(binds=USB_HP_CAN_TX, resources = [usb_dev, hid])]
    fn usb_tx(cx: usb_tx::Context) {
        cx.resources.usb_dev.poll(&mut [cx.resources.hid]);
    }

    #[task(binds=USB_LP_CAN_RX0, resources = [usb_dev, hid])]
    fn usb_rx(cx: usb_rx::Context) {
        cx.resources.usb_dev.poll(&mut [cx.resources.hid]);
    }

    extern "C" {
        fn EXTI0();
    }
};

#[alloc_error_handler]
fn oom(_: core::alloc::Layout) -> ! {
    panic!("Allocation failed")
}
