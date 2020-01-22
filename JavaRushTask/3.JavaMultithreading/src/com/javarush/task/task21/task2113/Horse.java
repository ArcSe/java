package com.javarush.task.task21.task2113;

public class Horse {
    private  String name;
    private  double speed;
    private  double distance;

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Horse(String name, double speed, double distance) {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
    }
    public void move(){
        this.distance += this.speed*Math.random();
    }
    public void print(){
        int valueDistance = (int) Math.floor(distance);
        for (int i = 0; i < valueDistance; i++) {
            System.out.print(".");
        }
        System.out.print(this.name);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
