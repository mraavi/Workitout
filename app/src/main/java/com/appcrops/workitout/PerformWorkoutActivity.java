package com.appcrops.workitout;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class PerformWorkoutActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    public class TimerData extends Object{
        public String name;
        public int duration;

        public TimerData(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }
    }

    TextView mTxtExecerciseName;
    TextView mTxtTimerCountdown;
    ArrayBlockingQueue<TimerData> mWorkoutTimerQueue;
    ExcercisesQueueAdapter mExcercisesQueueAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perform_workout);
        TextToVoice.init(getApplicationContext());
        mTxtTimerCountdown = (TextView) findViewById(R.id.txt_exe_timer_countdown);
        mTxtExecerciseName = (TextView) findViewById(R.id.txt_current_excercise);

        Workout workout = (Workout) getIntent().getSerializableExtra("WorkoutObject");
        initWorkoutQueue(workout);

        ListView excerciseQueueList = (ListView) findViewById(R.id.listview_excercises);
        mExcercisesQueueAdapter = new ExcercisesQueueAdapter(this, mWorkoutTimerQueue);
        excerciseQueueList.setAdapter(mExcercisesQueueAdapter);
        runTasksFromQueue();
    }

    public void initWorkoutQueue(Workout workout) {
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
    }

    private void runTasksFromQueue() {
        TimerData timerData = mWorkoutTimerQueue.poll();
        if ( timerData != null) {
            mExcercisesQueueAdapter.setExcerciseQueue(mWorkoutTimerQueue);
            mExcercisesQueueAdapter.notifyDataSetChanged();
            runTimerTask(timerData);
        } else {
            Log.d(TAG, "runTasksFromQueue: " + "WORKOUT DONE!!");
            finish();
        }
    }

    private void runTimerTask(final TimerData timerData) {
        mTxtExecerciseName.setText(timerData.name);
        mTxtTimerCountdown.setText(String.valueOf(timerData.duration));
        TextToVoice.speak(timerData.name);
        new CountDownTimer(timerData.duration*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTxtTimerCountdown.setText(String.valueOf(millisUntilFinished / 1000));
                Log.d(TAG, "runTimerTask-Remaining Time for " + timerData.name + " " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d(TAG, "runTimerTask-Finished " + timerData.name);
                runTasksFromQueue();
            }
        }.start();
    }

    public class ExcercisesQueueAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<TimerData> mTimerDataList;
        public ExcercisesQueueAdapter(Context context, ArrayBlockingQueue<TimerData> timerDataQueue) {
            mContext = context;
            mTimerDataList = new ArrayList<>(timerDataQueue);
        }

        public void setExcerciseQueue(ArrayBlockingQueue<TimerData> timerDataQueue) {
            //mTimerDataList = (ArrayList<TimerData>) Arrays.asList((TimerData[])timerDataQueue.toArray());
            mTimerDataList = new ArrayList<>(timerDataQueue);
        }

        @Override
        public int getCount() {
            if (mTimerDataList != null) {
                return mTimerDataList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mTimerDataList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            LayoutInflater infalInflater = LayoutInflater.from(mContext);
            view = infalInflater.inflate(R.layout.perform_workout_queue_list_item, viewGroup, false);
            TextView txtExName = (TextView) view.findViewById(R.id.txt_excercise_name);
            TextView txtExDur = (TextView) view.findViewById(R.id.txt_excercise_duration);
            TimerData timerData = mTimerDataList.get(index);
            txtExName.setText(timerData.name);
            txtExDur.setText(String.valueOf(timerData.duration));
            return view;
        }
    }
}
