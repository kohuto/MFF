package cz.cuni.mff.kohut.java;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = new PrintStream(System.out);

        int size = in.nextInt();
        int numSteps = in.nextInt();
        in.nextLine();

        GameOfLife game = new GameOfLife(size);
        for (int row = 0; row < size; row++) {
            String line = in.nextLine();
            for (int col = 0; col < size; col++) {
                char c = line.charAt(col);
                boolean alive = (c == 'X');
                game.setCell(row, col, alive);
            }
        }

        for (int i = 0; i < numSteps; i++) {
            game.step();
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                out.print(game.getCell(row,col).isAlive() ? 'X' : '_');
            }
            out.println();
        }
    }
}