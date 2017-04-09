package com.appcrops.workitout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
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
            view = infalInflater.inflate(R.layout.add_excercise_list_item, null);
            TextView textView = (TextView) view.findViewById(R.id.txt_add_excercise);
            textView.setText(workoutProperty.name);
            view.setTag(workoutProperty);
        } else if (workoutProperty.type == WorkoutDataModel.Group.EXCERCISES) {
            view = infalInflater.inflate(R.layout.excercise_list_item, null);
            TextView txtExName = (TextView) view.findViewById(R.id.txt_excercise_name);
            TextView txtExDur = (TextView) view.findViewById(R.id.txt_excercise_duration);
            txtExName.setText(workoutProperty.name);
            txtExDur.setText(workoutProperty.value);
        } else {
            view = infalInflater.inflate(R.layout.name_value_list_item, null);
            TextView txtName = (TextView) view.findViewById(R.id.txt_name);
            TextView txtValue = (TextView) view.findViewById(R.id.etxt_value);
            txtName.setText(workoutProperty.name);
            txtValue.setText(workoutProperty.value);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
