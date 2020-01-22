package com.javarush.task.task22.task2208;

import java.util.HashMap;
import java.util.Map;

/* 
Формируем WHERE
*/
public class Solution {
    public static void main(String[] args) {
        Map<String, String> maps = new HashMap<>();
        maps.put("name", "Ivanov");
        maps.put("city", "Kiev");
        maps.put("age", null);
        System.out.println(getQuery(maps));

    }
    public static String getQuery(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for(Map.Entry<String, String> param : params.entrySet()){
            if(param.getValue() != null) {
                if(i != 0){stringBuilder.append("and ");}
                stringBuilder.append(param.getKey() + " = '");
                stringBuilder.append(param.getValue()+ "' ");
            }
            i++;
        }
        return stringBuilder.toString().trim();
    }
}
