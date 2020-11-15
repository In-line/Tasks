use arrayvec::{Array, ArrayVec};
use ascii::{AsciiChar, ToAsciiChar};
use bitflags::*;
use core::convert::TryFrom;
use derive_new::new as Constructor;

bitflags! {
    pub struct Modifiers: u8 {
        const NONE = 0x0;
        const LEFT_CTRL = 0x01;
        const LEFT_SHIFT = 0x02;
        const LEFT_ALT = 0x04;
        const LEFT_META = 0x08;
        const RIGHT_CTRL = 0x10;
        const RIGHT_SHIFT = 0x20;
        const RIGHT_ALT = 0x40;
        const RIGHT_META = 0x80;
    }
}

impl Default for Modifiers {
    fn default() -> Self {
        Modifiers::NONE
    }
}

#[repr(u8)]
#[derive(Clone, Copy, Eq, PartialEq, Debug)]
pub enum KeyCode {
    Null, // Reserved
    ErrorRollOver,
    PostFail,
    ErrorUndefined,
    A, // 4
    B,
    C,
    D,
    E,
    F,
    G,
    H, //11
    I,
    J,
    K,
    L,
    M, // 0x10
    N,
    O,
    P,
    Q, //20
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,   //29
    Kb1, // Keyboard 1 30
    Kb2,
    Kb3, // 0x20
    Kb4,
    Kb5,
    Kb6,
    Kb7,
    Kb8,
    Kb9,
    Kb0, //40
    Enter,
    Escape,
    BSpace,
    Tab,
    Space,
    Minus, //0x2D - 45
    Equal,
    LBracket,
    RBracket,  // 0x30 --48
    BSlash,    // \ (and |)
    NonUsHash, // Non-US # and ~ (Typically near the Enter key)
    SColon,    // ; (and :)
    Quote,     // ' and "
    Grave,     // Grave accent and tilde
    Comma,     // , and <
    Dot,       // . and >
    Slash,     // / and ?
    CapsLock,
    F1,
    F2,
    F3,
    F4,
    F5,
    F6,
    F7, // 0x40
    F8,
    F9,
    F10,
    F11,
    F12,
    PScreen,
    ScrollLock,
    Pause,
    Insert,
    Home,
    PgUp,
    Delete,
    End,
    PgDown,
    Right,
    Left, // 0x50
    Down,
    Up,
    NumLock,
    KpSlash,
    KpAsterisk,
    KpMinus,
    KpPlus,
    KpEnter,
    Kp1,
    Kp2,
    Kp3,
    Kp4,
    Kp5,
    Kp6,
    Kp7,
    Kp8, // 0x60
    Kp9,
    Kp0,
    KpDot,
    NonUsBslash, // Non-US \ and | (Typically near the Left-Shift key)
    Application, // 0x65

    Power,   // 0x66,
    KpEqual, // 0x67, // Keypad =

    F13, // 0x68 Keyboard F13
    F14, // 0x69, // Keyboard F14
    F15, // 0x6a, // Keyboard F15
    F16, // 0x6b, // Keyboard F16
    F17, // 0x6c, // Keyboard F17
    F18, // 0x6d, // Keyboard F18
    F19, // 0x6e, // Keyboard F19
    F20, // 0x6f, // Keyboard F20
    F21, // 0x70, // Keyboard F21
    F22, // 0x71, // Keyboard F22
    F23, // 0x72, // Keyboard F23
    F24, // 0x73, // Keyboard F24

    Open,                      // 0x74,       // Keyboard Execute
    Help,                      // 0x75,       // Keyboard Help
    KeyboardMenu,              // 0x76,      // Keyboard Menu
    Front,                     // 0x77,      // Keyboard Select
    Stop,                      // 0x78,       // Keyboard Stop
    Again,                     // 0x79,      // Keyboard Again
    Undo,                      // 0x7a,       // Keyboard Undo
    Cut,                       // 0x7b,        // Keyboard Cut
    Copy,                      // 0x7c,       // Keyboard Copy
    Paste,                     // 0x7d,      // Keyboard Paste
    Find,                      // 0x7e,       // Keyboard Find
    Mute,                      // 0x7f,       // Keyboard Mute
    VolumeUp,                  // 0x80,   // Keyboard Volume Up
    VolumeDown,                // 0x81, // Keyboard Volume Down
    KeyboardLockingCapsLock,   // 0x82  Keyboard Locking Caps Lock
    KeyboardLockingNumLock,    // 0x83  Keyboard Locking Num Lock
    KeyboardLockingScrollLock, // 0x84  Keyboard Locking Scroll Lock
    Kpcomma,                   // 0x85, // Keypad Comma
    EqualSign,                 // // 0x86  Keypad Equal Sign
    Ro,                        // 0x87,               // Keyboard International1
    Katakanahiragana,          // 0x88, // Keyboard International2
    Yen,                       // 0x89,              // Keyboard International3
    Henkan,                    // 0x8a,           // Keyboard International4
    Muhenkan,                  // 0x8b,         // Keyboard International5
    KpJpComma,                 // 0x8c,        // Keyboard International6
    KeyboardInternational7,    // 0x8d  Keyboard International7
    KeyboardInternational8,    // 0x8e  Keyboard International8
    KeyboardInternational9,    // 0x8f  Keyboard International9
    Hangeul,                   // 0x90,        // Keyboard LANG1
    Hanja,                     // 0x91,          // Keyboard LANG2
    Katakana,                  // 0x92,       // Keyboard LANG3
    Hiragana,                  // 0x93,       // Keyboard LANG // 0x674
    Zenkakuhankaku,            // 0x94, // Keyboard LANG5
    KpLeftParen = 0xb6,        // Keypad (
    KpRightParen,              // 0xb7 // Keypad )
    // Modifiers
    LCtrl = 0xE0,
    LShift,
    LAlt,
    LGui,
    RCtrl,
    RShift,
    RAlt,
    RGui, // 0xE7

    MediaPlayPause = 0xE8,
    MediaStopCd,
    MediaPrevioussong,
    MediaNextsong,
    MediaEjectCd,
    MediaVolumeUp,
    MediaVolumeDown,
    MediaMUte,
    MediaWww,
    MediaBack,
    MediaForward,
    MediaStop,
    MediaFind,
    MediaScrollUp,
    MediaScrollDown,
    MediaEdit,
    MediaSleep,
    MediaCoffee,
    MediaRefresh,
    MediaCalc,
}

impl Default for KeyCode {
    fn default() -> Self {
        KeyCode::Null
    }
}

pub const KBD_TABLE: &[UsbKey] = &[
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // },
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // SOH
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // STX
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // ETX
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // EOT
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // ENQ
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // ACK
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // BEL
    // UsbKey {
    //     code: 0x2a,
    //     modifier: 0,
    // }, // BS	Backspace
    // UsbKey {
    //     code: 0x2b,
    //     modifier: 0,
    // }, // TAB	Tab
    // UsbKey {
    //     code: 0x28,
    //     modifier: 0,
    // }, // LF	Enter
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // VT
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // FF
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // CR
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // SO
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // SI
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // DEL
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // DC1
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // DC2
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // DC3
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // DC4
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // NAK
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // SYN
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // ETB
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // CAN
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // EM
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // SUB
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // ESC
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // FS
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // GS
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // RS
    // UsbKey {
    //     code: 0x00,
    //     modifier: 0,
    // }, // US
    // UsbKey {
    //     code: 0x2c,
    //     modifier: 0,
    // }, //  ' '
    // UsbKey {
    //     code: 0x1e,
    //     modifier: SHIFT,
    // }, // !
    // UsbKey {
    //     code: 0x34,
    //     modifier: SHIFT,
    // }, // "
    // UsbKey {
    //     code: 0x20,
    //     modifier: SHIFT,
    // }, // #
    // UsbKey {
    //     code: 0x21,
    //     modifier: SHIFT,
    // }, // $
    // UsbKey {
    //     code: 0x22,
    //     modifier: SHIFT,
    // }, // %
    // UsbKey {
    //     code: 0x24,
    //     modifier: SHIFT,
    // }, // &
    // UsbKey {
    //     code: 0x34,
    //     modifier: 0,
    // }, // '
    // UsbKey {
    //     code: 0x26,
    //     modifier: SHIFT,
    // }, // (
    // UsbKey {
    //     code: 0x27,
    //     modifier: SHIFT,
    // }, // )
    // UsbKey {
    //     code: 0x25,
    //     modifier: SHIFT,
    // }, // *
    // UsbKey {
    //     code: 0x2e,
    //     modifier: SHIFT,
    // }, // +
    // UsbKey {
    //     code: 0x36,
    //     modifier: 0,
    // }, // ,
    // UsbKey {
    //     code: 0x2d,
    //     modifier: 0,
    // }, // -
    // UsbKey {
    //     code: 0x37,
    //     modifier: 0,
    // }, // .
    // UsbKey {
    //     code: 0x38,
    //     modifier: 0,
    // }, // /
    // UsbKey {
    //     code: 0x27,
    //     modifier: 0,
    // }, // 0
    // UsbKey {
    //     code: 0x1e,
    //     modifier: 0,
    // }, // 1
    // UsbKey {
    //     code: 0x1f,
    //     modifier: 0,
    // }, // 2
    // UsbKey {
    //     code: 0x20,
    //     modifier: 0,
    // }, // 3
    // UsbKey {
    //     code: 0x21,
    //     modifier: 0,
    // }, // 4
    // UsbKey {
    //     code: 0x22,
    //     modifier: 0,
    // }, // 5
    // UsbKey {
    //     code: 0x23,
    //     modifier: 0,
    // }, // 6
    // UsbKey {
    //     code: 0x24,
    //     modifier: 0,
    // }, // 7
    // UsbKey {
    //     code: 0x25,
    //     modifier: 0,
    // }, // 8
    // UsbKey {
    //     code: 0x26,
    //     modifier: 0,
    // }, // 9
    // UsbKey {
    //     code: 0x33,
    //     modifier: SHIFT,
    // }, // :
    // UsbKey {
    //     code: 0x33,
    //     modifier: 0,
    // }, // ;
    // UsbKey {
    //     code: 0x36,
    //     modifier: SHIFT,
    // }, // <
    // UsbKey {
    //     code: 0x2e,
    //     modifier: 0,
    // }, // =
    // UsbKey {
    //     code: 0x37,
    //     modifier: SHIFT,
    // }, // >
    // UsbKey {
    //     code: 0x38,
    //     modifier: SHIFT,
    // }, // ?
    // UsbKey {
    //     code: 0x1f,
    //     modifier: SHIFT,
    // }, // @
    // UsbKey {
    //     code: 0x04,
    //     modifier: SHIFT,
    // }, // A
    // UsbKey {
    //     code: 0x05,
    //     modifier: SHIFT,
    // }, // B
    // UsbKey {
    //     code: 0x06,
    //     modifier: SHIFT,
    // }, // C
    // UsbKey {
    //     code: 0x07,
    //     modifier: SHIFT,
    // }, // D
    // UsbKey {
    //     code: 0x08,
    //     modifier: SHIFT,
    // }, // E
    // UsbKey {
    //     code: 0x09,
    //     modifier: SHIFT,
    // }, // F
    // UsbKey {
    //     code: 0x0a,
    //     modifier: SHIFT,
    // }, // G
    // UsbKey {
    //     code: 0x0b,
    //     modifier: SHIFT,
    // }, // H
    // UsbKey {
    //     code: 0x0c,
    //     modifier: SHIFT,
    // }, // I
    // UsbKey {
    //     code: 0x0d,
    //     modifier: SHIFT,
    // }, // J
    // UsbKey {
    //     code: 0x0e,
    //     modifier: SHIFT,
    // }, // K
    // UsbKey {
    //     code: 0x0f,
    //     modifier: SHIFT,
    // }, // L
    // UsbKey {
    //     code: 0x10,
    //     modifier: SHIFT,
    // }, // M
    // UsbKey {
    //     code: 0x11,
    //     modifier: SHIFT,
    // }, // N
    // UsbKey {
    //     code: 0x12,
    //     modifier: SHIFT,
    // }, // O
    // UsbKey {
    //     code: 0x13,
    //     modifier: SHIFT,
    // }, // P
    // UsbKey {
    //     code: 0x14,
    //     modifier: SHIFT,
    // }, // Q
    // UsbKey {
    //     code: 0x15,
    //     modifier: SHIFT,
    // }, // R
    // UsbKey {
    //     code: 0x16,
    //     modifier: SHIFT,
    // }, // S
    // UsbKey {
    //     code: 0x17,
    //     modifier: SHIFT,
    // }, // T
    // UsbKey {
    //     code: 0x18,
    //     modifier: SHIFT,
    // }, // U
    // UsbKey {
    //     code: 0x19,
    //     modifier: SHIFT,
    // }, // V
    // UsbKey {
    //     code: 0x1a,
    //     modifier: SHIFT,
    // }, // W
    // UsbKey {
    //     code: 0x1b,
    //     modifier: SHIFT,
    // }, // X
    // UsbKey {
    //     code: 0x1c,
    //     modifier: SHIFT,
    // }, // Y
    // UsbKey {
    //     code: 0x1d,
    //     modifier: SHIFT,
    // }, // Z
    // UsbKey {
    //     code: 0x2f,
    //     modifier: 0,
    // }, // [
    // UsbKey {
    //     code: 0x31,
    //     modifier: 0,
    // }, // bslash
    // UsbKey {
    //     code: 0x30,
    //     modifier: 0,
    // }, // ]
    // UsbKey {
    //     code: 0x23,
    //     modifier: SHIFT,
    // }, // ^
    // UsbKey {
    //     code: 0x2d,
    //     modifier: SHIFT,
    // }, // _
    // UsbKey {
    //     code: 0x35,
    //     modifier: 0,
    // }, // `
    // UsbKey {
    //     code: 0x04,
    //     modifier: 0,
    // }, // a
    // UsbKey {
    //     code: 0x05,
    //     modifier: 0,
    // }, // b
    // UsbKey {
    //     code: 0x06,
    //     modifier: 0,
    // }, // c
    // UsbKey {
    //     code: 0x07,
    //     modifier: 0,
    // }, // d
    // UsbKey {
    //     code: 0x08,
    //     modifier: 0,
    // }, // e
    // UsbKey {
    //     code: 0x09,
    //     modifier: 0,
    // }, // f
    // UsbKey {
    //     code: 0x0a,
    //     modifier: 0,
    // }, // g
    // UsbKey {
    //     code: 0x0b,
    //     modifier: 0,
    // }, // h
    // UsbKey {
    //     code: 0x0c,
    //     modifier: 0,
    // }, // i
    // UsbKey {
    //     code: 0x0d,
    //     modifier: 0,
    // }, // j
    // UsbKey {
    //     code: 0x0e,
    //     modifier: 0,
    // }, // k
    // UsbKey {
    //     code: 0x0f,
    //     modifier: 0,
    // }, // l
    // UsbKey {
    //     code: 0x10,
    //     modifier: 0,
    // }, // m
    // UsbKey {
    //     code: 0x11,
    //     modifier: 0,
    // }, // n
    // UsbKey {
    //     code: 0x12,
    //     modifier: 0,
    // }, // o
    // UsbKey {
    //     code: 0x13,
    //     modifier: 0,
    // }, // p
    // UsbKey {
    //     code: 0x14,
    //     modifier: 0,
    // }, // q
    // UsbKey {
    //     code: 0x15,
    //     modifier: 0,
    // }, // r
    // UsbKey {
    //     code: 0x16,
    //     modifier: 0,
    // }, // s
    // UsbKey {
    //     code: 0x17,
    //     modifier: 0,
    // }, // t
    // UsbKey {
    //     code: 0x18,
    //     modifier: 0,
    // }, // u
    // UsbKey {
    //     code: 0x19,
    //     modifier: 0,
    // }, // v
    // UsbKey {
    //     code: 0x1a,
    //     modifier: 0,
    // }, // w
    // UsbKey {
    //     code: 0x1b,
    //     modifier: 0,
    // }, // x
    // UsbKey {
    //     code: 0x1c,
    //     modifier: 0,
    // }, // y
    // UsbKey {
    //     code: 0x1d,
    //     modifier: 0,
    // }, // z
    // UsbKey {
    //     code: 0x2f,
    //     modifier: SHIFT,
    // }, // {
    // UsbKey {
    //     code: 0x31,
    //     modifier: SHIFT,
    // }, // |
    // UsbKey {
    //     code: 0x30,
    //     modifier: SHIFT,
    // }, // }
    // UsbKey {
    //     code: 0x35,
    //     modifier: SHIFT,
    // }, // ~
    // UsbKey {
    //     code: 0,
    //     modifier: 0,
    // }, // DEL
];

impl<T> core::cmp::PartialEq<T> for KeyCode
where
    T: num_traits::PrimInt,
{
    fn eq(&self, other: &T) -> bool {
        Some(*self as u64) == other.to_u64()
    }
}

#[derive(Clone, Default, Eq, PartialEq)]
#[derive(Debug)]
pub struct UsbKey {
    pub codes: ArrayVec<[KeyCode; 6]>,
    pub modifiers: Modifiers,
}

impl UsbKey {
    pub fn with_modifier(codes: impl Array<Item = KeyCode>, modifiers: Modifiers) -> UsbKey {
        UsbKey {
            codes: codes.as_slice().iter().take(6).cloned().collect(),
            modifiers,
        }
    }

    pub fn new(codes: impl Array<Item = KeyCode>) -> UsbKey {
        UsbKey {
            codes: codes.as_slice().iter().take(6).cloned().collect(),
            modifiers: Default::default(),
        }
    }

    pub fn append_codes(&mut self, codes: impl IntoIterator<Item = KeyCode>) -> &mut Self {
        codes.into_iter().for_each(|code| {
            self.codes.try_push(code).ok();
        });
        self
    }

    pub fn append_modifiers(&mut self, modifiers: Modifiers) -> &mut Self {
        self.modifiers |= modifiers;
        self
    }
}
pub struct UnknownKeyError;
impl TryFrom<AsciiChar> for UsbKey {
    type Error = UnknownKeyError;

    fn try_from(other: AsciiChar) -> Result<Self, Self::Error> {
        use KeyCode::*;

        match other {
            AsciiChar::Space => Some(UsbKey::new([Space])),
            AsciiChar::Exclamation => Some(UsbKey::with_modifier([Kb1], Modifiers::LEFT_SHIFT)),
            AsciiChar::Quotation => Some(UsbKey::with_modifier([Quote], Modifiers::LEFT_SHIFT)),
            AsciiChar::Hash => Some(UsbKey::with_modifier([Kb3], Modifiers::LEFT_SHIFT)),
            AsciiChar::Dollar => Some(UsbKey::with_modifier([Kb4], Modifiers::LEFT_SHIFT)),
            AsciiChar::Percent => Some(UsbKey::with_modifier([Kb5], Modifiers::LEFT_SHIFT)),
            AsciiChar::Ampersand => Some(UsbKey::with_modifier([Kb7], Modifiers::LEFT_SHIFT)),
            AsciiChar::Apostrophe => Some(UsbKey::new([Quote])),
            AsciiChar::ParenOpen => Some(UsbKey::with_modifier([Kb9], Modifiers::LEFT_SHIFT)),
            AsciiChar::ParenClose => Some(UsbKey::with_modifier([Kb0], Modifiers::LEFT_SHIFT)),
            AsciiChar::Asterisk => Some(UsbKey::with_modifier([Kb8], Modifiers::LEFT_SHIFT)),
            AsciiChar::Plus => Some(UsbKey::with_modifier([KpPlus], Modifiers::LEFT_SHIFT)),
            AsciiChar::Comma => Some(UsbKey::new([Comma])),
            AsciiChar::Minus => Some(UsbKey::new([KpMinus])),
            AsciiChar::Dot => Some(UsbKey::new([Dot])),
            AsciiChar::Slash => Some(UsbKey::new([Slash])),
            AsciiChar::_0 => Some(UsbKey::new([Kb0])),
            AsciiChar::_1 => Some(UsbKey::new([Kb1])),
            AsciiChar::_2 => Some(UsbKey::new([Kb2])),
            AsciiChar::_3 => Some(UsbKey::new([Kb3])),
            AsciiChar::_4 => Some(UsbKey::new([Kb4])),
            AsciiChar::_5 => Some(UsbKey::new([Kb5])),
            AsciiChar::_6 => Some(UsbKey::new([Kb6])),
            AsciiChar::_7 => Some(UsbKey::new([Kb7])),
            AsciiChar::_8 => Some(UsbKey::new([Kb8])),
            AsciiChar::_9 => Some(UsbKey::new([Kb9])),
            AsciiChar::Colon => Some(UsbKey::with_modifier([SColon], Modifiers::LEFT_SHIFT)),
            AsciiChar::Semicolon => Some(UsbKey::new([SColon])),
            AsciiChar::LessThan => Some(UsbKey::with_modifier([Comma], Modifiers::LEFT_SHIFT)),
            AsciiChar::Equal => Some(UsbKey::new([Equal])),
            AsciiChar::GreaterThan => Some(UsbKey::with_modifier([Dot], Modifiers::LEFT_SHIFT)),
            AsciiChar::Question => Some(UsbKey::with_modifier([Slash], Modifiers::LEFT_SHIFT)),
            AsciiChar::At => Some(UsbKey::with_modifier([Kb2], Modifiers::LEFT_SHIFT)),
            AsciiChar::A => Some(UsbKey::with_modifier([A], Modifiers::LEFT_SHIFT)),
            AsciiChar::B => Some(UsbKey::with_modifier([B], Modifiers::LEFT_SHIFT)),
            AsciiChar::C => Some(UsbKey::with_modifier([C], Modifiers::LEFT_SHIFT)),
            AsciiChar::D => Some(UsbKey::with_modifier([D], Modifiers::LEFT_SHIFT)),
            AsciiChar::E => Some(UsbKey::with_modifier([E], Modifiers::LEFT_SHIFT)),
            AsciiChar::F => Some(UsbKey::with_modifier([F], Modifiers::LEFT_SHIFT)),
            AsciiChar::G => Some(UsbKey::with_modifier([G], Modifiers::LEFT_SHIFT)),
            AsciiChar::H => Some(UsbKey::with_modifier([H], Modifiers::LEFT_SHIFT)),
            AsciiChar::I => Some(UsbKey::with_modifier([I], Modifiers::LEFT_SHIFT)),
            AsciiChar::J => Some(UsbKey::with_modifier([J], Modifiers::LEFT_SHIFT)),
            AsciiChar::K => Some(UsbKey::with_modifier([K], Modifiers::LEFT_SHIFT)),
            AsciiChar::L => Some(UsbKey::with_modifier([L], Modifiers::LEFT_SHIFT)),
            AsciiChar::M => Some(UsbKey::with_modifier([M], Modifiers::LEFT_SHIFT)),
            AsciiChar::N => Some(UsbKey::with_modifier([N], Modifiers::LEFT_SHIFT)),
            AsciiChar::O => Some(UsbKey::with_modifier([O], Modifiers::LEFT_SHIFT)),
            AsciiChar::P => Some(UsbKey::with_modifier([P], Modifiers::LEFT_SHIFT)),
            AsciiChar::Q => Some(UsbKey::with_modifier([Q], Modifiers::LEFT_SHIFT)),
            AsciiChar::R => Some(UsbKey::with_modifier([R], Modifiers::LEFT_SHIFT)),
            AsciiChar::S => Some(UsbKey::with_modifier([S], Modifiers::LEFT_SHIFT)),
            AsciiChar::T => Some(UsbKey::with_modifier([T], Modifiers::LEFT_SHIFT)),
            AsciiChar::U => Some(UsbKey::with_modifier([U], Modifiers::LEFT_SHIFT)),
            AsciiChar::V => Some(UsbKey::with_modifier([V], Modifiers::LEFT_SHIFT)),
            AsciiChar::W => Some(UsbKey::with_modifier([W], Modifiers::LEFT_SHIFT)),
            AsciiChar::X => Some(UsbKey::with_modifier([X], Modifiers::LEFT_SHIFT)),
            AsciiChar::Y => Some(UsbKey::with_modifier([Y], Modifiers::LEFT_SHIFT)),
            AsciiChar::Z => Some(UsbKey::with_modifier([Z], Modifiers::LEFT_SHIFT)),
            AsciiChar::BracketOpen => Some(UsbKey::new([LBracket])),
            AsciiChar::BackSlash => Some(UsbKey::new([BSlash])),
            AsciiChar::BracketClose => Some(UsbKey::new([RBracket])),
            AsciiChar::UnderScore => Some(UsbKey::with_modifier([Minus], Modifiers::LEFT_SHIFT)),
            AsciiChar::Grave => Some(UsbKey::with_modifier([Grave], Modifiers::LEFT_SHIFT)),
            AsciiChar::a => Some(UsbKey::new([A])),
            AsciiChar::b => Some(UsbKey::new([B])),
            AsciiChar::c => Some(UsbKey::new([C])),
            AsciiChar::d => Some(UsbKey::new([D])),
            AsciiChar::e => Some(UsbKey::new([E])),
            AsciiChar::f => Some(UsbKey::new([F])),
            AsciiChar::g => Some(UsbKey::new([G])),
            AsciiChar::h => Some(UsbKey::new([H])),
            AsciiChar::i => Some(UsbKey::new([I])),
            AsciiChar::j => Some(UsbKey::new([J])),
            AsciiChar::k => Some(UsbKey::new([K])),
            AsciiChar::l => Some(UsbKey::new([L])),
            AsciiChar::m => Some(UsbKey::new([M])),
            AsciiChar::n => Some(UsbKey::new([N])),
            AsciiChar::o => Some(UsbKey::new([O])),
            AsciiChar::p => Some(UsbKey::new([P])),
            AsciiChar::q => Some(UsbKey::new([Q])),
            AsciiChar::r => Some(UsbKey::new([R])),
            AsciiChar::s => Some(UsbKey::new([S])),
            AsciiChar::t => Some(UsbKey::new([T])),
            AsciiChar::u => Some(UsbKey::new([U])),
            AsciiChar::v => Some(UsbKey::new([V])),
            AsciiChar::w => Some(UsbKey::new([W])),
            AsciiChar::x => Some(UsbKey::new([X])),
            AsciiChar::y => Some(UsbKey::new([Y])),
            AsciiChar::z => Some(UsbKey::new([Z])),
            AsciiChar::LineFeed => Some(UsbKey::new([Enter])),
            AsciiChar::CurlyBraceOpen => {
                Some(UsbKey::with_modifier([LBracket], Modifiers::LEFT_SHIFT))
            }
            AsciiChar::VerticalBar => Some(UsbKey::with_modifier([BSlash], Modifiers::LEFT_SHIFT)),
            AsciiChar::CurlyBraceClose => {
                Some(UsbKey::with_modifier([RBracket], Modifiers::LEFT_SHIFT))
            }
            AsciiChar::Tilde => Some(UsbKey::with_modifier([Grave], Modifiers::LEFT_SHIFT)),
            _ => None,
        }
        .ok_or(UnknownKeyError)
    }
}

#[derive(Constructor)]
pub struct StrToUsbLossyIterator<'a> {
    payload: &'a str,
    #[new(default)]
    index: usize,
}

impl<'a> Iterator for StrToUsbLossyIterator<'a> {
    type Item = UsbKey;

    fn next(&mut self) -> Option<Self::Item> {
        loop {
            self.index += 1;

            if let Some(key) = self
                .payload
                .as_bytes()
                .get(self.index - 1)?
                .to_ascii_char()
                .ok()
                .and_then(|character| UsbKey::try_from(character).ok())
            {
                break Some(key);
            }
        }
    }
}

pub trait StrExt {
    fn to_usb_keys_iter_lossy(&self) -> StrToUsbLossyIterator<'_>;
}

impl StrExt for str {
    fn to_usb_keys_iter_lossy(&self) -> StrToUsbLossyIterator<'_> {
        StrToUsbLossyIterator::new(self)
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn test_some_keycodes() {
        use KeyCode::*;

        assert_eq!(F7, 0x40);
        assert_eq!(H, 11);
        assert_eq!(K, 0x0E);
        assert_eq!(Again, 0x79);
        assert_eq!(Katakanahiragana, 0x88);
    }
}
