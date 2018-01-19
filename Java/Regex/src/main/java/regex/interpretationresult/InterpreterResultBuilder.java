package regex.interpretationresult;

import regex.InterpretationContext;

public class InterpreterResultBuilder {
    private String string = null;
    private int position = 0;
    private int possiblyMatchedCharacterelsLen = 0;
    private int matchedCharactersLen = 0;
    private boolean isGood = false;

    public InterpreterResultBuilder() {

    }

    public InterpreterResultBuilder(String string) {
        this.setString(string);
    }

    public InterpreterResultBuilder(InterpretationContext context) {
        this.setContext(context);
    }

    public InterpreterResultBuilder setString(String string) {
        this.string = string;
        return this;
    }

    public InterpreterResultBuilder setPosition(int position) {
        this.position = position;
        return this;
    }

    public InterpreterResultBuilder setPossiblyMatchedCharactersLen(int possiblyMatchedCharacterelsLen) {
        this.possiblyMatchedCharacterelsLen = possiblyMatchedCharacterelsLen;
        return this;
    }

    public InterpreterResultBuilder setMatchedCharactersLen(int matchedCharactersLen) {
        this.matchedCharactersLen = matchedCharactersLen;
        return this;
    }

    public InterpreterResultBuilder setIsGood(boolean isGood) {
        this.isGood = isGood;
        return this;
    }

    public InterpreterResultBuilder setContext(InterpretationContext context) {
        return this
                .setString(context.getOriginalString())
                .setPosition(context.getPositionRelativeToOriginal()); // TODO:
    }

    public static class WithContext {
        InterpretationContext context;
        InterpreterResultBuilder builder;

        public WithContext(InterpretationContext context) {
            this.builder = new InterpreterResultBuilder(context);
            this.context = context;
        }

        public WithContext setPossiblyMatchedCharactersLen(int possiblyMatchedCharactersLen) {
            builder.setPossiblyMatchedCharactersLen(possiblyMatchedCharactersLen);
            return this;
        }

        public WithContext setMatchedCharactersLen(int matchedCharactersLen) {
            builder.setMatchedCharactersLen(matchedCharactersLen);
            return this;
        }

        public WithContext setIsGood(boolean isGood) {
            builder.setIsGood(isGood);
            return this;
        }

        public InterpreterResult complete() {
            InterpreterResult result = builder.complete();
            if(result.isMatched()) {
                context.nextPosition(result.getMatchedCharactersLen());
            }

            return result;
        }
    }

    public InterpreterResult complete() {
        return new InterpreterResult(string, position, possiblyMatchedCharacterelsLen, matchedCharactersLen, isGood);
    }
}