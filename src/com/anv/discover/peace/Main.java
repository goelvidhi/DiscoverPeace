package com.anv.discover.peace;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import android.view.KeyEvent;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

import android.widget.Button;

public class Main extends Activity
	{
	
	private Button startButton;

//	private final String LOG_TAG = "DP: Main";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startButton = (Button) findViewById(R.id.start_here);
                
        startButton.setShadowLayer(9, 9, 9, Color.rgb(44, 44, 44));
        startButton.setOnClickListener(new OnClickListener() 
        {
			
		public void onClick(View view) 
			{ 
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(), StressList.class));
			}
		}
        );
        
        
     startButton.setOnFocusChangeListener(new OnFocusChangeListener() {
		
	
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
	
			Button buttonView = (Button) v;
			if(hasFocus)
			{
				buttonView.setShadowLayer(9, 1, 1, Color.WHITE);
			}
		}
	});
     
        
     new Eula(this).show();
	
    }

    @Override
	protected void onResume() 
    {
		// TODO Auto-generated method stub
		super.onResume();
	     
			 
	}
    
   


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}
	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
	}

	
	private void launchExitDialog()
	{
		
		String title = getString(R.string.quit);
		String message = getString (R.string.quit_message);
				
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

        	
            public void onClick(DialogInterface dialogInterface, int i) {
                // Finish this activity
            	finish();
                
            }
        })
        .setNegativeButton(android.R.string.cancel, null);

		builder.create().show();
	}
	
	
	
	
	
	
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		launchExitDialog();
    	}
    	return super.onKeyDown(keyCode, event);
    	}
      
  
	}
