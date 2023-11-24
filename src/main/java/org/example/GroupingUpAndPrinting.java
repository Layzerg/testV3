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
    public void processFile(File inputFile){
        ReadingFile readingFile = new ReadingFile(inputFile);
        ArrayList<String> strArray =  readingFile.reading();
        QuickUnionDS quickUnionDS = new QuickUnionDS(strArray.size());
        groupingSets(strArray, quickUnionDS);
        int max = quickUnionDS.countMax();
        int countOfBigSets = quickUnionDS.countSetsWithSizeGreaterThanOne();
        WritingFile writingFile = new WritingFile(countOfBigSets, quickUnionDS, outputFile, strArray, max);
        writingFile.writing();
    }

    private void groupingSets(ArrayList<String> list, QuickUnionDS quickUnionDS){
        Map<String, ArrayList<Integer>> map = new HashMap<>(list.size()*2);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).replaceAll("\"", "");
            StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
            String[] temp = str.split(";");
            for (int j = 0; j < temp.length; j++) {
                String value =temp[j];
                if (!"".equals(value) && map.containsKey(value) && map.get(value).contains(j) && map.get(value).indexOf(j)%2==0 ) {
                    if (!quickUnionDS.isConnected(map.get(value).get(map.get(value).indexOf(j)+1), i)) {          // индекс в строке на чётном месте в листе, номер строки на нечётном, после индекса в строке
                        quickUnionDS.connect(map.get(value).get(map.get(value).indexOf(j)+1), i);
                    }
                }
               ArrayList<Integer> mapValue;
                if (map.get(value) == null) {
                    mapValue = new ArrayList<>();
                } else {
                    mapValue = map.get(value);
                }
                mapValue.add(j);
                mapValue.add(i);
                map.put(value, mapValue);
            }
        }
    }
}
