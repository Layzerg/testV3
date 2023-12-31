package org.example;

import java.io.*;
import java.nio.file.Files;
import java.util.*;


public class ReadingFile {
    File inputFile;

    public ReadingFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public String[] reading() {
        HashSet<String> set = new HashSet<>();
        InputStreamReader inputStreamReader;
        BufferedReader br;
        try {
            inputStreamReader = new InputStreamReader(Files.newInputStream(inputFile.toPath()));
            br = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
        try {
            while (br.ready()) {
                String input = br.readLine().trim();
                set.add(input);
            }
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        try {
            inputStreamReader.close();
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String arr[] = new String[set.size()];
        arr = set.toArray(arr);
        return arr;
    }
}
