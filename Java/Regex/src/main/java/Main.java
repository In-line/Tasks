import regex.interpretationresult.InterpreterResult;
import regex.RegexMachine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<InterpreterResult> resultList = new ArrayList<>();
//        for(int j = 0; j < 100000; ++j) {
//            for (int i = 0; i < 100000; ++i) {
                RegexMachine machine = null;
                try {
                    machine = new RegexMachine("(.*)(.*)cd");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                InterpreterResult result = machine.execute("aaaacdaaa");
                if (result.isMatched()) {

                    //System.out.println("Okey");
                } else {
                    //System.out.println("Not okey");
                }
                resultList.add(result);
//            }
//        }
        System.out.println((result.isMatched() ? "G: " : "F: ") + result.getMatchedSubString());


//        for(InterpreterResult result : resultList) {
//            System.out.println((result.isMatched() ? "G: " : "F: ") + result.getMatchedSubString());
//        }
    }

//    static void showResults(InterpreterResult result) {
//        System.out.println((result.isMatched() ? "G: " : "F: ") + result.getMatchedSubString());
//        for (Iterator<InterpreterResult> it = result.getSubResultsIterator(); it.hasNext(); ) {
//            InterpreterResult subResult = it.next();
//            showResults(subResult);
//        }
//    }
}