package cz.cuni.mff.kohut.java;

public class Main {
    public static void main(String[] args) {
        int i;
        BinTree tree = new BinTree();

        try {
            for (String arg : args) {
                i = Integer.parseInt(arg);
                tree.add(i);
            }
        } catch(Exception e){
            System.out.println("INPUT ERROR");
            return;
        }

        for (Integer integer : tree) {
            System.out.println(integer);
        }
    }
}
