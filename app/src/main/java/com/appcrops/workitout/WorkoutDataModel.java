package com.appcrops.workitout;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public static final int TAG_NUM_OF_SETS = 1;
    public static final int TAG_EXCERCISES = 2;
    public static final int TAG_REST_BET_EXCERCISES = 3;
    public static final int TAG_REST_BET_SETS = 4;
    public static final int TAG_WARMUP = 5;
    public static final int TAG_COOLDOWN = 6;

    public class WorkoutProperty{
        public String name;
        public String value;
        public Group type;
        public int tag;
    }

    private Context mContext;
    private Workout mWorkout;
    private ArrayList<String> mSavedWorkoutNames;
    private ArrayList<Workout> mSavedWorkouts;

    public WorkoutDataModel(Context context) {
        mContext = context;
        mSavedWorkoutNames = new ArrayList<String>();
        mSavedWorkouts = new ArrayList<Workout>();
        loadSavedWorkouts();

        if (mSavedWorkouts.size() > 0) {
            mWorkout = mSavedWorkouts.get(0);
            Group.EXCERCISES.numOfchildren = getNumOfExcercises();
        } else {
            createNewExcercise();
        }
        //Group.EXCERCISES.numOfchildren = getNumOfExcercises();
    }

    public void createNewExcercise() {
        mWorkout = new Workout();
        addExcercise();

    }

    public Workout getCurrentWorkout() {
        return mWorkout;
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

    public void setNumOfSets(int num) {
        mWorkout.setNumOfSets(num);
    }

    public void setRestDurationBetweenExercises(int duration) {
        mWorkout.setRestDurationBetweenExercises(duration);
    }

    public void setRestDurationBetweenSets(int duration) {
        mWorkout.setRestDurationBetweenSets(duration);
    }

    public void setWarmUpDuration(int duration) {
        mWorkout.setWarmUpDuration(duration);
    }

    public void setCoolDownDuration(int duration) {
        mWorkout.setCoolDownDuration(duration);
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
                child.tag = TAG_NUM_OF_SETS;
                break;
            case EXCERCISES:
                Excercise excercise = mWorkout.getExcercises().get(chaildIndex);
                child.name = excercise.getName();
                child.value = String.valueOf(excercise.getDuration());
                child.tag = TAG_EXCERCISES;
                break;
            case ADD_EXCERCISES:
                child.name = mContext.getString(R.string.add_excercise);
                break;
            case REST:
                if (chaildIndex == 0) {
                    child.name = mContext.getString(R.string.rest_bet_sets);
                    child.value = String.valueOf(mWorkout.getRestDurationBetweenSets());
                    child.tag = TAG_REST_BET_SETS;
                } else {
                    child.name = mContext.getString(R.string.rest_bet_exercise);
                    child.value = String.valueOf(mWorkout.getRestDurationBetweenExercises());
                    child.tag = TAG_REST_BET_EXCERCISES;
                }
                break;
            case WARPUP:
                if (chaildIndex == 0) {
                    child.name = mContext.getString(R.string.warmup);
                    child.value = String.valueOf(mWorkout.getWarmUpDuration());
                    child.tag = TAG_WARMUP;
                } else {
                    child.name = mContext.getString(R.string.cooldown);
                    child.value = String.valueOf(mWorkout.getCoolDownDuration());
                    child.tag = TAG_COOLDOWN;
                }
                break;
        }
        child.type = Group.values()[groupIndex];
        return child;
    }

    public void saveWorkout() {
        try {
            FileOutputStream outStream = null;
            String workoutName = mWorkout.getName();
            File file = new File(mContext.getFilesDir(), File.pathSeparator + workoutName);
            outStream = new FileOutputStream(file);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeObject(mWorkout);
            objectOutStream.close();
            saveWorkoutName(workoutName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveWorkoutName(String workoutName) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName() + "workout", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        mSavedWorkoutNames.add(workoutName);
        Set<String> workoutSet = new HashSet<String>();
        workoutSet.addAll(mSavedWorkoutNames);
        editor.putStringSet("names", workoutSet);
        editor.commit();
    }

    public void loadSavedWorkouts () {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getPackageName() + "workout", Context.MODE_PRIVATE);
        Set<String> workoutSet = prefs.getStringSet("names", null);
        if (workoutSet != null) {
            mSavedWorkoutNames.clear();
            mSavedWorkoutNames.addAll(workoutSet);

            mSavedWorkouts.clear();
            for (String workoutName : mSavedWorkoutNames) {
                Workout workout = getWorkout(workoutName);
                mSavedWorkouts.add(workout);
            }
        }
    }

    private Workout getWorkout(String workoutName) {
        Workout workout = null;
        try {
            FileInputStream inStream = null;
            File file = new File(mContext.getFilesDir(), File.pathSeparator + workoutName);
            inStream = new FileInputStream(file);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            workout = (Workout) objectInStream.readObject();
            objectInStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workout;
    }

    public void deleteWorkout(String workoutName) {
        try {
            File file = new File(mContext.getFilesDir(), File.pathSeparator + workoutName);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
