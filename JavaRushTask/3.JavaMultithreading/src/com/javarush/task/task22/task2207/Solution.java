package com.javarush.task.task22.task2207;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/* 
Обращенные слова
*/
public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader1 = new BufferedReader(new FileReader(reader.readLine()));

        StringBuilder stringBuilder = new StringBuilder();
        while (reader1.ready()){
            stringBuilder.append(reader1.readLine());
            stringBuilder.append(" ");
        }

        String[] strToPart = stringBuilder.toString().split(" ");
        ArrayList<String> words = new ArrayList<>(Arrays.asList(strToPart));

        reader.close();
        reader1.close();
        int k=0;
        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < words.size(); j++) {
                StringBuilder strForCheck = new StringBuilder(words.get(j));
                if(i != j && words.get(i).equals(strForCheck.reverse().toString())){
                    Pair pair1 = new Pair();
                    pair1.first = words.get(i);
                    pair1.second = words.get(j);
                    Pair pair2 = new Pair();
                    pair2.second = pair1.first;
                    pair2.first = pair1.second;
                    if (!result.contains(pair1) && !result.contains(pair2)) {
                        result.add(pair1);}
                }
            }
        }




        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).toString());
        }


    }

    public static class Pair {
        String first;
        String second;

        public Pair() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  first == null && second == null ? "" :
                        first == null ? second :
                            second == null ? first :
                                first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
