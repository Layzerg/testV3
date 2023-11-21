package org.example;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if(args.length!=1 || !args[0].endsWith(".txt")){
            System.out.println("Wrong args");
            System.exit(1);
        }
        long startTime = System.currentTimeMillis();
        File inputFile = new File(args[0]);
        try {
            GroupingUpAndPrinting puap = new GroupingUpAndPrinting(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("Время выполнения " + endTime / 1000 + " c");
    }
}