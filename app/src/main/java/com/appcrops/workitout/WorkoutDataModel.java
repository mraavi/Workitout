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
        EXCERCISES("Excercise", 0),
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

    public static final int TAG_WORKOUT_NAME = 0;
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

    private static WorkoutDataModel sWorkoutDataModel = null;
    private WorkoutDataModel() {

    }
    public static WorkoutDataModel instance() {
        if(sWorkoutDataModel == null) {
            sWorkoutDataModel = new WorkoutDataModel();
        }
        return sWorkoutDataModel;
    }

    public WorkoutDataModel init(Context context) {
        mContext = context;
        mSavedWorkoutNames = new ArrayList<String>();
        mSavedWorkouts = new ArrayList<Workout>();
        loadSavedWorkouts();

        /*if (mSavedWorkouts.size() > 0) {
            mWorkout = mSavedWorkouts.get(0);
            Group.EXCERCISES.numOfchildren = getNumOfExcercises();
        } else {
            createNewExcercise();
        }*/
        return this;
    }

    public void setWorkout(Workout workout) {
        if(workout != null) {
            mWorkout = workout;
            Group.EXCERCISES.numOfchildren = getNumOfExcercises();
        }
    }

    public void createNewWorkout() {
        mWorkout = new Workout();
        mWorkout.setId(getNewWorkoutId());
       // addExcercise();
    }

    public int getNewWorkoutId(){
        return mSavedWorkouts.size() + Constants.START_WORKOUT_ID;
    }

    public String getNewWorkoutName(){
        String workoutName = mContext.getString(R.string.default_workout_name);
        if(mSavedWorkouts.size() > 0) {
            workoutName = workoutName +" " + mSavedWorkouts.size();
        }
        return workoutName;
    }

   /* public WorkoutDataModel(Context context) {
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
    }*/

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

    public void addExcercisesAtIndex(ArrayList<Excercise> newExcercises) {
        addExcercisesAtIndex(newExcercises, 0);
    }

    public void addExcercisesAtIndex(ArrayList<Excercise> newExcercises, int index) {
        ArrayList<Excercise> workoutExcercises =  mWorkout.getExcercises();
        for(Excercise excercise:newExcercises) {
            workoutExcercises.add(index, excercise);
            index++;
        }
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

    public void setWorkoutName(String name) {
        mWorkout.setName(name);
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
                child.tag = TAG_WORKOUT_NAME;
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

    public boolean isWorkoutNameExist(){
        boolean result = false;
        String workoutName = mWorkout.getName();
        for (Workout savedWorkout: mSavedWorkouts) {
            String savedWorkoutName = savedWorkout.getName();
            if(savedWorkout.getId() != mWorkout.getId() && (workoutName.compareToIgnoreCase(savedWorkoutName) == 0)){
                result = true;
                break;
            }
        }
        return result;
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

    public ArrayList<Workout> getSavedWorkouts() {
        return mSavedWorkouts;
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
