package com.javarush.task.task35.task3509;

import java.io.*;
import java.util.*;



public class Solution<T> {
    public static void block(BufferedReader readerListFile, Map<String, Boolean> startState ) throws IOException {
        
        //Изменение прав на файлы
        while (readerListFile.ready()) {
            String file;
            if(new File(file = readerListFile.readLine()).exists()) {
                Runtime.getRuntime().exec(new String("chmod 111 " + file));
                Runtime.getRuntime().exec(new String("chattr +i " + file));
                startState.put(file, true);
            }
            else{
                startState.put(file, false);
            }
        }
        //Бессконечный цикл проврки создания новых файлов
        while(true){
            for(Map.Entry<String, Boolean> fileInList: startState.entrySet()){
                if(!fileInList.getValue()){
                    Runtime.getRuntime().exec("rm " + fileInList.getKey());

                }
            }

        }
    }
    public static void unblock(BufferedReader readerListFile, Map<String, Boolean> startState ) throws IOException {
        //Изменение прав на файлы
        while (readerListFile.ready()) {
            String file;
            if(new File(file = readerListFile.readLine()).exists()) {
                Runtime.getRuntime().exec(new String("chmod 777 " + file));
                Runtime.getRuntime().exec(new String("chattr -i " + file));
                startState.put(file, true);
            }
            else{
                startState.put(file, false);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        // Путь до файла-списока для определения запрещенных файлов
        String pathList = "/home/1lab/list.txt";

        //Создание вспомогательных переменных
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader readerListFile = new BufferedReader(new FileReader(pathList));
        //Список для сохранения кол-ва созданных файлов
        Map<String, Boolean> startState = new HashMap<String, Boolean>();

        //Выполнение команд в консоли для бехопасности файла-списка
        Runtime.getRuntime().exec(new String("chmod 444 " + pathList));
        Runtime.getRuntime().exec(new String("chattr +i " + pathList));

        System.out.println("Напишите 1-заблокировать файлы, 2-разблокировать файлы");
        try {
            int query = Integer.parseInt(reader.readLine());
            if (query==1){
                block(readerListFile, startState);
            }
            else if(query ==2){
                unblock(readerListFile, startState);
            }
            else {
                throw new Exception();
            }
        }catch (Exception e){
            System.out.println("Нужно было написать 1 или 2, перезапустите программу и напишите 1 или 2");
        }


    }
}
