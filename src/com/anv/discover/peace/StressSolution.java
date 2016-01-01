package com.anv.discover.peace;

import java.util.ArrayList;
import java.util.Iterator;

import com.anv.discover.peace.customized.ui.UnderlinedTextView;
import com.anv.discover.peace.model.XMLAdapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StressSolution extends Activity
{
	private TextView headingView;
	private TextView solutionView;
	private UnderlinedTextView solutionTitleView;
	LinearLayout.LayoutParams textViewLp;
	private LinearLayout solutionLayout;
	private LayoutInflater inflater ;
	
	private XMLAdapter xmlAdapter;
	private String stresscause;
	
	private static final String LOG_TAG = "DP:StressSolutionSummary";
	public static final int RECEIVE_STRESS_SUMMARY = 2;
	public static final int RECEIVE_SOLUTION= 3;
	
	private String stress_summary;
	private ArrayList<String> solution_title;
	
	private ArrayList<String> solutions;
	 TitleClickListener ontitleClick;
	
	
	
	class TitleClickListener implements View.OnClickListener
	{

	
		public void onClick(View view) {
			// TODO Auto-generated method stub
			
			
			int selectedIndex = (Integer)view.getTag();
			log("The selected index is " + selectedIndex);
			UnderlinedTextView selectedView = (UnderlinedTextView)view.findViewById(R.id.solutionTitle);
			String solution_title = selectedView.getText().toString();
			String solution = solutions.get(selectedIndex);
			
			Intent startSolution  = new Intent(getApplicationContext(),StressSolutionSummary.class);
			startSolution.putExtra("cause", stresscause);
			startSolution.putExtra("title", solution_title);
			startSolution.putExtra("solution", solution);
			
			startActivity(startSolution);
			
			
			
		}

		private void log(String string) {
			// TODO Auto-generated method stub
			Log.d(LOG_TAG,string);
		}
		
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stress_solution);
        solutionView = (TextView)findViewById(R.id.detailed_solution);
        headingView = (TextView)findViewById(R.id.heading_solution);
        solutionLayout = (LinearLayout)findViewById(R.id.title_parentlayout_solution);
       
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
       
        textViewLp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
        
        textViewLp.setMargins(0, 0, 0, 8);
        
        solution_title = new ArrayList<String>();
        solutions = new ArrayList<String>();
        
        Intent receivedIntent = getIntent();
        
         stresscause = receivedIntent.getStringExtra("stresscause");
        
         headingView.setText(stresscause);
         
         xmlAdapter = new XMLAdapter(this);	
		 xmlAdapter.findSolution(stresscause);
         
         ontitleClick  = new TitleClickListener();
         
        
    }
	@Override
	protected void onResume() 
	{
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
        	case RECEIVE_STRESS_SUMMARY:
        		stress_summary = (String)msg.obj;
        			log("Received instance is " +stress_summary);
        			
        			solutionView.setText(stress_summary);
        		
        		//adapter.addItem(stressType);
        		//adapter.notifyDataSetChanged();
        		break;
        		
        	case RECEIVE_SOLUTION:
        		
        		int index = -1;
        	//StringBuilder sb = new StringBuilder();
        		
        		Bundle receivedData = (Bundle)msg.getData();
        		
        		solution_title = receivedData.getStringArrayList("solution_title");
        		solutions = receivedData.getStringArrayList("solutions");
        		Iterator<String> titleIterator = solution_title.iterator();
        		
        		while (titleIterator.hasNext())
        		{
        			index++;
        			solutionTitleView = (UnderlinedTextView) inflater.inflate(R.layout.solution_title_layout, null);
        			solutionTitleView.setText( titleIterator.next());
        			solutionTitleView.setTag(index);
        			solutionTitleView.setOnClickListener(ontitleClick);
        			solutionLayout.addView(solutionTitleView, textViewLp);
        			
        		}
        		
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
