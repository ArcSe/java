package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        try {
            if (args[0].matches("\\w*")) {

                for (int j = 2; j < 37; j++) {
                    try {
                        new BigInteger(args[0], j);
                        System.out.println(j);
                        break;
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                }
            }
            else {
                System.out.println("incorrect");
            }
        }
        catch (Exception e){
                e.getStackTrace();
        }


    }      //напишите тут ваш код
}