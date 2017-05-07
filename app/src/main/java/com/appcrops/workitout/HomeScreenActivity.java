package com.appcrops.workitout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    private WorkoutDataModel mWorkoutDataModel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        TextToVoice.init(getApplicationContext());
        mWorkoutDataModel = WorkoutDataModel.instance().init(getApplicationContext());

        TextView txtNewWorkout = (TextView) findViewById(R.id.txt_new_workout);
        txtNewWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWorkoutDataModel.createNewWorkout();
                Intent intent = new Intent(HomeScreenActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });

        ListView lstWorkouts = (ListView) findViewById(R.id.lst_workouts);
        WorkoutListAdapter workoutListAdapter = new WorkoutListAdapter(this, mWorkoutDataModel.getSavedWorkouts());
        lstWorkouts.setAdapter(workoutListAdapter);
        lstWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                ArrayList<Workout> workoutList = mWorkoutDataModel.getSavedWorkouts();
                Workout workout = new Workout(workoutList.get(index));
                mWorkoutDataModel.setWorkout(workout);
                Intent intent = new Intent(HomeScreenActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });
    }

    public class WorkoutListAdapter extends BaseAdapter {
        ArrayList<Workout> mWorkoutList;
        Context mContext;
        public WorkoutListAdapter(Context context, ArrayList<Workout> workoutList) {
            mWorkoutList = workoutList;
            mContext = context;
        }
        @Override
        public int getCount() {
            return mWorkoutList.size();
        }

        @Override
        public Object getItem(int index) {
            return mWorkoutList.get(index);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            LayoutInflater infalInflater = LayoutInflater.from(mContext);
            view = infalInflater.inflate(R.layout.workouts_list_item, viewGroup, false);
            TextView txtWorkoutName = (TextView) view.findViewById(R.id.txt_workout_name);
            TextView txtWorkoutDuration = (TextView) view.findViewById(R.id.txt_workout_duration);
            Workout workout = mWorkoutList.get(index);
            txtWorkoutName.setText(workout.getName());
            txtWorkoutDuration.setText(String.valueOf(workout.getNumOfSets()));
            return view;
        }
    }
}
