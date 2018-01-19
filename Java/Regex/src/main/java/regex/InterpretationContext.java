package regex;


import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

public class InterpretationContext {
    private String inputString;
    private int position;

    public InterpretationContext(final String inputString) {
        assert inputString != null;
        this.inputString = inputString;
        this.position = 0;
    }

    public InterpretationContext(final String inputString, int position) {
        this(inputString);
        this.position = position;
    }

    public InterpretationContext assign(final InterpretationContext context) {
        this.inputString = context.inputString;
        this.position = context.position;

        return this;
    }

    public InterpretationContext(final InterpretationContext context) {
        this(context.inputString, context.position);
    }

    public String getString() {
        return this.inputString.substring(position);
    }

    public int getLength() {
        return getString().length();
    }

    public String getOriginalString() {
        return this.inputString;
    }

    public int getOriginalLength() {
        return this.getOriginalString().length();
    }

    public char getChar() {
        return this.getString().charAt(0);
    }

    public int nextPosition() {
        return this.nextPosition(1);
    }

    public int nextPosition(int i) {
        this.position += i;
        assert this.position <= this.inputString.length();
        return this.position;
    }

    public int getPositionRelativeToOriginal() {
        return nextPosition(0);
    }


    @Override
    public boolean equals(Object o) {
        if(!(o instanceof InterpretationContext)) {
            return false;
        }

        InterpretationContext c = (InterpretationContext) o;

        return position == c.position && inputString.equals(c.inputString);
    }
}