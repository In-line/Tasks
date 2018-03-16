package regex.token;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

import static java.lang.Math.min;

public class StringToken implements IToken {
    private String string;

    public StringToken(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public InterpreterResult interpret(InterpretationContext context) {
        int i = 0;
        int minimalSize = min(string.length(), context.getLength());
        for (; i < minimalSize; ++i) {
            if (string.charAt(i) != context.getString().charAt(i)) {
                break;
            }
        }

        return new
                InterpreterResultBuilder.WithContext(context)
                .setPossiblyMatchedCharactersLen(minimalSize)
                .setMatchedCharactersLen(i)
                .setIsGood(i == string.length())
                .complete();
    }

    @Override
    public boolean reset() {
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StringToken)) {
            return false;
        }

        StringToken tokenObj = (StringToken) obj;
        return tokenObj.string.equals(this.string);
    }
}