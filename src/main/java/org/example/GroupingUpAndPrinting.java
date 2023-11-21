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
        int max = countMax(quickUnionDS);
        int countOfBigSets = countBigSets(quickUnionDS);
        int [] countArr = countArrWithSetsAmount(quickUnionDS, max);
        WritingFile writingFile = new WritingFile(countOfBigSets, quickUnionDS,  countArr, outputFile, strArray, max);
        writingFile.writing();
    }

    public void groupingSets(ArrayList<String> list, QuickUnionDS quickUnionDS){
        Map<Long, ArrayList<Integer>> map = new HashMap<>(list.size()*2);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).replaceAll("\"", "");
            String[] temp = str.split(";");
            for (int j = 0; j < temp.length; j++) {
                Long value;
                if ("".equals(temp[j])) {
                    value = 0L;
                } else {
                    value = Long.parseLong(temp[j]);
                }
                if (value!=0 && map.containsKey(value) && map.get(value).contains(j) && map.get(value).indexOf(j)%2==0 ) {
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
     public int countMax(QuickUnionDS quickUnionDS){
        int max = 0;
        for (int i = 0; i < quickUnionDS.size.length; i++) {
            if (quickUnionDS.size[i] > max) {
                max = quickUnionDS.size[i];
            }
        }
        return max;
    }

    public int countBigSets(QuickUnionDS quickUnionDS){
        int count = 0;
        for (int i = 0; i < quickUnionDS.size.length; i++) {
        if (quickUnionDS.size[i] > 1) {
            count++;
            }
        }
        return count;
    }
    public int [] countArrWithSetsAmount(QuickUnionDS quickUnionDS, int max){
        int[] counts = new int[max + 1];
        for (int q : quickUnionDS.size) {
            counts[q]++;
        }
        return counts;
    }

}
