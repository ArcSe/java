package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {/*
        Integer[] massive = new Integer[]{13, 8, 15, 5, 17};
        for (Integer integer : massive) {
            System.out.println(integer);
        }
        sort(massive);
        System.out.println();
        for (Integer integer : massive) {
            System.out.println(integer);
        }
       */

    }

    public static Integer[] sort(Integer[] array) {
        Arrays.sort(array);
        int lenghtHulf = array.length/2;
        int mediana = array.length%2==0? (array[lenghtHulf] + array[lenghtHulf-1])/2: array[lenghtHulf];
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                Integer del1 = Math.abs(o1 - mediana);
                Integer del2 = Math.abs(o2 - mediana);
                return del1.compareTo(del2);
            }
        };
        Arrays.sort(array, comparator);
        //implement logic here
        return array;
    }
}
