package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(args[0], "rw");
        raf.seek(Long.parseLong(args[1]));
        byte[] buf = new byte[args[2].length()];
        raf.read(buf, 0, args[2].length());
        if(args[2].equals(new String(buf))){
            raf.seek(raf.length());
            raf.write("true".getBytes());
        }
        else {
            raf.seek(raf.length());
            raf.write("false".getBytes());
        }

    }
}
