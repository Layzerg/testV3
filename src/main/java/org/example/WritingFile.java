package org.example;

import java.io.*;
import java.util.*;

public class WritingFile {
    File outputFile;
    BufferedWriter bw;
    QuickUnionDS quickUnionDS;
    ArrayList<String> strArray;
    int countOfBigSets;
    int max;
    int groupCounter;

    public WritingFile(int countOfBigSets, QuickUnionDS quickUnionDS, File outputFile, ArrayList<String> strArray, int max) {
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

            List<Map.Entry<Integer, ArrayList<Integer>>> entryList = new ArrayList<>(pMap.entrySet());
            entryList.sort(Comparator.comparingInt(entry -> entry.getValue().size()));
            Collections.reverse(entryList);

            for (Map.Entry<Integer, ArrayList<Integer>> entry : entryList) {
                bw.write("Группа " + groupCounter);
                bw.newLine();
                for (Integer i : entry.getValue()) {
                    bw.write(strArray.get(i));
                    bw.newLine();
                }
                groupCounter++;
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
