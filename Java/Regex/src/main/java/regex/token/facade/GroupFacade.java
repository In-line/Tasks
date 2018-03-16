package regex.token.facade;

import regex.BackTrackPair;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.token.adaptor.AbstractRepetitiveAdaptor;
import regex.IToken;

import java.util.List;

// TODO: Backtracking for nested groups?
public class GroupFacade extends AbstractFacade {
    public GroupFacade(List<IToken> innerTokens) {
        super(innerTokens);
    }

    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        final InterpreterResult rootResult = InterpreterResult.createRoot(context);

        AbstractRepetitiveAdaptor previousRepetitiveAdaptor = null;
        for (int i = 0; i < innerTokens.size(); ++i) {
            IToken token = innerTokens.get(i);

            InterpreterResult result = token.interpret(context);

            if (previousRepetitiveAdaptor != null && !result.isMatched()) {
                BackTrackPair backTrack = previousRepetitiveAdaptor.backTrack();
                if(backTrack == null) {
                    rootResult.add(result); // NOTE: This is fine. Because if backtracking didn't work, there is no match.
                    continue;
                }

                // TODO: Refactor.
                context.assign(backTrack.getContext());
                rootResult.getSubResults().set(rootResult.getSubResultsSize()- 1, backTrack.getResult());
                --i;
            } else {
                rootResult.add(result);

                try {
                    previousRepetitiveAdaptor = (AbstractRepetitiveAdaptor) token;
                } catch (ClassCastException e) {
                    previousRepetitiveAdaptor = null;
                }
            }
        }

        return rootResult;
    }

    @Override
    public boolean reset() {
        boolean wasSomethingResetted = false;
        for(IToken token : innerTokens) {
            wasSomethingResetted |= token.reset();
        }
        return wasSomethingResetted;
    }
}
