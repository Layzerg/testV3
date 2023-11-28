package org.example;

import java.io.*;
import java.util.*;


public class GroupingUpAndPrinting {
    File outputFile = new File("output.txt");

    File inputFile;

    public GroupingUpAndPrinting(File inputFile) throws IOException {
        this.inputFile = inputFile;
        processFile(inputFile);
    }

    public void processFile(File inputFile) {
        ReadingFile readingFile = new ReadingFile(inputFile);
        String [] strArray = readingFile.reading();
        QuickUnionDS quickUnionDS = new QuickUnionDS(strArray.length);
        groupingSets(strArray, quickUnionDS);
        int max = quickUnionDS.countMax();
        int countOfBigSets = quickUnionDS.countSetsWithSizeGreaterThanOne();
        WritingFile writingFile = new WritingFile(countOfBigSets, quickUnionDS, outputFile, strArray, max);
        writingFile.writingGroups();
    }

    private void groupingSets(String [] arr, QuickUnionDS quickUnionDS) {
        Map<String, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            String str = arr[i].replaceAll("\"", "");
            String[] temp = str.split(";");
            for (int j = 0; j < temp.length; j++) {
                String value = temp[j];
                if ("".equals(value)) {
                    continue;
                }
                ArrayList<Integer> mapValue;
                if (map.containsKey(value)) {
                    mapValue = map.get(value);
                    int index = map.get(value).indexOf(j);
                    if (index % 2 == 0) {
                        quickUnionDS.connect(mapValue.get(index + 1), i);           // индекс в строке на чётном месте в листе, номер строки на нечётном, после индекса в строке
                    }
                } else {
                    mapValue = new ArrayList<>(2);
                }
                mapValue.add(j);
                mapValue.add(i);
                map.put(value, mapValue);
            }
        }
    }
}
