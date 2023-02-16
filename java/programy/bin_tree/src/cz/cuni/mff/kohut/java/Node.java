package cz.cuni.mff.kohut.java;

public class Node {
    public Node(int i) {
        Value = i;
    }

    private Node leftNode;
    private Node rightNode;
    private int Value;

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public int getValue() {
        return Value;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }
}
