package regex.token.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

// Can be negated
public class WhiteSpaceToken extends AbstractSingleCharacterToken {
    @Override
    protected boolean isGood(char c) {
        return Character.isWhitespace(c);
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WhiteSpaceToken;
    }
}