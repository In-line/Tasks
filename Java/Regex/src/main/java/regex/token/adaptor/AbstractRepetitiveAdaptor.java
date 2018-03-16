package regex.token.adaptor;

import regex.*;

public abstract class AbstractRepetitiveAdaptor extends AbstractAdaptor {
    protected BackTrackData backTrackData;

    protected AbstractRepetitiveAdaptor(IToken innerToken) {
        super(innerToken);
        this.backTrackData = new BackTrackData();
    }

    public BackTrackPair backTrack() {
        if(innerToken instanceof AbstractRepetitiveAdaptor) {
            BackTrackPair pair = ((AbstractRepetitiveAdaptor) innerToken).backTrack(); // Recursive strategy.

            if(pair != null) {
                return pair;
            }
        }

        return backTrackData.isEmpty() ? null : backTrackData.pop();
    }

    // NOTE: This is necessary for correct backtracking.
    @Override
    public boolean reset() {
        boolean wasEmpty = this.getBackTrackData().isEmpty();
        getBackTrackData().clear();
        return wasEmpty;
    }

    public void clearBackTrackData() {
        this.getBackTrackData().clear();
    }

    public BackTrackData getBackTrackData() {
        return backTrackData;
    }
}
