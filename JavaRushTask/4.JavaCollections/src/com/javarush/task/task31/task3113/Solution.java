package com.javarush.task.task31.task3113;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution  {
    private static int amountFiles=0;
    private static int amountDirectory = -1;
    private static int sum = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filePathStr = reader.readLine();
        reader.close();
        Path file = Paths.get(filePathStr);

        Files.walkFileTree(file,new Visitior());
        if(!Files.isDirectory(file)){
            System.out.println(file.toAbsolutePath().toString() + " - не папка");
            return;
        }
        System.out.println("Всего папок - " + amountDirectory);
        System.out.println("Всего файлов - " + amountFiles);
        System.out.println("Общий размер - " + sum);
    }

    private static class Visitior extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            amountFiles++;
            sum += attrs.size();

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            amountDirectory++;

            return FileVisitResult.CONTINUE;
        }
    }
}
