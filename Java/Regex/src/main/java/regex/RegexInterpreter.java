package regex;

import regex.token.facade.GroupFacade;
import regex.interpretationresult.InterpreterResult;

import java.util.Arrays;
import java.util.List;

public class RegexInterpreter {
    GroupFacade rootGroup;

    public RegexInterpreter(final List<IToken> tokens) {
        this.rootGroup = new GroupFacade(tokens);
    }

    public RegexInterpreter(final IToken[] tokens) {
        this.rootGroup = new GroupFacade(Arrays.asList(tokens));
    }

    public InterpreterResult interpret(final String input) {
        this.rootGroup.reset();

        InterpreterResult failedRoot = InterpreterResult.createRoot(new InterpretationContext(input));

        for (int offset = 0; offset <= input.length(); ++offset) {
            final InterpretationContext context = new InterpretationContext(input, offset);

            final InterpreterResult result = rootGroup.interpret(context);

            if (result.isMatched()) {
                return result;
            } else {
                failedRoot = result;
            }

        }

        return failedRoot;
    }

    public boolean reset() {
        return rootGroup.reset();
    }
}
