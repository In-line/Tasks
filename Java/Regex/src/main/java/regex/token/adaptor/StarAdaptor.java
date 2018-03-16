package regex.token.adaptor;

import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.IToken;
import regex.interpretationresult.InterpreterResultBuilder;

public class StarAdaptor extends PlusAdaptor {
    public StarAdaptor(IToken innerToken) {
        super(innerToken);
    }

    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        InterpreterResult result = super.interpret(context);

        if (!result.isMatched()) {
            result = new
                    InterpreterResultBuilder.WithContext(context)
                    .setIsGood(true)
                    .complete();
        }

        return result;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof StarAdaptor;
    }
}
