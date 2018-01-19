package regex.tokens.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;


public class AlphaToken extends AbstractSingleCharacterToken {
    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AlphaToken;
    }

    @Override
    protected boolean isGood(char c) {
        return Character.isDigit(c) || (c > 'a' && c < 'z') || (c > 'A' && c < 'Z') || c == '_';
    }
}