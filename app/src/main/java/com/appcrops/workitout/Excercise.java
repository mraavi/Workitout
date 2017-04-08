package com.appcrops.workitout;

/**
 * Created by mraavi on 08/04/17.
 */

public class Excercise {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Excercise() {
        this.name = "Excercise";
        this.duration = 30;
    }

    public Excercise(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public Excercise(String name) {
        this.name = name;
        this.duration = 30;
    }

    public Excercise(Excercise excercise) {
        this.name = excercise.getName();
        this.duration = excercise.getDuration();
    }

    private String name;
    private int duration;
}
