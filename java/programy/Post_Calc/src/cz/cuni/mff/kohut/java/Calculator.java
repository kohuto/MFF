package cz.cuni.mff.kohut.java;

import java.util.Arrays;
import java.util.Stack;

public class Calculator {
    Stack<Integer> stack = new Stack<Integer>();
    private int processOperator(String operator){
        if(stack.size()>1){
            int number2 = stack.pop();
            int number1 = stack.pop();
            if(number2==0 && operator.equals("/")){
                return 2;
            }
            switch (operator){
                case "+" -> stack.push(number1+number2);
                case "-" -> stack.push(number1-number2);
                case "/" -> stack.push(number1/number2);
                case "*" -> stack.push(number1*number2);
            }
            return 1;
        }
        return -1;
    }
    public void processLine(String[] line) {
        String[] operators = {"*", "/", "+", "-"};
        String nextChar;
        if((line.length==1 && line[0].equals("")) || line.length==0)
            return;
        for (int i = 0; i < line.length; i++) {
            nextChar = line[i];
            if(nextChar=="") //line starts with spaces
                continue;
            if(Arrays.asList(operators).contains(nextChar)){
                int successfulOperation = processOperator(nextChar);
                if(successfulOperation==-1){
                    System.out.println("Malformed expression");
                    return;
                }
                if(successfulOperation==2){
                    System.out.println("Zero division");
                    return;
                }
            }
            else{ //next char is integer
                try{
                    int number = Integer.parseInt(nextChar);
                    stack.push(number);
                }
                catch (NumberFormatException ex){
                    System.out.println("Malformed expression");
                    return;
                }
            }
        }
        if(stack.size()!=1) {
            System.out.println("Malformed expression");
            stack.empty();
        }
        else
            System.out.println(stack.pop());
    }
}
