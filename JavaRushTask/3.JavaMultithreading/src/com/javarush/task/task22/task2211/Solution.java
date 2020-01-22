package com.javarush.task.task22.task2211;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* 
Смена кодировки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Charset UTF8 = StandardCharsets.UTF_8;
        Charset windows1251 = Charset.forName("Windows-1251");

        FileInputStream inputStream = new FileInputStream(args[0]);
        FileOutputStream outputStream = new FileOutputStream(args[1]);
        //чтение из файла
        File file = new File(args[0]);
        long length = file.length();
        byte[] in = new byte[(int)length];
        while (inputStream.available()>0){
            inputStream.read(in);
        }
        inputStream.close();

        //перекодировка
        String buf = new String(in,UTF8);
        in = buf.getBytes(windows1251);
        //чтение в файл
        outputStream.write(in);
        outputStream.close();


    }
}
