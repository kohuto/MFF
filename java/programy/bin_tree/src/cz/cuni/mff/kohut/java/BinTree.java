package cz.cuni.mff.kohut.java;

import java.util.Iterator;
import java.util.Stack;

interface IBinTree {
    void add(int o);
}
public class BinTree implements IBinTree, Iterable<Integer> {
    Node root;

    public void add(int i) {
        if (root == null) {
            root = new Node(i);
            return;
        }
        Node node = new Node(i);
        Node parent = root;
        Node child = root;
        boolean nodeDirection = true; //true - left, false - right
        int value;
        while (child != null) {
            parent = child;
            value = parent.getValue();
            if (value > i) {
                child = parent.getLeftNode();
                nodeDirection = true;
            } else {
                child = parent.getRightNode();
                nodeDirection = false;
            }
        }
        if (nodeDirection)
            parent.setLeftNode(node);
        else
            parent.setRightNode(node);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Stack<Node> stack = new Stack<Node>();

            {
                pushToLeft(root);
            }

            @Override
            public boolean hasNext() {
                if (stack.empty()) {
                    return false;
                }
                return true;
            }

            @Override
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
        };
    }
}
