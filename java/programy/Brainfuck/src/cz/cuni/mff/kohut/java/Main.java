package cz.cuni.mff.kohut.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        int DEFAULT_MEMORY_SIZE = 30000;
        int memorySize;
        if(args.length==2)
            memorySize = Integer.parseInt(args[1]);
        else
            memorySize = DEFAULT_MEMORY_SIZE;

        File file = new File(args[0]);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        FileProcessor fp = new FileProcessor(br);
        String code;

        //check incorrect cycle and make from file code in one string
        code = fp.processFile();
        BrainFuckInterpreter bfi = new BrainFuckInterpreter();
        //interpret code
        bfi.interpret(code,memorySize);
    }
}