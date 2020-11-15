/*
{Win+R}
{Delay 1.5s}
cmd.exe
{Delay 1.5s}
{{
}}
kaspersky-install key fdklfdkfjdkfsdfjlsfjlsfjslfjslfjslffjlsfjls
*/

use ascii::ToAsciiChar;
use derive_new::new as Constructor;
use nom::branch::alt;
use nom::bytes::complete::{tag, tag_no_case, take, take_until, take_while1};
use nom::character::complete::{alphanumeric1, multispace0};
use nom::combinator::{map, opt, recognize};
use nom::multi::{many0, many1};
use nom::sequence::delimited;
use nom::IResult;

use crate::keys::{Modifiers, UsbKey};
use alloc::vec::Vec;
use core::convert::TryFrom;
use nom_locate::LocatedSpan;
use nom::character::is_digit;

pub type Span<'a> = LocatedSpan<&'a str>;

// #[derive(Snafu)]
// enum Error<'a> {
//     #[snafu(display("Missing token: {} ({})", missing_token, source2))]
//     MissingToken {
//         missing_token: &'static str,
//         token: Span<'a>,
//         source2: std::io::Error,
//     }
// }

#[derive(Clone, Eq, PartialEq, Constructor)]
#[derive(Debug)]
pub enum TokenKind<'a> {
    Text(&'a str),
    DelayMs(u32),
    Key(UsbKey),
}

#[derive(Clone, Constructor, Eq, PartialEq)]
#[derive(Debug)]
pub struct Token<'a> {
    pub span: Span<'a>,
    pub kind: TokenKind<'a>,
}

impl<'a> Token<'a> {
    fn new_text(span: Span<'a>) -> Token<'a> {
        Token::new(span, TokenKind::new_text(*span))
    }

    fn new_key(span: Span<'a>, key: UsbKey) -> Token<'a> {
        Token::new(span, TokenKind::new_key(key))
    }

    fn new_delay_ms(span: Span<'a>, delay: u32) -> Token<'a> {
        Token::new(span, TokenKind::new_delay_ms(delay))
    }
}

fn parse_usb_key(s: Span<'_>) -> IResult<Span, UsbKey> {
    let parse_separator = delimited(
        multispace0,
        alt((tag("+"), tag("-"), tag(","), tag("_"))),
        multispace0,
    );

    fn parse_one_character(s: Span<'_>) -> IResult<Span, Span> {
        let (s, data) = take(1u32)(s)?;
        let (_, _) = alphanumeric1(data)?;
        Ok((s, data))
    }

    let parse_one_token = alt((
        tag_no_case("Ctrl"),
        tag_no_case("Alt"),
        tag_no_case("Shift"),
        tag_no_case("Meta"),
        tag_no_case("Win"),
        parse_one_character,
    ));

    let parse_delimited = |token| many1(delimited(multispace0, token, opt(parse_separator)));

    let (s, tokens) = parse_delimited(parse_one_token)(s)?;

    let modifiers = tokens.iter().fold(Modifiers::empty(), |modifiers, token| {
        match *token.fragment() {
            "Ctrl" => modifiers | Modifiers::LEFT_CTRL,
            "Alt" => modifiers | Modifiers::LEFT_ALT,
            "Shift" => modifiers | Modifiers::LEFT_SHIFT,
            "Meta" => modifiers | Modifiers::LEFT_META,
            "Win" => modifiers | Modifiers::LEFT_META,
            _ => modifiers,
        }
    });

    let key = tokens
        .into_iter()
        .filter(|token| token.fragment().len() == 1)
        .filter_map(|token| {
            token.fragment().as_bytes()[0]
                .to_ascii_char()
                .ok()
                .and_then(|character| UsbKey::try_from(character).ok())
        })
        .fold(UsbKey::with_modifier([], modifiers), |mut acc, key| {
            acc.append_codes(key.codes).append_modifiers(key.modifiers);
            acc
        });

    // TODO: Handle single Ctrl, Win, etc..

    Ok((s, key))
}

fn parse_delay_ms(s: Span<'_>) -> IResult<Span, u32> {
    let (s, _) = multispace0(s)?;
    let (s, _) = tag_no_case("Delay")(s)?;

    let (s, _) = multispace0(s)?;
    let (s, _) = opt(tag(":"))(s)?;

    let (s, _) = multispace0(s)?;
    let (s, delay) = recognize(take_while1(|c| is_digit(c as u8)))(s)?;

    let (s, _) = multispace0(s)?;

    let (s, suffix) = opt(tag("s"))(s)?;
    let (s, _) = multispace0(s)?;

    let delay = delay.parse::<u32>().unwrap();
    if suffix.is_some() {
        Ok((s, delay * 1000))
    } else {
        Ok((s, delay))
    }
}

fn parse_special_string(s: Span<'_>) -> IResult<Span, Span> {
    let (s, _) = tag("{")(s)?;
    let (s, data) = take_until("}")(s)?;
    let (s, _) = tag("}")(s)?;

    Ok((s, data))
}

fn parse_special_tokens(s: Span) -> IResult<Span, Token> {
    let (s, data) = parse_special_string(s)?;
    let (_, token) = alt((
        |s| {
            let (s, delay) = parse_delay_ms(s)?;
            Ok((s, Token::new_delay_ms(data, delay)))
        },
        |s| {
            let (s, key) = parse_usb_key(s)?;
            Ok((s, Token::new_key(data, key)))
        },
    ))(data)?;

    Ok((s, token))
}

fn parse_escaped_brackets(s: Span<'_>) -> IResult<Span, Token> {
    let (s, bracket) = alt((tag("{{"), tag("}}")))(s)?;

    let (_, first) = take(1u32)(bracket)?;

    Ok((s, Token::new_text(first)))
}

fn parse_text(s: Span) -> IResult<Span, Token> {
    map(take_while1(|c| c != '{' && c != '}'), Token::new_text)(s)
}

pub fn parse(input: Span<'_>) -> IResult<Span, Vec<Token>> {
    many0(alt((
        // Parsing of escaped brackets should take more priority,
        // Because special token parsing will consume everything if it sees { or }
        parse_escaped_brackets,
        // Next try to parse special tokens
        parse_special_tokens,
        // Next special token should start with { or }, anything before is just text
        parse_text,
    )))(input)
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::keys::KeyCode;

    #[test]
    fn test_parse_special_string() {
        unsafe {
            assert_eq!(
                parse_special_string(Span::new("{Ctrl+C} blabla")).unwrap(),
                (
                    Span::new_from_raw_offset(8, 1, " blabla", ()),
                    Span::new_from_raw_offset(1, 1, "Ctrl+C", ()),
                )
            );

            assert_eq!(
                parse_special_string(Span::new("{}")).unwrap().0.fragment(),
                &""
            );

            assert!(parse_special_string(Span::new("{Ctrl+C]")).is_err(),);

            assert!(parse_special_string(Span::new("Ctrl+C}")).is_err(),);

            assert_eq!(
                parse_special_string(Span::new("{{}")).unwrap(),
                (
                    Span::new_from_raw_offset(3, 1, "", ()),
                    Span::new_from_raw_offset(1, 1, "{", ()),
                )
            );
        }
    }

    #[test]
    fn test_parse_escaped_brackets() {
        unsafe {
            assert_eq!(
                parse_escaped_brackets(Span::new("{{")).unwrap(),
                (
                    Span::new_from_raw_offset(2, 1, "", ()),
                    Token::new_text(Span::new_from_raw_offset(0, 1, "{", ())),
                )
            );

            assert_eq!(
                parse_escaped_brackets(Span::new("}}")).unwrap(),
                (
                    Span::new_from_raw_offset(2, 1, "", ()),
                    Token::new_text(Span::new_from_raw_offset(0, 1, "}", ())),
                )
            );
        }

        assert!(parse_escaped_brackets(Span::new("}")).is_err());
        assert!(parse_escaped_brackets(Span::new("{")).is_err());
        assert!(parse_escaped_brackets(Span::new("{}")).is_err());
        assert!(parse_escaped_brackets(Span::new("}{")).is_err());
    }

    #[test]
    fn test_parse() {
        unsafe {
            assert_eq!(
                parse(Span::new("{Ctrl+C}{{bd")).unwrap(),
                (
                    Span::new_from_raw_offset(12, 1, "", ()),
                    vec![
                        Token::new(
                            Span::new_from_raw_offset(1, 1, "Ctrl+C", ()),
                            TokenKind::Key(UsbKey::with_modifier(
                                [KeyCode::C],
                                Modifiers::LEFT_CTRL | Modifiers::LEFT_SHIFT
                            ))
                        ),
                        Token::new(
                            Span::new_from_raw_offset(8, 1, "{", ()),
                            TokenKind::Text("{")
                        ),
                        Token::new(
                            Span::new_from_raw_offset(10, 1, "bd", ()),
                            TokenKind::Text("bd")
                        )
                    ]
                )
            );
        }
    }

    #[test]
    fn test_parse_usb_key() {
        unsafe {
            assert_eq!(
                parse_usb_key(Span::new("Ctrl + C + Shift")).unwrap(),
                (
                    Span::new_from_raw_offset(16, 1, "", ()),
                    UsbKey::with_modifier(
                        [KeyCode::C],
                        Modifiers::LEFT_CTRL | Modifiers::LEFT_SHIFT
                    )
                )
            );
            assert!(parse_usb_key(Span::new("")).is_err());

            assert_eq!(
                parse_usb_key(Span::new("bd")).unwrap(),
                (
                    Span::new_from_raw_offset(2, 1, "", ()),
                    UsbKey::new([KeyCode::B, KeyCode::D],)
                )
            );
        }
    }

    #[test]
    fn test_parse_delay_ms() {
        unsafe {
            assert_eq!(
                parse_delay_ms(Span::new("Delay :  32 ")).unwrap(),
                (Span::new_from_raw_offset(12, 1, "", ()), 32)
            );

            assert_eq!(
                parse_delay_ms(Span::new("Delay :  32s ")).unwrap(),
                (Span::new_from_raw_offset(13, 1, "", ()), 32000)
            );

            // TODO: Floating support bloats size up by ~20 KB
            // assert_eq!(
            //     parse_delay_ms(Span::new("Delay :  32.5s ")).unwrap(),
            //     (Span::new_from_raw_offset(15, 1, "", ()), 32500)
            // );

            // TODO: Fix this
            // parse_delay_ms(Span::new("Delay :  32.5m ")).unwrap_err();
        }
    }

    #[test]
    fn test_parse_special_tokens() {
        unsafe {
            assert_eq!(
                parse_special_tokens(Span::new("{Ctrl + C + Shift}")).unwrap(),
                (
                    Span::new_from_raw_offset(18, 1, "", ()),
                    Token::new_key(
                        Span::new_from_raw_offset(1, 1, "Ctrl + C + Shift", ()),
                        UsbKey::with_modifier(
                            [KeyCode::C],
                            Modifiers::LEFT_CTRL | Modifiers::LEFT_SHIFT
                        )
                    )
                )
            );

            assert_eq!(
                parse_special_tokens(Span::new("{Delay :  1321 }")).unwrap(),
                (
                    Span::new_from_raw_offset(16, 1, "", ()),
                    Token::new_delay_ms(
                        Span::new_from_raw_offset(1, 1, "Delay :  1321 ", ()),
                        1321
                    )
                )
            );
        }
    }
}
