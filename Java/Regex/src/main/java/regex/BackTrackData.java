package regex;

import com.rits.cloning.Cloner;
import regex.interpretationresult.InterpreterResult;

import java.util.Stack;

public class BackTrackData {
    private Stack<BackTrackPair> backTrackList;

    public BackTrackData() {
        backTrackList = new Stack<>();
    }

    public void push(final InterpretationContext context, final InterpreterResult result) {
        backTrackList.push(new BackTrackPair(new InterpretationContext(context), recursiveInterpreterResultDeepClone(result)));
    }

    public BackTrackPair pop() {
        return this.isEmpty() ? null : backTrackList.pop();
    }

    public BackTrackPair peek() {
        return this.isEmpty() ? null : backTrackList.peek();
    }

    public boolean isEmpty() {
        return backTrackList.isEmpty();
    }

    public void clear() {
        backTrackList.clear();
    }

    // NOTE: String is not cloned.
    // NOTE: new Cloner.deepClone() is much slower than this.
    private InterpreterResult recursiveInterpreterResultDeepClone(InterpreterResult result) {
//        return new Cloner().deepClone(result);
        InterpreterResult cloneResult = new InterpreterResult(result);
        for(InterpreterResult subResult : result.getSubResults()) {
            InterpreterResult clonedSubResult = recursiveInterpreterResultDeepClone(subResult);
            cloneResult.add(new InterpreterResult(clonedSubResult));
        }
        return cloneResult;
    }
}
