package com.appcrops.workitout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

public class WorkoutActivity extends AppCompatActivity {

    private WorkoutDataModel mWorkoutDataModel;
    private WorkoutAdapter mWorkoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        mWorkoutDataModel = WorkoutDataModel.instance();
        mWorkoutAdapter = new WorkoutAdapter(this, mWorkoutDataModel);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.exlst_workout_table);
        expandableListView.setAdapter(mWorkoutAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupIndex, int chaildIndex, long l) {
                WorkoutDataModel.WorkoutProperty workoutProperty = (WorkoutDataModel.WorkoutProperty)view.getTag();
                if (workoutProperty != null) {
                    if (workoutProperty.type == WorkoutDataModel.Group.ADD_EXCERCISES) {
                        mWorkoutDataModel.addExcercise();
                        mWorkoutAdapter.notifyDataSetChanged();
                        return true;
                    } else if (workoutProperty.type == WorkoutDataModel.Group.EXCERCISES){
                        Intent intent = new Intent(WorkoutActivity.this, AddExcerciseActivity.class);
                        Excercise excercise = mWorkoutDataModel.getExcercise(chaildIndex);
                        intent.putExtra("ExcerciseObject", excercise);
                        intent.putExtra("ExcerciseIndex", chaildIndex);
                        startActivityForResult(intent, Constants.ACTION_EDIT_EXERCISE);
                        return true;
                    }
                }
                return false;
            }
        });

        Button saveBtn = (Button) findViewById(R.id.btn_save_workout);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mWorkoutDataModel.isWorkoutNameExist()) {
                    mWorkoutDataModel.saveWorkout();
                    Intent intent = new Intent(WorkoutActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showDialog(getString(R.string.workout_name_exist) );
                }
            }
        });

        Button startBtn = (Button) findViewById(R.id.btn_start_workout);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWorkoutDataModel.saveWorkout();
                Intent intent = new Intent(WorkoutActivity.this, PerformWorkoutActivity.class);
                intent.putExtra("WorkoutObject", mWorkoutDataModel.getCurrentWorkout());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.ACTION_EDIT_EXERCISE) {
            if (resultCode == Constants.ACTION_EDIT_EXERCISE_YES) {
                Excercise excercise = (Excercise)intent.getSerializableExtra("ExcerciseObject");
                int excerciseIndex = intent.getIntExtra("ExcerciseIndex", -1);
                if ( excercise != null && excerciseIndex != -1) {
                    mWorkoutDataModel.modifyExcercise(excerciseIndex, excercise);
                    mWorkoutAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    private void showDialog(String str) {
        new AlertDialog.Builder(this)
                .setMessage(str)
                .show();
    }
}
