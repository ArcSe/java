package com.javarush.task.task29.task2909.car;

public class Cabriolet extends Car {
    @Override
    public int getMaxSpeed() {
        return Car.MAX_CABRIOLET_SPEED;
    }

    public Cabriolet(int numberOfPassengers) {
        super(CABRIOLET, numberOfPassengers);
    }
}
