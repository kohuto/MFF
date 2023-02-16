package cz.cuni.mff.kohut.java;

import java.util.Iterator;
import java.util.Stack;

public class BinTreeIterator implements Iterator {
    Stack<Node> stack = new Stack<Node>();

    public BinTreeIterator(Node root) {
        pushToLeft(root);
    }

    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        }
        return true;
    }

    public Integer next() {
        Node node = stack.pop();
        pushToLeft(node.getRightNode());
        return node.getValue();
    }

    private void pushToLeft(Node node) {
        if (node != null) {
            stack.push(node);
            pushToLeft(node.getLeftNode());
        }
    }
}
