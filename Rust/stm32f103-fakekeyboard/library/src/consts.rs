#![allow(dead_code)]

pub const USB_SUBCLASS_NONE: u8 = 0x00;
pub const USB_SUBCLASS_BOOT: u8 = 0x01;

pub const USB_INTERFACE_NONE: u8 = 0x00;
pub const USB_INTERFACE_KEYBOARD: u8 = 0x01;
pub const USB_INTERFACE_MOUSE: u8 = 0x02;

pub const REQ_GET_REPORT: u8 = 0x01;
pub const REQ_GET_IDLE: u8 = 0x02;
pub const REQ_GET_PROTOCOL: u8 = 0x03;
pub const REQ_SET_REPORT: u8 = 0x09;
pub const REQ_SET_IDLE: u8 = 0x0a;
pub const REQ_SET_PROTOCOL: u8 = 0x0b;

// https://docs.microsoft.com/en-us/windows-hardware/design/component-guidelines/mouse-collection-report-descriptor
pub const REPORT_DESCR: &[u8] = &[
    0x05, 0x01, // Usage Page (Generic Desktop Ctrls)
    0x09, 0x06, // Usage (Keyboard)
    0xA1, 0x01, // Collection (Application)
    0x05, 0x07, //   Usage Page (Kbrd/Keypad)
    0x19, 0xE0, //   Usage Minimum (0xE0)
    0x29, 0xE7, //   Usage Maximum (0xE7)
    0x15, 0x00, //   Logical Minimum (0)
    0x25, 0x01, //   Logical Maximum (1)
    0x75, 0x01, //   Report Size (1)
    0x95, 0x08, //   Report Count (8)
    0x81, 0x02, //   Input (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position)
    0x95, 0x01, //   Report Count (1)
    0x75, 0x08, //   Report Size (8)
    0x81, 0x01, //   Input (Const,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
    0x95, 0x03, //   Report Count (3)
    0x75, 0x01, //   Report Size (1)
    0x05, 0x08, //   Usage Page (LEDs)
    0x19, 0x01, //   Usage Minimum (Num Lock)
    0x29, 0x03, //   Usage Maximum (Scroll Lock)
    0x91,
    0x02, //   Output (Data,Var,Abs,No Wrap,Linear,Preferred State,No Null Position,Non-volatile)
    0x95, 0x05, //   Report Count (5)
    0x75, 0x01, //   Report Size (1)
    0x91,
    0x01, //   Output (Const,Array,Abs,No Wrap,Linear,Preferred State,No Null Position,Non-volatile)
    0x95, 0x06, //   Report Count (6)
    0x75, 0x08, //   Report Size (8)
    0x15, 0x00, //   Logical Minimum (0)
    0x26, 0xFF, 0x00, //   Logical Maximum (255)
    0x05, 0x07, //   Usage Page (Kbrd/Keypad)
    0x19, 0x00, //   Usage Minimum (0x00)
    0x2A, 0xFF, 0x00, //   Usage Maximum (0xFF)
    0x81, 0x00, //   Input (Data,Array,Abs,No Wrap,Linear,Preferred State,No Null Position)
    0xC0, // End Collection

          // 65 bytes
];