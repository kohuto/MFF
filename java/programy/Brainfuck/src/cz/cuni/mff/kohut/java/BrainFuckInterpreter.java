package cz.cuni.mff.kohut.java;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
public class BrainFuckInterpreter {
    BufferedReader reader;
    public BrainFuckInterpreter(){
        reader = new BufferedReader(new InputStreamReader(System.in));;
    }

    public void interpret(String code, int memorySize) {
        char[] memory = new char[memorySize];
        int pointer = 0;
        int instructionPointer = 0;
        // initilize stack for save position of [
        Deque<Integer> stack = new ArrayDeque<>();

        while (instructionPointer < code.length()) {
            char instruction = code.charAt(instructionPointer);
            switch (instruction) {
                case '<':
                    pointer--;
                    // pointer is out of code
                    if (pointer < 0) {
                        System.out.println("Memory underrun");
                        return;
                    }
                    break;
                case '>':
                    pointer++;
                    // pointer is out of code
                    if (pointer >= memorySize) {
                        System.out.println("Memory overrun");
                        return;
                    }
                    break;
                case '+':
                    memory[pointer]++;
                    break;
                case '-':
                    memory[pointer]--;
                    break;
                case '.':
                    System.out.print(memory[pointer]);
                    break;
                case ',':
                    try {
                        memory[pointer] = (char) reader.read();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case '[':
                    if (memory[pointer] == 0) {
                        int counter = 1;
                        while (counter > 0) {
                            instructionPointer++;
                            if (code.charAt(instructionPointer) == '[') {
                                counter++;
                            } else if (code.charAt(instructionPointer) == ']') {
                                counter--;
                            }
                        }
                    } else {
                        stack.push(instructionPointer);
                    }
                    break;
                case ']':
                    if (memory[pointer] != 0) {
                        instructionPointer = stack.peek();
                    } else {
                        stack.pop();
                    }
                    break;
            }
            instructionPointer++;
        }
    }
}


