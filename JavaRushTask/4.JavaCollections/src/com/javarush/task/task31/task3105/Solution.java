package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static Map<String, ByteArrayOutputStream> map = new HashMap<>();
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        String zipFileName = args[1];
        File fileIn = new File(fileName);
        zipToMap(zipFileName);
        addFileToZip(zipFileName, fileIn);

    }

    public static void zipToMap(String zipFileName) throws IOException {
        try(ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int count = 0;
                byte[] buffer = new byte[1024];
                while ((count = in.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, count);
                }
                map.put(entry.getName(), byteArrayOutputStream);
            }
        }
        catch (Exception e){
            e.getCause();
        }


    }

    public static void addFileToZip(String zipFileName, File fileIn) throws IOException {
        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            FileInputStream in = new FileInputStream(fileIn);) {

            ZipEntry comparatorEntry = new ZipEntry(fileIn.getName());
            for (Map.Entry<String, ByteArrayOutputStream> mapEntry : map.entrySet()) {

                if (!mapEntry.getKey().substring(mapEntry.getKey().lastIndexOf("/") + 1).equals(comparatorEntry.getName())) {
                    out.putNextEntry(new ZipEntry(mapEntry.getKey()));
                    out.write(mapEntry.getValue().toByteArray());
                } else {
                    ZipEntry addEntry = new ZipEntry("new/" + fileIn.getName());
                    out.putNextEntry(addEntry);
                    Files.copy(fileIn.toPath(), out);
                }

            }
        }
        catch (Exception e){
            e.getCause();
        }
    }
}
