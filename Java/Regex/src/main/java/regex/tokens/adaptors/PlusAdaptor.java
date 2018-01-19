package regex.tokens.adaptors;

import regex.BackTrackPair;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.IToken;

// Repeat one or more times
public class PlusAdaptor extends AbstractRepetitiveAdaptor {

    public PlusAdaptor(IToken innerToken) {
        super(innerToken);
    }

    @Override
    public InterpreterResult interpret(final InterpretationContext context) {

        final InterpreterResult rootResult = InterpreterResult.createRoot(context);

        while (true) {
            BackTrackPair peek = backTrackData.peek();
            if(peek != null && context.equals(peek.getContext())) {
                break;
            }

            backTrackData.push(context, rootResult);
            final InterpreterResult result = innerToken.interpret(context);

            if(!result.isMatched()) {
                break;
            } else {
                rootResult.add(result);
            }
        }

        return rootResult;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlusAdaptor)) {
            return false;
        }

        final PlusAdaptor other = (PlusAdaptor) o;

        return other.innerToken.equals(this.innerToken);
    }
}
