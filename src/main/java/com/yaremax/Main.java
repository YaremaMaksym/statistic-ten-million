package com.yaremax;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String path = "10m.txt";

        long start = System.currentTimeMillis();
        foo(path);
        long finish = System.currentTimeMillis();

        System.out.println("Time took: " + (finish - start));
    }

    public static void foo(String path) {
        Long min = null;
        Long max = null;
        double median = 0;
        double average = 0;

        List<Long> longestAsc = new ArrayList<>();
        List<Long> longestDesc = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            PriorityQueue<Long> lowerHalf = new PriorityQueue<>(Comparator.reverseOrder()); // max heap
            PriorityQueue<Long> upperHalf = new PriorityQueue<>(); // min heap

            List<Long> currentAsc = new ArrayList<>();
            List<Long> currentDesc = new ArrayList<>();

            String line;
            long number;
            int listSize;
            double listSum = 0;

            while ((line = br.readLine()) != null) {
                number = Long.parseLong(line);

                listSum += number;


                // get min and max
                if (max == null || number > max) {
                    max = number;
                }
                if (min == null || number < min) {
                    min = number;
                }


                // get longest ascending and descending sequences
                if (currentAsc.isEmpty() || number > currentAsc.getLast()) {
                    currentAsc.add(number);
                } else {
                    currentAsc = new ArrayList<>();
                    currentAsc.add(number);
                }

                if (currentDesc.isEmpty() || number < currentDesc.getLast()) {
                    currentDesc.add(number);
                } else {
                    currentDesc = new ArrayList<>();
                    currentDesc.add(number);
                }

                if (currentAsc.size() > longestAsc.size()) longestAsc = new ArrayList<>(currentAsc);
                if (currentDesc.size() > longestDesc.size()) longestDesc = new ArrayList<>(currentDesc);



                // Add number to one of the heaps (for getting median)
                if (lowerHalf.isEmpty() || number <= lowerHalf.peek()) {
                    lowerHalf.add(number);
                } else {
                    upperHalf.add(number);
                }

                // Balance the heaps (for getting median)
                while (lowerHalf.size() < upperHalf.size()) {
                    lowerHalf.add(upperHalf.poll());
                }
                while (lowerHalf.size() > upperHalf.size() + 1) {
                    upperHalf.add(lowerHalf.poll());
                }
            }

            // get median
            if (lowerHalf.size() > upperHalf.size()) {
                median = lowerHalf.peek();
            } else {
                median = (lowerHalf.peek() + upperHalf.peek()) / 2.0;
            }

            // get average
            listSize = lowerHalf.size() + upperHalf.size();
            average = listSum / listSize;

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