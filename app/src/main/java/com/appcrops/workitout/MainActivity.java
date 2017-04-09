package com.appcrops.workitout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {

    private WorkoutDataModel mWorkoutDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWorkoutDataModel = new WorkoutDataModel(this);
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this, mWorkoutDataModel);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.exlst_workout_table);
        expandableListView.setAdapter(workoutAdapter);
    }
}
