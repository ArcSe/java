package com.javarush.task.task22.task2203;

import org.w3c.dom.ls.LSOutput;

/*
Между табуляциями
*/
public class Solution {
    public static String getPartOfString(String string) throws TooShortStringException {

        try {
            if(string ==null){
                throw new Exception();
            }
            int match = string.length() - string.replaceAll("\t","").length();
            if(match < 2 ) throw new TooShortStringException();
            String[] parse = string.split("\t");
            return parse[1];
        }
        catch (Exception e) {
            throw new TooShortStringException();
        }

    }

    public static class TooShortStringException extends Exception {
    }

    public static void main(String[] args) throws TooShortStringException {
        System.out.println(getPartOfString("\tJavaRush - лучший сервис обучения Java\t."));
    }
}
