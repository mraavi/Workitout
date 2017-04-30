package com.appcrops.workitout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class AddExcerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excercise);
        Excercise excercise = (Excercise) getIntent().getSerializableExtra("ExcerciseObject");
        final int excerciseIndex = getIntent().getIntExtra("ExcerciseIndex", -1);

        final EditText nameEditText = (EditText) findViewById(R.id.etxt_excercise_name);
        final EditText durationEditText = (EditText) findViewById(R.id.etxt_duration);
        nameEditText.setText(excercise.getName());
        durationEditText.setText(String.valueOf(excercise.getDuration()));

        ListView excerciseList = (ListView) findViewById(R.id.lst_excercises);
        String[] excercises = getResources().getStringArray(R.array.excercise_names);
        AddExcerciseAdapter addExcerciseAdapter = new AddExcerciseAdapter(this, excercises);
        excerciseList.setAdapter(addExcerciseAdapter);

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

    public class AddExcerciseAdapter extends BaseAdapter {

        private Context mContext;
        private HashMap<String, Boolean> mExcercises;
        public AddExcerciseAdapter(Context context, String[] excercises) {
            mContext = context;
            mExcercises = new HashMap<String, Boolean>();
            for (int i =0; i < excercises.length; i++) {
              mExcercises.put(excercises[i], false);
            }
        }

        @Override
        public int getCount() {
            return mExcercises.size();
        }

        @Override
        public Object getItem(int index) {
            return mExcercises.keySet().toArray()[index];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater infalInflater = LayoutInflater.from(mContext);
                view = infalInflater.inflate(R.layout.select_excercise_list_item, viewGroup, false);
            } else {
                TextView txtExcercise = (TextView) view.findViewById(R.id.txt_excercise_name);
                CheckBox chkExcercise = (CheckBox) view.findViewById(R.id.chk_excercise);
                String excerciseName = (String)view.getTag();
                Boolean isChecked = chkExcercise.isChecked();
                mExcercises.put(excerciseName, isChecked);
            }
            ImageView imgExcercise = (ImageView) view.findViewById(R.id.img_excercise);
            TextView txtExcercise = (TextView) view.findViewById(R.id.txt_excercise_name);
            CheckBox chkExcercise = (CheckBox) view.findViewById(R.id.chk_excercise);
            String excercisesName = (String)mExcercises.keySet().toArray()[index];
            Boolean isChecked = mExcercises.get(excercisesName);
            txtExcercise.setText(excercisesName);
            chkExcercise.setChecked(isChecked);
            view.setTag(excercisesName);
            return view;
        }
    }
}
