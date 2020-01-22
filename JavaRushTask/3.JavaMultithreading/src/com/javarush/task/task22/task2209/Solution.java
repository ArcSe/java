package com.javarush.task.task22.task2209;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
Составить цепочку слов
*/
public class Solution {
    private ArrayList<String> stroka = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader fr = new BufferedReader(new FileReader(reader.readLine()));
        String txt;
        String stroka="";
        while ((txt=fr.readLine())!=null){
            stroka+=txt+" ";
        }
        reader.close();
        fr.close();
        String[] words = stroka.split(" ");
        //...
        StringBuilder result = getLine(words);
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        if(words == null || words.length == 0) return new StringBuilder("");
        if(!words[0].isEmpty() && words.length == 1) return  new StringBuilder(words[0]);

        ArrayList<String> base = new ArrayList<String>();
        Collections.addAll(base, words);
        Collections.sort(base);

        Solution solution = new Solution();

        for (int i = 0; i <base.size() ; i++) {
            ArrayList<String> list = new ArrayList<>();
            ArrayList<String> remlist = new ArrayList<>(base);
            list.add(remlist.get(i));
            remlist.remove(i);
            rekurs(list,remlist,solution);
        }
        StringBuilder builder = new StringBuilder();
        if (solution.stroka.size()>1){
            for (int i = 0; i <solution.stroka.size() ; i++) {
                builder.append(solution.stroka.get(i));
                if (i!=solution.stroka.size()-1){
                    builder.append(" ");
                }
            }}

            for (int i = 0; i < base.size(); i++){
                int count = 0;
                for (int j = 0; j < solution.stroka.size(); j++) {
                    if (base.get(i).equals(solution.stroka.get(j))) {
                        count++;
                    }
                }
                if(count == 0) {
                    builder.append(" " + base.get(i));
                }
            }


        
        return builder;
    }

    public static void rekurs (ArrayList<String> list, ArrayList<String> remlist,Solution solution){
        String lastWRD = list.get(list.size()-1);
        String last = lastWRD.substring(lastWRD.length()-1);
        ArrayList<Integer> idList = new ArrayList<>();
        for (int i = 0; i <remlist.size() ; i++) {
            String first = remlist.get(i).substring(0,1);
            if (first.equalsIgnoreCase(last)){
                idList.add(i);
            }
        }

        for (int i = 0; i <idList.size() ; i++) {
            int id = idList.get(i);
            ArrayList<String> line = new ArrayList<>(list);
            ArrayList<String> remLine = new ArrayList<>(remlist);
            line.add(remLine.get(id));
            remLine.remove(id);
            rekurs(line,remLine,solution);

        }
        if (solution.stroka.size()<list.size()){solution.stroka=list;}


    }
}