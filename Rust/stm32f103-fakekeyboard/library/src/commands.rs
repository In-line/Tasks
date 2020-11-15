use crate::keys::{StrExt, UsbKey};
use crate::parser::{parse, Span, TokenKind};
use alloc::boxed::Box;
use core::iter::once;
use nom::Err;

pub type ParsedCommandsIterator<'a> = Box<dyn Iterator<Item = Command> + Send + 'a>;

#[derive(Clone, Debug)]
pub enum Command {
    DelayMs(u32),
    Key(UsbKey),
}

pub trait ParseStrExt {
    fn to_parsed_commands_iterator(&self) -> Result<ParsedCommandsIterator, Span>;
}

impl ParseStrExt for str {
    fn to_parsed_commands_iterator(&self) -> Result<ParsedCommandsIterator, Span> {
        let (remaining, tokens) = parse(Span::new(self)).map_err(|err| match err {
            Err::Incomplete(_) => unreachable!(),
            Err::Error((s, _)) => s,
            Err::Failure((s, _)) => s,
        })?;

        if remaining.len() > 0 {
            return Err(remaining);
        }

        Ok(tokens
            .into_iter()
            .map(|token| match token.kind {
                TokenKind::Key(key) => Box::new(once(Command::Key(key))) as ParsedCommandsIterator,
                TokenKind::Text(text) => Box::new(text.to_usb_keys_iter_lossy().map(Command::Key))
                    as ParsedCommandsIterator,
                TokenKind::DelayMs(delay) => {
                    Box::new(once(Command::DelayMs(delay))) as ParsedCommandsIterator
                }
            })
            .flatten())
        .map(|x| Box::new(x) as ParsedCommandsIterator)
    }
}
