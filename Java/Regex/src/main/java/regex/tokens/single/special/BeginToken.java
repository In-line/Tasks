package regex.tokens.single.special;

import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;
import regex.tokens.single.AbstractSingleCharacterToken;

public class BeginToken extends AbstractSingleCharacterToken {
    @Override
    protected boolean isGood(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        boolean isGood = context.getPositionRelativeToOriginal() == 0;

        return new
                InterpreterResultBuilder.WithContext(context)
                .setIsGood(isGood)
                .complete();
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BeginToken;
    }
}