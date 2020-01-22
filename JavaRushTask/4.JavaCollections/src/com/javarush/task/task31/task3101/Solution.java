package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {
    public List<File> listFiles = new ArrayList<>();

    public static void main(String[] args) {
        Solution solution = new Solution();
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        File filerForRename = new File(resultFileAbsolutePath.getParent()+"/allFilesContent.txt" );
        FileUtils.renameFile(resultFileAbsolutePath, filerForRename);

        solution.recurs(path);
        solution.listFiles.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        try{
            FileOutputStream out = new FileOutputStream(filerForRename, true);
            for (File file : solution.listFiles) {
                FileInputStream in = new FileInputStream(file);
                while (in.available() > 0) {
                    out.write(in.read());
                }
                out.write(System.lineSeparator().getBytes());
                out.flush();
                in.close();
            }
            out.close();
        }
        catch (IOException e){

        }





    }

    public void recurs(File dir){

        for (File file :  dir.listFiles()){
            if(file.isFile()&& file.length()<=50){
                listFiles.add(file);
            }
            else if (file.isDirectory()){
                recurs(file.getAbsoluteFile());
            }
        }
    }
}
