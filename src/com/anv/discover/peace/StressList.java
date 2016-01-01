package com.anv.discover.peace;

import java.util.ArrayList;

import com.anv.discover.peace.model.ExpandableListAdapter;
import com.anv.discover.peace.model.XMLAdapter;

import com.anv.discover.peace.stressbean.StressType;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class StressList extends Activity
{
	 private  static final String LOG_TAG  = "DP: StressList";
	
	 private ExpandableListAdapter adapter;
	 public ExpandableListView listView; 
	 private XMLAdapter xmlReader;
	 public static final int RECEIVE_STRESS_TYPE = 1;
	 private int lastExpandedGroupPosition = 0;
	 
	 private StressType stressType;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stress_list);
        
     listView = (ExpandableListView) findViewById(R.id.listview);
     stressType = null;
     // Initialize the adapter with blank groups and children
     // We will be adding children on a thread, and then update the ListView
     adapter = new ExpandableListAdapter(this, new ArrayList<String>(),
             new ArrayList<ArrayList<StressType>>());

     
        listView.setOnChildClickListener(new OnChildClickListener()
        {
            
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
               //Toast.makeText(getBaseContext(), "Child clicked", Toast.LENGTH_LONG).show();
               
               
               StressType childStress = (StressType)adapter.getChild(groupPosition, childPosition);
               
               
               Intent solutionIntent = new Intent(getApplicationContext(), StressSolution.class);
               
               solutionIntent.putExtra("stresscause", childStress.getStressCause());
               
               startActivity(solutionIntent);
                return true;
            }

			
        });
        
        listView.setOnGroupClickListener(new OnGroupClickListener()
        {
            
    
            public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3)
            {
                //Toast.makeText(getBaseContext(), "Group clicked", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        
        
        listView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
		
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				
				
				
				if (groupPosition != lastExpandedGroupPosition)
				{
					listView.collapseGroup(lastExpandedGroupPosition);
				}
			
				lastExpandedGroupPosition = groupPosition;
			
			
			}
		});
        
           
               // Set this blank adapter to the list view
        listView.setAdapter(adapter);
     // Initialize the Xml adapter with the app context and read the data from xml.
        xmlReader = new XMLAdapter(this);   
        xmlReader.readCause();
        

       
        
        
    }
    
    
    

 

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}






	public Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
        	
        	switch(msg.what)
        	{
        	case RECEIVE_STRESS_TYPE:
        		stressType = (StressType)msg.obj;
        		log("Received instance is " +stressType.toString());
        		
        		adapter.addItem(stressType);
        		adapter.notifyDataSetChanged();
        		break;
        	default: break;
        	}
            
            super.handleMessage(msg);
        }

		private void log(String msg) 
		{
			// TODO Auto-generated method stub
			Log.d(LOG_TAG , msg);
		}

    };
    }
