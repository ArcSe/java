package com.javarush.task.task21.task2113;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses = new ArrayList<>();
    public static Hippodrome game;

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void run() {
        for(int i=0; i<100; i++){
            move();
            print();
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e){
                e.getStackTrace();
            }
        }
    }
    public void move(){
        for (Horse horse: horses) {
            horse.move();
        }
    }
    public void print(){
        for (Horse horse: horses) {
            horse.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public Horse getWinner(){
        return horses.stream()
                .max((h1, h2) -> Double.compare(h1.getDistance(), h2.getDistance()))
                .get();
    }
    public void printWinner(){
        System.out.println("Winner is " + getWinner().getName() + "!");
    }
    public static void main(String[] args) {
        game = new Hippodrome(new ArrayList<>());
        game.getHorses().add(new Horse("Horse1", 3.0d, 0.0d));
        game.getHorses().add(new Horse("Horse2", 3.0d, 0.0d));
        game.getHorses().add(new Horse("Horse3", 3.0d, 0.0d));

        game.run();
        game.printWinner();
    }
}