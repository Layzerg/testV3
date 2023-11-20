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
        int [] countArr = countArr(quickUnionDS, max);
        WritingFile writingFile = new WritingFile(countOfBigSets, quickUnionDS,  countArr, outputFile, strArray, max);
        writingFile.writing();
    }

        public void groupingSets(ArrayList<String> list, QuickUnionDS quickUnionDS){
        Map<Long, ArrayList<Integer>> map = new HashMap<>(list.size());
        for (Integer i = 0; i < list.size(); i++) {
            String str = list.get(i).replaceAll("\"", "");
            String[] temp = str.split(";");
            for (int j = 0; j < temp.length; j++) {
                Long value;
                if ("".equals(temp[j])) {
                    value = 0L;
                } else {
                    value = Long.parseLong(temp[j]);
                }
                if (value != 0L && map.containsKey(value) && !map.get(value).get(0).equals(i) && map.get(value).contains(j) && map.get(value).get(0) != j) {
                    if (!quickUnionDS.isConnected(map.get(value).get(0), i)) {      // в ArrayList'e на позиции 0 стоит индекс строки в которой находится повторяющийся номер,
                        quickUnionDS.connect(map.get(value).get(0), i);             // тк используем сжатие пути, то можно не хранить все интексы строк с совпадениями
                    }                                                               // на остальных позициях находятся индексы номера в самой строке
                }                                                                   // соответсвенно в if'e проверяем что номер содержится в map'e, не в этой же строке,
                ArrayList<Integer> mapValue;                                        // положение в строке совпадает с предыдущим и не равно номеру строки (нулевому элементу листа)
                if (map.get(value) == null) {
                    mapValue = new ArrayList<>();
                    mapValue.add(i);
                    mapValue.add(j);
                } else {
                    map.get(value).set(0, i);
                    mapValue = new ArrayList<>(map.get(value));
                    map.get(value).add(j);
                }
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
    public int [] countArr(QuickUnionDS quickUnionDS, int max){
        int[] counts = new int[max + 1];
        for (int q : quickUnionDS.size) {
            counts[q]++;
        }
        return counts;
    }

}
