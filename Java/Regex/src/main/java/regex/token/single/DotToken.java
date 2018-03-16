package regex.token.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

public class DotToken extends AbstractSingleCharacterToken {
    @Override
    protected boolean isGood(char c) {
        return true; // length == 0 is handled in AbstractSingleCharacterToken
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DotToken;
    }
}