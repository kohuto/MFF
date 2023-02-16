package cz.cuni.mff.kohut.java;

import java.io.*;
public class Main {
    public static void main(String[] args){
        File file = new File(args[0]);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        FileProcessor fp = new FileProcessor(br);
        fp.processFile();
        fp.printData();
    }
}