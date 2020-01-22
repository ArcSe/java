package com.javarush.task.task29.task2909.car;

public class Truck extends Car {
    @Override
    public int getMaxSpeed() {
        return Car.MAX_TRUCK_SPEED;
    }

    public Truck(int numberOfPassengers) {
        super(TRUCK, numberOfPassengers);
    }
}
