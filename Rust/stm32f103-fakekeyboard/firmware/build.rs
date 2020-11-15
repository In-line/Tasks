use library::commands::ParseStrExt;

fn main() {
    include_str!("payload.txt").to_parsed_commands_iterator().unwrap().for_each(|_| {});
}