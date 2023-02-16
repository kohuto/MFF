package cz.cuni.mff.kohut.java;

public class Cell {
    private boolean alive;
    private boolean prealive;

    public Cell(boolean alive) {
        this.alive = alive;
    }

    public void update(int numNeighbors) {
        if (alive) {
            if (numNeighbors < 2 || numNeighbors > 3) {
                prealive = false;
            }
            else
                prealive = true;
        } else {
            if (numNeighbors == 3) {
                prealive = true;
            }
            else
                prealive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void PreAliveIntoAlive(){
        alive = prealive;
    }
}

