package cz.cuni.mff.kohut.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String infixFormula1 = "(3 + (4 * 5)) / (2 + (1 * 3))";
        String infixFormula2 = "(3 + 4) * 2";
        InfixCalculator infixCalculator = new InfixCalculator();
        String postfixFormula = infixCalculator.ConvertToPostfix(infixFormula1);

        PostfixCalculator postfixCalculator = new PostfixCalculator();
        String[] splitLine = postfixFormula.split("\\s+");
        postfixCalculator.processLine(splitLine);
    }
}

