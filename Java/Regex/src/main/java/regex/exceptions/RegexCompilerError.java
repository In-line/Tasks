package regex.exceptions;

public class RegexCompilerError extends Error {
    public RegexCompilerError(int index) {
        super("Compiler error at: " + index);
    }
}
