package regex;

import org.javatuples.Pair;
import regex.interpretationresult.InterpreterResult;

public class BackTrackPair  {
    Pair<InterpretationContext, InterpreterResult> inner;

    public BackTrackPair(final InterpretationContext context, final InterpreterResult result) {
        this.inner = new Pair<>(context, result);
    }

    public InterpretationContext getContext() {
        return inner.getValue0();
    }

    public InterpreterResult getResult() {
        return inner.getValue1();
    }

    public Pair<InterpretationContext, InterpreterResult> getPair() {
        return inner;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof BackTrackPair)) {
            return false;
        }

        BackTrackPair pair = (BackTrackPair) o;

        return inner.equals(pair.inner);
    }
}
