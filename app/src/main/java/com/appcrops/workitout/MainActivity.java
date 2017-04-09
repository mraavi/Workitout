package com.appcrops.workitout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {

    private WorkoutDataModel mWorkoutDataModel;
    private WorkoutAdapter mWorkoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWorkoutDataModel = new WorkoutDataModel(this);
        mWorkoutAdapter = new WorkoutAdapter(this, mWorkoutDataModel);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.exlst_workout_table);
        expandableListView.setAdapter(mWorkoutAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupIndex, int chaildIndex, long l) {
                WorkoutDataModel.WorkoutProperty workoutProperty = (WorkoutDataModel.WorkoutProperty)view.getTag();
                if (workoutProperty != null && workoutProperty.type == WorkoutDataModel.Group.ADD_EXCERCISES) {
                    mWorkoutDataModel.addExcercise();
                    mWorkoutAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
    }
}
