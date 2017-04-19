package com.appcrops.workitout;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mraavi on 08/04/17.
 */

public class Workout implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Excercise> getExcercises() {
        return excercises;
    }

    public void setExcercises(ArrayList<Excercise> excercises) {
        this.excercises = excercises;
    }

    public int getNumOfSets() {
        return numOfSets;
    }

    public void setNumOfSets(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    public int getRestDurationBetweenSets() {
        return restDurationBetweenSets;
    }

    public void setRestDurationBetweenSets(int restDurationBetweenSets) {
        this.restDurationBetweenSets = restDurationBetweenSets;
    }

    public int getRestDurationBetweenExercises() {
        return restDurationBetweenExercises;
    }

    public void setRestDurationBetweenExercises(int restDurationBetweenExercises) {
        this.restDurationBetweenExercises = restDurationBetweenExercises;
    }

    public int getWarmUpDuration() {
        return warmUpDuration;
    }

    public void setWarmUpDuration(int warmUpDuration) {
        this.warmUpDuration = warmUpDuration;
    }

    public int getCoolDownDuration() {
        return coolDownDuration;
    }

    public void setCoolDownDuration(int coolDownDuration) {
        this.coolDownDuration = coolDownDuration;
    }

    public  Workout() {
        name = "Challenge";
        excercises = new ArrayList<Excercise>();
        numOfSets = 1;
        restDurationBetweenExercises = 10;
        restDurationBetweenSets = 45;
        warmUpDuration = 0;
        coolDownDuration = 0;
    }

    private String name;
    private ArrayList<Excercise> excercises;
    private int numOfSets;
    private int restDurationBetweenSets;
    private int restDurationBetweenExercises;
    private int warmUpDuration;
    private int coolDownDuration;
}
