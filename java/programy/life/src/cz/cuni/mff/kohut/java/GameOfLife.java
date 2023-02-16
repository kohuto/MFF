package cz.cuni.mff.kohut.java;
public class GameOfLife {
    private Cell[][] cells;
    private int size;

    public GameOfLife(int size) {
        this.size = size;
        cells = new Cell[size][size];
    }

    public void setCell(int row, int col, boolean alive) {
        cells[row][col] = new Cell(alive);
    }

    public Cell getCell(int row, int col){
        return cells[row][col];
    }

    public void step() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col].update(getNumLiveNeighbors(row, col));
            }
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col].PreAliveIntoAlive();
            }
        }
    }

    public int getNumLiveNeighbors(int row, int col) {
        int numLiveNeighbors = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int neighborRow = (row + i + size) % size;
                int neighborCol = (col + j + size) % size;
                if (cells[neighborRow][neighborCol].isAlive()) {
                    numLiveNeighbors++;
                }
            }
        }
        if (cells[row][col].isAlive()) {
            numLiveNeighbors--;
        }
        return numLiveNeighbors;
    }
}