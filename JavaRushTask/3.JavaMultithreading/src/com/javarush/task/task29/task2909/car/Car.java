package com.javarush.task.task29.task2909.car;


import java.util.Date;

public abstract class  Car {
    static public final int TRUCK = 0;
    static public final int SEDAN = 1;
    static public final int CABRIOLET = 2;

    public static final int MAX_TRUCK_SPEED = 80;
    public static final int MAX_SEDAN_SPEED = 120;
    public static final int MAX_CABRIOLET_SPEED = 90;

    double fuel;

    public double summerFuelConsumption;
    public double winterFuelConsumption;
    public double winterWarmingUp;

    private int type;

    private boolean driverAvailable;
    private int numberOfPassengers;

    public static Car create(int type, int numberOfPassengers){
        if(type == 0) {
            return new Truck( numberOfPassengers);
        }
        else if(type ==1){
            return new Sedan(numberOfPassengers);
        }
        else if(type == 2){
            return new Cabriolet(numberOfPassengers);
        }
        return null;
    }

    protected Car(int type, int numberOfPassengers) {
        this.type = type;
        this.numberOfPassengers = numberOfPassengers;
    }

    public boolean isSummer(Date date, Date summerStart, Date summerEnd){
        if(date.getTime()>summerStart.getTime() && date.getTime()<summerEnd.getTime()){
            return true;
        }
        return false;
    }

    private boolean canPassengersBeTransferred(){
        if(isDriverAvailable() && fuel>0){
            return true;
        }
        return  false;
    }

    public double getWinterConsumption(int length){
        return length * winterFuelConsumption +winterWarmingUp;
    }

    public double getSummerConsumption(int length){
        return length * summerFuelConsumption;
    }

    public void fill(double numberOfLiters) throws Exception {
        if(numberOfLiters>0){
            fuel += numberOfLiters;
        }
        else {
            throw new Exception();
        }
    }

    public double getTripConsumption(Date date, int length, Date SummerStart, Date SummerEnd) {
        double consumption;
        if (!isSummer(date, SummerStart, SummerEnd)) {
            consumption = getWinterConsumption(length);
        } else {
            consumption = length * getSummerConsumption(length);
        }
        return consumption;
    }

    public int getNumberOfPassengersCanBeTransferred() {
        if (!canPassengersBeTransferred())
            return 0;

        return numberOfPassengers;
    }

    public boolean isDriverAvailable() {
        return driverAvailable;
    }

    public void setDriverAvailable(boolean driverAvailable) {
        this.driverAvailable = driverAvailable;
    }

    public void startMoving() {
        if (numberOfPassengers > 0) {
            fastenPassengersBelts();
        }
        fastenDriverBelt();

    }

    public void fastenPassengersBelts() {
    }

    public void fastenDriverBelt() {
    }


    public abstract int getMaxSpeed();
}