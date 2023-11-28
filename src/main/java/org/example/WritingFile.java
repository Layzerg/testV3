package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class WritingFile {
    File outputFile;
    BufferedWriter bw;
    QuickUnionDS quickUnionDS;
    String [] strArray;
    int countOfBigSets;
    int max;
    int groupCounter;

    public WritingFile(int countOfBigSets, QuickUnionDS quickUnionDS, File outputFile, String [] strArray, int max) {
        this.countOfBigSets = countOfBigSets;
        this.quickUnionDS = quickUnionDS;
        this.outputFile = outputFile;
        this.strArray = strArray;
        this.max = max;
        groupCounter = 1;
    }

    public void writingGroups() {
        try {
            bw = new BufferedWriter(new FileWriter(outputFile));
            bw.write(String.valueOf(countOfBigSets));
            bw.newLine();
            HashMap<Integer, ArrayList<Integer>> pMap = quickUnionDS.parentMap();

            List<Map.Entry<Integer, ArrayList<Integer>>> entryList = pMap.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().size()))
                    .collect(Collectors.toList());

            Collections.reverse(entryList);

            StringBuilder output = new StringBuilder();

            for (Map.Entry<Integer, ArrayList<Integer>> entry : entryList) {
                output.append("Группа ").append(groupCounter).append("\n");
                for (Integer i : entry.getValue()) {
                    output.append(strArray[i]).append("\n");
                }
                groupCounter++;
            }
            bw.write(output.toString());
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }
}
