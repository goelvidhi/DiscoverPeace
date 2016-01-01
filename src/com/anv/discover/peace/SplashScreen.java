package com.anv.discover.peace;

import com.uqgpI.fowLg132141.Airpush;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends Activity{

	private Airpush airpush;
	  @Override
	    public void onCreate(Bundle savedInstanceState) 
	    {
	    	Log.d("DP", "Splash screen launch");
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash_layout);
	        
	        
	        ProgressDialog progressDialog = new ProgressDialog(this);
	        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	        progressDialog.setCancelable(false);
	       progressDialog.setIndeterminate(false);
	        
	        progressDialog.show();
	        
	        airpush=new Airpush(getApplicationContext());
	        
	        airpush.startPushNotification(true);
	        airpush.startIconAd();
	        airpush.startSmartWallAd();
	        
	        progressDialog.cancel();
	      /*  Intent mainIntent = new Intent(SplashScreen.this,Main.class);
            SplashScreen.this.startActivity(mainIntent);
            SplashScreen.this.finish();
	          */     
	     
			
	    }

	    
}
