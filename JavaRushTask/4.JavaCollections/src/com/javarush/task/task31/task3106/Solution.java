package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        String resultFileName = args[0];
        int filePartCount = args.length - 1;
        String[] fileNameParts = new String[filePartCount];
        for (int i = 0; i < filePartCount; i++) {
            fileNameParts[i] = args[i + 1];
        }

        // sort by file name
        Arrays.sort(fileNameParts);

        List<FileInputStream> fileNameIn = new ArrayList<>();
        for (int i = 0; i < filePartCount; i++) {
            fileNameIn.add(new FileInputStream(fileNameParts[i]));
        }
        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(fileNameIn));

        ZipInputStream in = new ZipInputStream(sequenceInputStream);
        FileOutputStream out =new FileOutputStream(resultFileName);
        byte[] buf = new byte[1024 * 1024]; // 1MB buffer
        while (in.getNextEntry() != null) {
            int count;
            while ((count = in.read(buf)) != -1) {
                out.write(buf, 0, count);
            }
        }
        in.close();
        sequenceInputStream.close();
        out.close();
    }
}
