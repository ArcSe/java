package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        char[] charArray = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".toCharArray();
        Random r = new Random();
        StringBuilder password = new StringBuilder();
        String s = password.toString();
        while (!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).+", s)) {
            password = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                password.append((charArray[r.nextInt(charArray.length - 1)]));
            }
            s = password.toString();
        }
        byteArrayOutputStream.write(password.toString().getBytes());
        return byteArrayOutputStream;
    }
}