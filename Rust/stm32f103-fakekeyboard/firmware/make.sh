cargo build --release
arm-none-eabi-objcopy -O binary target/thumbv7m-none-eabi/release/firmware badkbd.bin

while st-flash write badkbd.bin 0x8000000; do
  sleep 0.5
done
st-flash reset
# rm badkbd.bin
