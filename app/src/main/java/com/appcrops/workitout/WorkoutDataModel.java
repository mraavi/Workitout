package com.appcrops.workitout;

import android.content.Context;

/**
 * Created by mraavi on 08/04/17.
 */

public class WorkoutDataModel {
    public static enum Group{
        WORKOUT_NAME("", 1),
        NUM_OF_SETS("", 1),
        EXCERCISES("Excercise", 1),
        ADD_EXCERCISES("", 1),
        REST("", 2),
        WARPUP("", 2);

        public final static int COUNT = 6;
        public String name;
        public int numOfchildren;
        private Group(String name, int numOfChildern) {
            this.name = name;
            this.numOfchildren = numOfChildern;
        }
    }

    public class WorkoutProperty{
        public String name;
        public String value;
        public Group type;
    }

    private Workout mWorkout;
    private Context mContext;

    public WorkoutDataModel(Context context) {
        mContext = context;
        mWorkout = new Workout();
        addExcercise();

        Group.EXCERCISES.numOfchildren = getNumOfExcercises();
    }

    public void addExcercise() {
        int numOfExcercises = mWorkout.getExcercises().size();
        addExcercise(new Excercise("Excercise " + (numOfExcercises+1)));
    }

    public void copyExcercise(Excercise excercise) {
        addExcercise(new Excercise(excercise));
    }

    public void addExcercise(Excercise excercise) {
        mWorkout.getExcercises().add(excercise);
        Group.EXCERCISES.numOfchildren = getNumOfExcercises();
    }

    public void removeExcercise(int index) {
        mWorkout.getExcercises().remove(index);
        Group.EXCERCISES.numOfchildren = getNumOfExcercises();
    }

    public Excercise getExcercise(int index) {
        return mWorkout.getExcercises().get(index);
    }

    public void modifyExcercise(int index, Excercise excercise) {
        mWorkout.getExcercises().get(index).setName(excercise.getName());
        mWorkout.getExcercises().get(index).setDuration(excercise.getDuration());
    }

    public int getNumOfExcercises() {
        return mWorkout.getExcercises().size();
    }

    public int getNumOfGroups() {
        return Group.COUNT;
    }

    public String getGroupName(int groupIndex) {
        Group group = Group.values()[groupIndex];
        return group.name;
    }

    public int getNumOfChildernInGroup(int groupIndex) {
        Group group = Group.values()[groupIndex];
        return group.numOfchildren;
    }

    public Object getChaild(int groupIndex, int chaildIndex) {
        WorkoutProperty child = new WorkoutProperty();
        switch (Group.values()[groupIndex]) {
            case WORKOUT_NAME:
                child.name = mContext.getString(R.string.workout_name);
                child.value = mWorkout.getName();
                break;
            case NUM_OF_SETS:
                child.name = mContext.getString(R.string.num_of_sets);
                child.value = String.valueOf(mWorkout.getNumOfSets());
                break;
            case EXCERCISES:
                Excercise excercise = mWorkout.getExcercises().get(chaildIndex);
                child.name = excercise.getName();
                child.value = String.valueOf(excercise.getDuration());
                break;
            case ADD_EXCERCISES:
                child.name = mContext.getString(R.string.add_excercise);
                break;
            case REST:
                if (chaildIndex == 0) {
                    child.name = mContext.getString(R.string.rest_bet_sets);
                    child.value = String.valueOf(mWorkout.getRestDurationBetweenSets());
                } else {
                    child.name = mContext.getString(R.string.rest_bet_exercise);
                    child.value = String.valueOf(mWorkout.getRestDurationBetweenExercises());
                }
                break;
            case WARPUP:
                if (chaildIndex == 0) {
                    child.name = mContext.getString(R.string.warmup);
                    child.value = String.valueOf(mWorkout.getWarmUpDuration());
                } else {
                    child.name = mContext.getString(R.string.cooldown);
                    child.value = String.valueOf(mWorkout.getCoolDownDuration());
                }
                break;
        }
        child.type = Group.values()[groupIndex];
        return child;
    }
}
