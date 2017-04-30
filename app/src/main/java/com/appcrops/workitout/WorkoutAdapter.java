package com.appcrops.workitout;

import android.content.Context;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by mraavi on 08/04/17.
 */

public class WorkoutAdapter extends BaseExpandableListAdapter {

    WorkoutDataModel mWorkoutDataModel;
    Context mContext;

    public WorkoutAdapter(Context context, WorkoutDataModel workoutDataModel) {
        mWorkoutDataModel = workoutDataModel;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mWorkoutDataModel.getNumOfGroups();
    }

    @Override
    public int getChildrenCount(int groupIndex) {
        return mWorkoutDataModel.getNumOfChildernInGroup(groupIndex);
    }

    @Override
    public Object getGroup(int groupIndex) {
        return mWorkoutDataModel.getGroupName(groupIndex);
    }

    @Override
    public Object getChild(int groupIndex, int childIndex) {
        return mWorkoutDataModel.getChaild(groupIndex, childIndex);
    }

    @Override
    public long getGroupId(int groupIndex) {
        return groupIndex;
    }

    @Override
    public long getChildId(int groupIndex, int childIndex) {
        return childIndex;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupIndex, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater infalInflater = LayoutInflater.from(mContext);
            view = infalInflater.inflate(R.layout.group_name_list_item, null);
        }

        TextView groupItemTxt = (TextView)view.findViewById(R.id.txt_group_name);
        groupItemTxt.setText((String)getGroup(groupIndex));

        ExpandableListView expandableListView = (ExpandableListView) viewGroup;
        expandableListView.expandGroup(groupIndex);

        return view;
    }

    @Override
    public View getChildView(int groupIndex, int childIndex, boolean b, View view, ViewGroup viewGroup) {

        LayoutInflater infalInflater = LayoutInflater.from(mContext);
        WorkoutDataModel.WorkoutProperty workoutProperty = (WorkoutDataModel.WorkoutProperty) getChild(groupIndex, childIndex);
        if (workoutProperty.type == WorkoutDataModel.Group.ADD_EXCERCISES) {
            view = infalInflater.inflate(R.layout.add_excercise_list_item, viewGroup, false);
            TextView textView = (TextView) view.findViewById(R.id.txt_add_excercise);
            textView.setText(workoutProperty.name);
        } else if (workoutProperty.type == WorkoutDataModel.Group.EXCERCISES) {
            view = infalInflater.inflate(R.layout.excercise_list_item, viewGroup, false);
            TextView txtExName = (TextView) view.findViewById(R.id.txt_excercise_name);
            TextView txtExDur = (TextView) view.findViewById(R.id.txt_excercise_duration);
            txtExName.setText(workoutProperty.name);
            txtExDur.setText(workoutProperty.value);

            Button deleteBtn = (Button) view.findViewById(R.id.btn_delete_exe);
            deleteBtn.setTag(childIndex);
            setupExcerciseItemListeners(deleteBtn, null);
        } else {
            view = infalInflater.inflate(R.layout.name_value_list_item, viewGroup, false);
            TextView txtName = (TextView) view.findViewById(R.id.txt_name);
            EditText etxtValue = (EditText) view.findViewById(R.id.etxt_value);
            txtName.setText(workoutProperty.name);
            etxtValue.setText(workoutProperty.value);
            etxtValue.setTag(workoutProperty.tag);
            if(workoutProperty.tag == WorkoutDataModel.TAG_WORKOUT_NAME) {
                etxtValue.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            setupValueModifyListeners(etxtValue);
        }
        view.setTag(workoutProperty);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private void setupExcerciseItemListeners(Button deleteBtn, Button addBtn) {
        if (deleteBtn != null) {
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer index = (Integer)view.getTag();
                    mWorkoutDataModel.removeExcercise(index);
                    notifyDataSetChanged();
                }
            });
        }

        if (addBtn != null) {
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void setupValueModifyListeners(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if ( actionId == EditorInfo.IME_ACTION_DONE ) {
                    try {
                        int tag = (int) editText.getTag();
                        String inputValueTxt = (((EditText) textView).getText()).toString();
                        if (inputValueTxt.isEmpty()) {
                            if (tag == WorkoutDataModel.TAG_NUM_OF_SETS) {
                                inputValueTxt = "1";
                            } else {
                                inputValueTxt = "0";
                            }
                            editText.setText(inputValueTxt);
                        }

                        int inputValue = 0;
                        if(tag != WorkoutDataModel.TAG_WORKOUT_NAME) {
                            inputValue = Integer.parseInt(inputValueTxt);
                        }

                        switch (tag) {
                            case WorkoutDataModel.TAG_WORKOUT_NAME:
                                mWorkoutDataModel.setWorkoutName(inputValueTxt);
                                break;
                            case WorkoutDataModel.TAG_NUM_OF_SETS:
                                mWorkoutDataModel.setNumOfSets(inputValue);
                                break;
                            case WorkoutDataModel.TAG_REST_BET_SETS:
                                mWorkoutDataModel.setRestDurationBetweenSets(inputValue);
                                break;
                            case WorkoutDataModel.TAG_REST_BET_EXCERCISES:
                                mWorkoutDataModel.setRestDurationBetweenExercises(inputValue);
                                break;
                            case WorkoutDataModel.TAG_WARMUP:
                                mWorkoutDataModel.setWarmUpDuration(inputValue);
                                break;
                            case WorkoutDataModel.TAG_COOLDOWN:
                                mWorkoutDataModel.setCoolDownDuration(inputValue);
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    textView.clearFocus();

                   InputMethodManager inputManager = (InputMethodManager)
                            mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(0, 0);

                    return true;
                }
                return false;
            }
        });
    }
}
