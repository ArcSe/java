package com.javarush.task.task22.task2202;

/* 
Найти подстроку
*/
public class Solution {
    public static void main(String[] args) throws TooShortStringException {
        System.out.println(getPartOfString("JavaRush - лучший сервис обучения Java."));
    }

    public static String getPartOfString(String string) throws TooShortStringException{
        try {
            if (string == null) {
                throw new TooShortStringException();
            }
            String[] parse = string.split(" ");
            return parse[1]+" " + parse[2]+" " + parse[3]+" " + parse[4];
        }
        catch (Exception e){
            throw new TooShortStringException();
        }
    }

    public static class TooShortStringException extends RuntimeException {
    }
}
