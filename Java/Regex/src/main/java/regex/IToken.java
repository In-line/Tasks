package regex;

import regex.interpretationresult.InterpreterResult;

public interface IToken {
    InterpreterResult interpret(InterpretationContext context);
    boolean reset();
}
