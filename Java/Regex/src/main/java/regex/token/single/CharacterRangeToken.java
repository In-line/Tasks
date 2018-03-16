package regex.token.single;

import regex.IToken;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

public class CharacterRangeToken extends AbstractSingleCharacterToken {
    private Character start;
    private Character end;

    public CharacterRangeToken(Character start, Character end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected boolean isGood(char c) {
        return c >= start && c <= end;
    }

    public Character getStart() {
        return start;
    }

    public void setStart(Character start) {
        this.start = start;
    }

    public Character getEnd() {
        return end;
    }

    public void setEnd(Character end) {
        this.end = end;
    }

    @Override
    public boolean reset() {
        return false;
    }
}
