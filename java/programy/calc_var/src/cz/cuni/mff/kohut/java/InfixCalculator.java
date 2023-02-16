package cz.cuni.mff.kohut.java;

import java.util.*;

public class InfixCalculator {
    Stack<Character> operatorStack = new Stack<>();
    private boolean isOperator(char potOperator){
        Character[] operators = {'*', '/', '+', '-'};
        List<Character> operatorsList = Arrays.asList(operators);
        if(operatorsList.contains(potOperator))
            return true;
        return false;
    }

    public boolean hasPrecedence(char operator1, char operator2) {
        // Define a map of operator precedence values
        Map<Character, Integer> operatorPrecedence = new HashMap<>();
        operatorPrecedence.put('+', 1);
        operatorPrecedence.put('-', 1);
        operatorPrecedence.put('*', 2);
        operatorPrecedence.put('/', 2);
        operatorPrecedence.put('^', 3);

        // Get the precedence values for the two operators
        int operator1Precedence = operatorPrecedence.getOrDefault(operator1, 0);
        int operator2Precedence = operatorPrecedence.getOrDefault(operator2, 0);

        // Return true if operator1 has higher precedence than operator2, and false otherwise
        return operator1Precedence > operator2Precedence;
    }

    // This method converts the given infix expression to postfix
    public String ConvertToPostfix(String infix) {
        // Create a string builder to store the postfix expression
        StringBuilder postfix = new StringBuilder();

        // Loop through the infix expression one character at a time
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            // If the character is an operand (i.e. a number),
            // append it to the postfix expression
            if (Character.isDigit(c)) {
                postfix.append(c);
            }
            // If the character is an operator,
            // pop operands from the operand stack and operators from the operator stack
            // until the operator on the top of the operator stack has lower precedence than the character
            // and then push the character onto the operator stack
            else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(' && hasPrecedence(operatorStack.peek(), c)) {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.push(c);
            }
            // If the character is a left parentheses, push it onto the operator stack
            else if (c == '(') {
                operatorStack.push(c);
            }
            // If the character is a right parentheses,
            // pop operands from the operand stack and operators from the operator stack
            // until the left parentheses is found and then discard it
            else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop());
                }
                operatorStack.pop();
            }
        }

        // Pop any remaining operators from the operator stack and append them to the postfix expression
        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        // Return the postfix expression as a string
        return postfix.toString();
    }
}
