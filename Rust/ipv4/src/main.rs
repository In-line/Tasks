#![feature(bool_to_option)]

use std::env;

fn main() {
    let ip = "1.2.3.4"; //env::args().skip(1).next().unwrap();

    let is_ip_valid = ip
        .split('.')
        .map(|s| {
            s.parse::<u8>()
                .ok()
                .and_then(|i| (i.to_string() == s).then_some(()))
                .ok_or(())
        })
        .try_fold(0, |acc, i| {
            i?;
            (acc <= 4).then_some(acc + 1).ok_or(())
        })
        .is_ok();

    println!("{:#?}", is_ip_valid);
}
