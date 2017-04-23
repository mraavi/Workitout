package com.appcrops.workitout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class ExecuteActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    public class TimerData {
        public String name;
        public int duration;

        public TimerData(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }
    }

    ArrayBlockingQueue<TimerData> mWorkoutTimerQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute);
        Workout workout = (Workout) getIntent().getSerializableExtra("WorkoutObject");
        ArrayList<TimerData> timerList = new ArrayList<TimerData>();

        ArrayList<Excercise> excercises = workout.getExcercises();
        int numOfExecercise = excercises.size();
        int numOfSets = workout.getNumOfSets();
        int warpupDuration = workout.getWarmUpDuration();
        int coolDownDuration = workout.getCoolDownDuration();
        int restBetExeDuration = workout.getRestDurationBetweenExercises();
        int restBetSetsDuration = workout.getRestDurationBetweenSets();

        if (warpupDuration > 0) {
            timerList.add(new TimerData("Warm up", workout.getWarmUpDuration()));
        }
        for (int i = 0; i < numOfSets; i++) {

            for (int j = 0; j < numOfExecercise; j++) {
                Excercise exe = excercises.get(j);
                timerList.add(new TimerData(exe.getName(), exe.getDuration()));

                if ( (j < numOfExecercise-1) && (restBetExeDuration > 0)) {
                    timerList.add(new TimerData("Rest", restBetExeDuration));
                }
            }

            if (i < numOfSets-1) {
                if (restBetSetsDuration > 0) {
                    timerList.add(new TimerData("Rest", restBetSetsDuration));
                } else if (restBetExeDuration > 0) {
                    timerList.add(new TimerData("Rest", restBetExeDuration));
                }
            }
        }
        if (coolDownDuration > 0) {
            timerList.add(new TimerData("Cool down", coolDownDuration));
        }

        mWorkoutTimerQueue = new ArrayBlockingQueue<TimerData>(timerList.size(), false, timerList);
        runTasksFromQueue();
    }

    private void runTasksFromQueue() {
        TimerData timerData = mWorkoutTimerQueue.poll();
        if ( timerData != null) {
            runTimerTask(timerData);
        } else {
            Log.d(TAG, "runTasksFromQueue: " + "WORKOUT DONE!!");
            finish();
        }
    }

    private void runTimerTask(final TimerData timerData) {
        new CountDownTimer(timerData.duration*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "runTimerTask-Remaining Time for " + timerData.name + " " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d(TAG, "runTimerTask-Finished " + timerData.name);
                runTasksFromQueue();
            }
        }.start();
    }
}
