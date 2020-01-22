package com.javarush.task.task35.task3504;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;



public class Solution {
    public static Map<String, Integer> limit = new HashMap<>();
    public static BufferedWriter fileForWriteLimit;
    public static BufferedReader fileForReadLimit;
    public static BufferedWriter writerFioToFile;
    public static BufferedReader readFromFileFio;


    static {
        try {
            fileForWriteLimit = new BufferedWriter(new FileWriter("/home/arc/listLimit.txt"));
            fileForReadLimit = new BufferedReader(new FileReader("/home/arc/listLimit.txt"));
            writerFioToFile = new BufferedWriter(new FileWriter("/home/arc/listUser.txt", true));
            readFromFileFio = new BufferedReader(new FileReader("/home/arc/listUser.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("/home/arc/listLimit.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите ФИО:");
        String fio = reader.readLine();


        String s ;
        Integer sec = 0;
        String last, line;

        if(readFromFileFio.ready()) {
            while (readFromFileFio.ready()) {
                if (readFromFileFio.readLine().equals(fio)) {
                    System.out.println("Пользователь есть в системе");
                    while (fileForReadLimit.ready()) {
                        if ((s = fileForReadLimit.readLine()).contains(fio)) {
                            sec = Integer.parseInt(s.split(" ")[3]);
                            limit.put(fio, sec);
                            while (limit.get(fio) != 0){
                                Thread.sleep(1000);
                                limit.put(fio, sec);
                                StringBuilder sb = new StringBuilder();
                                sec--;
                                for(Map.Entry<String, Integer> set: limit.entrySet()){
                                    sb.append(set.getKey()+ " " + set.getValue() + "\n");
                                }
                                fileForWriteLimit.write(sb.toString());
                                fileForWriteLimit.flush();
                                System.out.println(limit.get(fio));

                            }
                            if(limit.get(fio) == 0){
                                break;
                            }


                        }
                        break;
                    }
                    break;

                } else {
                    System.out.println("Пользователь создан");
                    limit.put(fio, 60);

                    writerFioToFile.write(fio+ "\n");
                    writerFioToFile.flush();
                    fileForWriteLimit.write(fio + " 60\n");
                    fileForWriteLimit.flush();
                }
            }
        }



        fileForWriteLimit.close();


    }


}
