package cz.cuni.mff.kohut.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class FileProcessor {
    public FileProcessor(BufferedReader br) {
        this.br = br;
    }
    BufferedReader br;
    String st;
    Deque<int[]> stack = new ArrayDeque<>();
    private void processLine(String line, int lineNumber){
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '[') {
                 int[] element = {lineNumber,i+1};
                stack.push(element);
            } else if (c == ']') {
                if (stack.isEmpty()) {
                    System.out.println("Unopened cycle - line " + lineNumber + " character " + (i+1));
                    System.exit(0);
                }
                stack.pop();
            }
        }
    }

    public String processFile() {
        int lineNumber = 1;
        String code = "";
        while (true) {
            try {
                if (!((st = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            processLine(st, lineNumber);
            code += st;
            lineNumber++;
        }
        if (!stack.isEmpty()) {
            int[] element = stack.pop();
            System.out.println("Unclosed cycle - line " + (element[0]) + " character " + (element[1]));
            System.exit(0);
        }
        return code;
    }
}
