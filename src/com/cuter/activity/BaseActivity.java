package com.cuter.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.cuter.util.AppExit;

public abstract class BaseActivity extends Activity {
	  @Override
	   protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
	       requestWindowFeature(Window.FEATURE_NO_TITLE);		
	       AppExit.getInstance().addActivity(this);
	      
	   }
	  
	 
	  
	  @Override
	  public void onBackPressed(){
		  super.onBackPressed();
	  }
	  
	  @Override
	  public void onResume(){
		  super.onResume();
	  }
	  
	  @Override
	  public void onPause(){
		  super.onPause();
		  // Logs 'app deactivate' App Event.
	  }
	  
	  public void activityJump(Class<?> activity){
		  Intent intent = new Intent(this, activity);
		  startActivity(intent);
	  }
	  
	  public abstract void initComponent();
	  
	  public abstract void setComponentListener();
}
