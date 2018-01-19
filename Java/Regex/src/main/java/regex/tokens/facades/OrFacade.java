package regex.tokens.facades;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;

import java.util.List;

public class OrFacade extends AbstractFacade {
    protected OrFacade(List<IToken> innerTokens) {
        super(innerTokens);
    }

    @Override
    // TODO: Backtracking?
    public InterpreterResult interpret(InterpretationContext context) {
        return null;
    }
}
