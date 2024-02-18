package com.yaremax;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "10m.txt";

        long start = System.currentTimeMillis();
        foo(path);
        long finish = System.currentTimeMillis();

        System.out.println("Time took: " + (finish - start));
    }

    public static void foo(String path) {
        List<Long> list = new ArrayList<>();
        int listSize = 0;
        Long min = null;
        Long max = null;
        float median = 0;
        float average = 0;

        List<Long> longestAsc = new ArrayList<>();
        List<Long> currentAsc = new ArrayList<>();
        List<Long> longestDesc = new ArrayList<>();
        List<Long> currentDesc = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line;
            long number;
            float listSum = 0;

            while ((line = br.readLine()) != null) {
                number = Long.parseLong(line);
                list.add(number);

                listSum += number;

                if (max == null || number > max) {
                    max = number;
                }
                if (min == null || number < min) {
                    min = number;
                }

                if (currentAsc.isEmpty() || number > currentAsc.get(currentAsc.size() - 1)) {
                    currentAsc.add(number);
                } else {
                    currentAsc = new ArrayList<>();
                    currentAsc.add(number);
                }

                if (currentDesc.isEmpty() || number < currentDesc.get(currentDesc.size() - 1)) {
                    currentDesc.add(number);
                } else {
                    currentDesc = new ArrayList<>();
                    currentDesc.add(number);
                }

                if (currentAsc.size() > longestAsc.size()) longestAsc = new ArrayList<>(currentAsc);
                if (currentDesc.size() > longestDesc.size()) longestDesc = new ArrayList<>(currentDesc);

            }

            listSize = list.size();

            average = listSum / listSize;

            if (listSize % 2 == 0) {
                float halfSum = (list.get(listSize / 2) +
                        list.get(listSize / 2 - 1));
                median = halfSum / 2;
            }
            else {
                median = Float.valueOf(list.get(listSize / 2));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("min = " + min);
        System.out.println("max = " + max);
        System.out.println("median = " + median);
        System.out.println("average = " + average);
        System.out.println("longest ascending sequence = " + longestAsc);
        System.out.println("longest descending sequence = " + longestDesc);
    }
}