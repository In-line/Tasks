package regex.tokens.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

public class DigitToken extends AbstractSingleCharacterToken {
    @Override
    protected boolean isGood(char c) {
        return Character.isDigit(c);
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DigitToken;
    }
}