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

import java.util.ArrayList;
import java.util.HashMap;

public class AddExcerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_excercise);
       // Excercise excercise = (Excercise) getIntent().getSerializableExtra("ExcerciseObject");
        final int excerciseIndex = getIntent().getIntExtra("ExcerciseIndex", -1);

       // final EditText nameEditText = (EditText) findViewById(R.id.etxt_excercise_name);
        //nameEditText.setText(excercise.getName());
        final EditText durationEditText = (EditText) findViewById(R.id.etxt_duration);
        durationEditText.setText(String.valueOf(Constants.DEFAULT_EXCERCISE_DURATON));

        ListView excerciseList = (ListView) findViewById(R.id.lst_excercises);
        //String[] excercises = getResources().getStringArray(R.array.excercise_names);
        ArrayList<Excercise> excerciseArrayList = getAvailableExcercises();
        final AddExcerciseAdapter addExcerciseAdapter = new AddExcerciseAdapter(this, excerciseArrayList);
        excerciseList.setAdapter(addExcerciseAdapter);

        Button btnOk = (Button) findViewById(R.id.btn_ok);
        Button btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String excerciseName = nameEditText.getText().toString();
                //String excerciseDuration = durationEditText.getText().toString();
                ArrayList<Excercise> selectedExcercises = addExcerciseAdapter.getSelectedExcercises();
                Intent intent = new Intent();
                //intent.putExtra("ExcerciseObject", new Excercise(excerciseName, Integer.parseInt(excerciseDuration)));
                intent.putExtra("ExcerciseObjects", selectedExcercises);
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

    private ArrayList<Excercise> getAvailableExcercises(){
        ArrayList<Excercise> excerciseArrayList = new ArrayList<Excercise>();
        String[] excercises = getResources().getStringArray(R.array.excercise_names);
        for (int i =0; i < excercises.length; i++) {
            Excercise excercise = new Excercise(excercises[i], Constants.DEFAULT_EXCERCISE_DURATON);
            excerciseArrayList.add(excercise);
        }

        return excerciseArrayList;
    }


    public class AddExcerciseAdapter extends BaseAdapter {

        private Context mContext;
        private HashMap<Excercise, Boolean> mExcercises;
        private ArrayList<Excercise> mExcerciseArrayList;
        public AddExcerciseAdapter(Context context, ArrayList<Excercise> excerciseArrayList) {
            mContext = context;
            mExcerciseArrayList = excerciseArrayList;
            mExcercises = new HashMap<Excercise, Boolean>();
            for(Excercise excercise : mExcerciseArrayList){
                mExcercises.put(excercise, false);
            }
            /*mExcercises = new HashMap<String, Boolean>();
            for (int i =0; i < excercises.length; i++) {
              mExcercises.put(excercises[i], false);
            }*/
        }

       public ArrayList<Excercise> getSelectedExcercises() {
           ArrayList<Excercise> selectedExcercises = new ArrayList<Excercise>();
           for(Excercise excercise:mExcerciseArrayList) {
               if(mExcercises.get(excercise) != null && mExcercises.get(excercise) == true) {
                   selectedExcercises.add(excercise);
               }
           }
           return selectedExcercises;
       }

        @Override
        public int getCount() {
            return mExcerciseArrayList.size();
        }

        @Override
        public Object getItem(int index) {
            return mExcerciseArrayList.get(index);
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
                /*TextView txtExcercise = (TextView) view.findViewById(R.id.txt_excercise_name);
                CheckBox chkExcercise = (CheckBox) view.findViewById(R.id.chk_excercise);
                Excercise excercise = (Excercise)view.getTag();
                Boolean isChecked = chkExcercise.isChecked();
                mExcercises.put(excercise, isChecked);*/
            }
            Excercise excercise = (Excercise) getItem(index);
            ImageView imgExcercise = (ImageView) view.findViewById(R.id.img_excercise);
            TextView txtExcercise = (TextView) view.findViewById(R.id.txt_excercise_name);
            CheckBox chkExcercise = (CheckBox) view.findViewById(R.id.chk_excercise);
            chkExcercise.setTag(excercise);
            setupCheckStateChanged(chkExcercise);
            boolean isChecked = mExcercises.get(excercise);
            txtExcercise.setText(excercise.getName());
            chkExcercise.setChecked(isChecked);
            return view;
        }

        private void setupCheckStateChanged(CheckBox checkBox) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox chkBox = (CheckBox) view;
                    boolean isChecked = chkBox.isChecked();
                    Excercise excercise = (Excercise) chkBox.getTag();
                    mExcercises.put(excercise, isChecked);
                }
            });
        }
    }
}
