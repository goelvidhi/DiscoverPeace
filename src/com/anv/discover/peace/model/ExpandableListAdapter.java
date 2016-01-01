package com.anv.discover.peace.model;

import java.util.ArrayList;

import com.anv.discover.peace.R;
import com.anv.discover.peace.stressbean.StressType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    

	@Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    private Context context;

    private ArrayList<String> groups;

    private ArrayList<ArrayList<StressType>> children;

    public ExpandableListAdapter(Context context, ArrayList<String> groups,
            ArrayList<ArrayList<StressType>> children) {
        this.context = context;
        this.groups = groups;
        this.children = children;
    }

    /**
     * A general add method, that allows you to add a Vehicle to this list
     * 
     * Depending on if the category opf the vehicle is present or not,
     * the corresponding item will either be added to an existing group if it 
     * exists, else the group will be created and then the item will be added
     * @param vehicle
     */
    public void addItem(StressType stressType) {
        if (!groups.contains(stressType.getStressGroup())) {
            groups.add(stressType.getStressGroup());
        }
        int index = groups.indexOf(stressType.getStressGroup());
        if (children.size() < index + 1) {
            children.add(new ArrayList<StressType>());
        }
        children.get(index).add(stressType);
    }

   
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

   
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    // Return a child view. You can load your custom layout here.
    
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        StressType stressType = (StressType) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_layout, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText("   " + stressType.getStressCause());

        // Depending upon the child type, set the imageTextView01
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
      /*  if (stressType instanceof Chemical) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon, 0, 0, 0);
        }else if (stressType instanceof Physical) {
            tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon, 0, 0, 0);
        }*/
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    public int getGroupCount() {
        return groups.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Return a group view. You can load your custom layout here.
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parent_layout, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(group);
        
        if(isExpanded)
        {
        tv.setTextColor(context.getResources().getColor(R.color.grey));
        tv.setTextAppearance(context, R.style.italicsText);
        
        }
        else
        {
        tv.setTextColor(context.getResources().getColor(R.color.white));
        tv.setTextAppearance(context, R.style.normalText);
        }
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

   
    public boolean isChildSelectable(int arg0, int arg1) 
    {
        return true;
    }

	

}
