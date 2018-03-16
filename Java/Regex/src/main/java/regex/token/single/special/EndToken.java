package regex.token.single.special;

import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;
import regex.token.single.AbstractSingleCharacterToken;

public class EndToken extends AbstractSingleCharacterToken {
    @Override
    protected boolean isGood(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        int offsetBeforeEnd = context.getOriginalLength() - context.getPositionRelativeToOriginal();

        boolean isGood = offsetBeforeEnd == 0 || (offsetBeforeEnd == 1 && context.getChar() == '\n');

        return new
                InterpreterResultBuilder.WithContext(context)
                .setPossiblyMatchedCharactersLen(offsetBeforeEnd)
                .setMatchedCharactersLen(isGood ? offsetBeforeEnd : 0)
                .setIsGood(isGood)
                .complete();
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof EndToken;
    }
}