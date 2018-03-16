package regex.token.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

public abstract class AbstractSingleCharacterToken implements IToken {
    protected abstract boolean isGood(char c);

    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        if (context.getLength() == 0) {
            return new
                    InterpreterResultBuilder.WithContext(context)
                    .complete();
        }

        boolean isGood = isGood(context.getChar());
        return new
                InterpreterResultBuilder.WithContext(context)
                .setPossiblyMatchedCharactersLen(1)
                .setMatchedCharactersLen(isGood ? 1 :0)
                .setIsGood(isGood)
                .complete();
    }

}
