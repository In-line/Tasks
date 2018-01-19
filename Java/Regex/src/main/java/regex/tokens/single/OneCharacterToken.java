package regex.tokens.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

public class OneCharacterToken extends AbstractSingleCharacterToken {
    Character character;

    public OneCharacterToken(Character character) {
        this.character = character;
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    protected boolean isGood(char c) {
        return character.equals(c);
    }
}
