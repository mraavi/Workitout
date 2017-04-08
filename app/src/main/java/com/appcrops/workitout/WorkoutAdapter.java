package com.appcrops.workitout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mraavi on 08/04/17.
 */

public class WorkoutAdapter extends BaseExpandableListAdapter {

    HashMap<String, ArrayList<String>> mLivingBeings;
    ArrayList<String> mLivingBeingGroups;
    Context mContext;

    public WorkoutAdapter(Context context, HashMap<String, ArrayList<String>> livingBeings, ArrayList<String> livingBeingGroups) {
        mLivingBeings = livingBeings;
        mLivingBeingGroups = livingBeingGroups;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        if (mLivingBeings != null) {
            return mLivingBeings.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupIndex) {
        if (mLivingBeings != null && mLivingBeingGroups != null) {
            return mLivingBeings.get(mLivingBeingGroups.get(groupIndex)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupIndex) {
        if (mLivingBeingGroups != null) {
            return mLivingBeingGroups.get(groupIndex);
        }
        return null;
    }

    @Override
    public Object getChild(int groupIndex, int childIndex) {
        if (mLivingBeings != null && mLivingBeingGroups != null) {
            return mLivingBeings.get(mLivingBeingGroups.get(groupIndex)).get(childIndex);
        }

        return null;
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

        /*if (view == null) {
            LayoutInflater infalInflater = LayoutInflater.from(mContext);
            view = infalInflater.inflate(R.layout.listview_group_item, null);
        }

        TextView groupItemTxt = (TextView)view.findViewById(R.id.living_being_group_name);
        groupItemTxt.setText((String)getGroup(groupIndex));

        ExpandableListView expandableListView = (ExpandableListView) viewGroup;
        expandableListView.expandGroup(groupIndex);*/

        return view;
    }

    @Override
    public View getChildView(int groupIndex, int childIndex, boolean b, View view, ViewGroup viewGroup) {
        /*if (view == null) {
            LayoutInflater infalInflater = LayoutInflater.from(mContext);
            view = infalInflater.inflate(R.layout.listview_child_item, null);
        }

        TextView childItemTxt = (TextView)view.findViewById(R.id.living_being_name);
        childItemTxt.setText((String)getChild(groupIndex, childIndex));*/
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
