package regex.interpretationresult;

import regex.InterpretationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class InterpreterResult {
    private ArrayList<InterpreterResult> subResults;
    private String string;
    private int position;
    private int possiblyMatchedCharactersLen;
    private int matchedCharactersLen;
    private boolean isGood = false;

    public InterpreterResult(final String string, int position, int possiblyMatchedCharactersLen, int matchedCharactersLen, boolean isGood) {
        this.subResults = new ArrayList<>();
        this.string = string;
        this.position = position;
        this.possiblyMatchedCharactersLen = possiblyMatchedCharactersLen;
        this.matchedCharactersLen = matchedCharactersLen;
        this.isGood = isGood;
    }

    public InterpreterResult(InterpretationContext context, int possiblyMatchedCharactersLen, int matchedCharactersLen, boolean isGood) {
        this(context.getOriginalString(), context.getPositionRelativeToOriginal(), possiblyMatchedCharactersLen, matchedCharactersLen, isGood);
    }

    public InterpreterResult(final InterpreterResult in) {
        this(in.string, in.position, in.possiblyMatchedCharactersLen, in.matchedCharactersLen, in.isGood);
    }

    // This is root for another subResults.
    public static InterpreterResult createRoot(final InterpretationContext context) {
        InterpreterResult result = new
                InterpreterResultBuilder()
                .setContext(context)
                .setPosition(0)
                .setIsGood(false)
                .complete();

        return result;
    }


    // TODO: Greedy/UnGreedy
    public boolean isMatched() {

        for (InterpreterResult result : subResults) {
            if (!result.isMatched()) {
                return false;
            }
        }

        if (isGood || !subResults.isEmpty()) {
            return true;
        }

        return false;
    }

    public Iterator<InterpreterResult> getSubResultsIterator() {
        return this.subResults.iterator();
    }

    public List<InterpreterResult> getSubResults() {
        return this.subResults;
    }

    public int getSubResultsSize() {
        return this.subResults.size();
    }

    public InterpreterResult getSubResult(int i) {
        return this.getSubResults().get(i);
    }

    public InterpreterResult add(InterpreterResult other) {
        this.subResults.add(other);
        return this;
    }

    public int getMatchedCharactersLen() {
        return this.matchedCharactersLen;
    }

    public int getPossiblyMatchedCharactersLen() {
        return possiblyMatchedCharactersLen;
    }

    public int addMatchedCharacters(int i) {
        this.matchedCharactersLen += i;
        return this.matchedCharactersLen;
    }

    public String getCurrentlyMatched() {
        return this.string.substring(position, position + matchedCharactersLen);
    }

    public String getMatchedSubString() {
        if(!isMatched()) { // NOTE: Mini optimization
            return "";
        }

        StringBuilder builder = new StringBuilder(this.getCurrentlyMatched());

        for(final InterpreterResult result : this.getSubResults()) {
            builder.append(result.getMatchedSubString());
        }

        return builder.toString();
    }

    public InterpreterResult negate() {
        InterpreterResult copy = null;

        if(!subResults.isEmpty()) {
            copy = new InterpreterResult(this).negate();;

            for(InterpreterResult subResult : this.getSubResults())
            {
                copy.subResults.add(subResult.negate());
            }

        } else {
            copy = new InterpreterResult(this);
            copy.isGood = !this.isGood;

            // TODO: Think about this more.
            if(copy.isGood) {
                // copy.possiblyMatchedCharactersLen = this.matchedCharactersLen;
                copy.matchedCharactersLen = this.possiblyMatchedCharactersLen;
            } else {
                copy.matchedCharactersLen = 0;
            }
        }

        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InterpreterResult)) {
            return false;
        }

        InterpreterResult other = (InterpreterResult) o;


        return subResults.equals(other.subResults)
                && string.equals(other.string)
                && position == other.position
                && possiblyMatchedCharactersLen == other.possiblyMatchedCharactersLen
                && matchedCharactersLen == other.matchedCharactersLen
                && isGood == other.isGood;
    }
}

