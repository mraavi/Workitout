package com.appcrops.workitout;

/**
 * Created by mraavi on 08/04/17.
 */

public class WorkoutDataModel {
    private static enum Group{
        NAME("", 1),
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

       /* public String getName(int index) {

            String name = "";
            switch (Groups.values()[index]) {
                case NAME:
                    name = "";
                    break;
                case NUM_OF_SETS:
                    name = "";
                    break;
                case EXCERCISES:
                    name = "";
                    break;
                case ADD_EXCERCISES:
                    name = "Excercises";
                    break;
                case REST:
                    name = "";
                    break;
                case WARPUP:
                    name = "";
                    break;
            }
            return name;
        }*/
    }

    private Workout mWorkout;

    public WorkoutDataModel() {
        Workout workout = new Workout();
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

    public int getNumOfExcercises() {
        return mWorkout.getExcercises().size();
    }

    public int getNumOfGroups() {
        return Group.COUNT;
    }

    public String getGroupName(int groupIndex) {
        Group group = Group.values()[groupIndex];
        return group.name;
        //return Groups.getName(groupIndex);
    }

    public int getNumOfChildernInGroup(int groupIndex) {
        Group group = Group.values()[groupIndex];
        return group.numOfchildren;
        /*int numOfChildern = 0;
        switch (Groups.values()[groupIndex]) {
            case NAME:
                numOfChildern = 1;
                break;
            case NUM_OF_SETS:
                numOfChildern = 1;
                break;
            case EXCERCISES:
                numOfChildern =  mWorkout.getExcercises().size();
                break;
            case ADD_EXCERCISES:
                numOfChildern = 1;
                break;
            case REST:
                numOfChildern = 2;
                break;
            case WARPUP:
                numOfChildern = 2;
                break;
        }
        return numOfChildern;*/
    }

    /*public String getChaildName(int groupIndex, int chaildIndex) {
        String name = "";
        switch (Groups.values()[groupIndex]) {
            case NAME:
                name = mWorkout.getName();
                break;
            case NUM_OF_SETS:
                name = String.valueOf(mWorkout.getNumOfSets());
                break;
            case EXCERCISES:
                name = String.valueOf(mWorkout.getExcercises().get(chaildIndex));
                break;
            case ADD_EXCERCISES:
                name = "Add Excercise";
                break;
            case REST:
                if (chaildIndex == )
                break;
            case WARPUP:
                name = "";
                break;
        }
        return name;
    }*/
}
