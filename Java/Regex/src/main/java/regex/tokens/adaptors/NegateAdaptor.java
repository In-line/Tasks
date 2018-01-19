package regex.tokens.adaptors;

import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.IToken;

import static java.lang.Math.abs;

public class NegateAdaptor extends AbstractAdaptor {
    public NegateAdaptor(IToken innerToken) {
        super(innerToken);
    }

    // TODO: Maybe refactor? We should encapsulate such details.
    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        InterpreterResult original = innerToken.interpret(context);
        InterpreterResult negated = original.negate();

        int changedDistance = abs(original.getMatchedCharactersLen() - negated.getMatchedCharactersLen());

        if(negated.isMatched()) { // Negated isMatched so we should go forward.
            context.nextPosition(changedDistance);
        } else {
            context.nextPosition(-changedDistance);;
        }

        return negated;
    }

    @Override
    public boolean reset() {
        return false;
    }
}
