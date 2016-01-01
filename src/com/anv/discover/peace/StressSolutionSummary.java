package com.anv.discover.peace;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

public class StressSolutionSummary extends Activity{
	
	
	private String stresscause;
	private String title;
	private String solution;
	
	private TextView headingView;
	private TextView titleView;
	private TextView solutionView;

	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stress_solution_summary);
        
        headingView = (TextView)findViewById(R.id.heading_solution_summary);
        titleView = (TextView)findViewById(R.id.title_solution_summary);
        solutionView = (TextView)findViewById(R.id.detailed_solution_summary);
        Intent receivedIntent = getIntent();
        
        
        stresscause = receivedIntent.getStringExtra("cause");
        title = receivedIntent.getStringExtra("title");
        solution = receivedIntent.getStringExtra("solution");
        
      
        
        headingView.setText(stresscause);
        titleView.setText(title);
        solutionView.setText(solution);
        
        
        
        
    }
    
    
    
    
}
