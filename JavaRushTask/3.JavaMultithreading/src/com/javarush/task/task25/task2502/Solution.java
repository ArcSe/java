package com.javarush.task.task25.task2502;

import org.jsoup.safety.Whitelist;

import java.util.*;

/* 
Машину на СТО не повезем!
*/
public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels;

        public Car() throws Exception{
            wheels = new ArrayList();
            String[] strings = loadWheelNamesFromDB();
            if(strings.length==4){
                for (String str: strings) {
                    wheels.add(Wheel.valueOf(str));
                }
            }
            else{
                throw new Exception();
            }
            //init wheels here
        }

        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_LEFT"};
        }
    }

    public static void main(String[] args) throws Exception{
        Car car = new Car();
        System.out.println(car.wheels);
    }
}
