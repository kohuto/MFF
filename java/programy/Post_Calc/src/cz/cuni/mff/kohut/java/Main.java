
package cz.cuni.mff.kohut.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        String line;
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()){
            line =  input.nextLine();
            String[] splitedLine = line.split("\\s+");
            c.processLine(splitedLine);
        }
    }
}
