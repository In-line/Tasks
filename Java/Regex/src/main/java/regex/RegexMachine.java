package regex;

import regex.exceptions.RegexCompilerError;
import regex.interpretationresult.InterpreterResult;

import java.util.List;
import java.util.regex.Pattern;

public class RegexMachine {
    String regex;
    List<IToken> tokenList;
    Pattern javaRegexPattern;

    public RegexMachine(String regex) throws RegexCompilerError {
        this.regex = regex;
        tokenList = new RegexCompiler(regex).getCompiled();
        //javaRegexPattern = Pattern.compile(regex);
    }

    public InterpreterResult execute(String input) {
        RegexInterpreter interpreter = new RegexInterpreter(tokenList);
        InterpreterResult result = interpreter.interpret(input);
/*
        Matcher matcher = javaRegexPattern.matcher(input);

        matcher.find();

        if(!result.getMatchedSubString().equals(matcher.group())) {
            throw new Error("Regex interpreter internal error");
        }
*/
        return result;
    }
}
