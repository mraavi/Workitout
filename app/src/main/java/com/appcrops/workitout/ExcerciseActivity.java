package com.appcrops.workitout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExcerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);
        Excercise excercise = (Excercise) getIntent().getSerializableExtra("ExcerciseObject");
        final int excerciseIndex = getIntent().getIntExtra("ExcerciseIndex", -1);

        final EditText nameEditText = (EditText) findViewById(R.id.etxt_excercise_name);
        final EditText durationEditText = (EditText) findViewById(R.id.etxt_duration);
        nameEditText.setText(excercise.getName());
        durationEditText.setText(String.valueOf(excercise.getDuration()));

        Button btnOk = (Button) findViewById(R.id.btn_ok);
        Button btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String excerciseName = nameEditText.getText().toString();
                String excerciseDuration = durationEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("ExcerciseObject", new Excercise(excerciseName, Integer.parseInt(excerciseDuration)));
                intent.putExtra("ExcerciseIndex", excerciseIndex);
                setResult(Constants.ACTION_EDIT_EXERCISE_YES, intent);
                finish();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Constants.ACTION_EDIT_EXERCISE_NO);
                finish();
            }
        });
    }
}
