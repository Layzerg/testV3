package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WritingFile {
    BufferedWriter bw;
    int countOfBigSets;
    QuickUnionDS quickUnionDS;
    int[] countArr;
    File outputFile;
    ArrayList<String> strArray;
    int max;
    int threshold;
    int groupCounter;

    public WritingFile(int countOfBigSets, QuickUnionDS quickUnionDS, int[] countArr, File outputFile, ArrayList<String> strArray, int max) {
        this.countOfBigSets = countOfBigSets;
        this.quickUnionDS = quickUnionDS;
        this.countArr = countArr;
        this.outputFile = outputFile;
        this.strArray = strArray;
        this.max = max;
        threshold = countArr.length - 1;
        groupCounter = 1;
    }
    public void writing() {
        writingBigGroups();
        writingGroupsWith1Size();
    }
    public void writingBigGroups() {
        try {
            bw = new BufferedWriter(new FileWriter(outputFile));
            bw.write(String.valueOf(countOfBigSets));
            bw.newLine();
            List<Integer> parentsList = Arrays.stream(quickUnionDS.parent)
                    .boxed()
                    .collect(Collectors.toList());

            for (int i = 0; i < strArray.size() && max > 1; i++) {
                if (quickUnionDS.size[i] == max) {
                    int[] printArr = indexesOfSet(parentsList, i);
                    if (printArr.length != 0) {
                        bw.write("Группа " + groupCounter);
                        bw.newLine();
                        for (int t = 0; t < printArr.length; t++) {
                            bw.write(strArray.get(printArr[t]));
                            quickUnionDS.size[printArr[t]] = 0;
                            strArray.set(printArr[t], null);
                            parentsList.set(printArr[t], null);
                            bw.newLine();
                        }
                        groupCounter++;
                    }
                }
                if (groupCounter - 1 == countArr[threshold]) {
                    max--;
                    i = 0;
                    countArr[threshold - 1] += countArr[threshold];
                    threshold--;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writingGroupsWith1Size() {
        try {
            for (String s : strArray) {
                if (s != null) {
                    bw.write("Группа " + groupCounter);
                    groupCounter++;
                    bw.newLine();
                    bw.write(s);
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        }
        catch (IOException e){
            System.out.println("IOException");
        }

    }

    public static int[] indexesOfSet(List<Integer> list, int value) {
        return IntStream.range(0, list.size())
                .filter(i -> Objects.equals(value, list.get(i)))
                .toArray();
    }

}
