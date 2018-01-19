package regex.tokens.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;

// TODO: Lazy.
public class QuestionMarkToken implements IToken {
    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof QuestionMarkToken;
    }
}
