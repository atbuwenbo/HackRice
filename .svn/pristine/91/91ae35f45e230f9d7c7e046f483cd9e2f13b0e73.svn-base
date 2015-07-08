package com.cuter.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.cuter.util.AppExit;
import com.cuter.util.ThreadPoolUtil;

public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        
        ThreadPoolUtil.execute(new Runnable(){

			@Override
			public void run() {
				try{
					   Thread.sleep(1000);
			       }catch(Exception e){
			    	   e.printStackTrace();
			       }
			       activityJump(SourceSelActivity.class);
			}
        	
        });
      
    }

	@Override
	public void initComponent() {
		
	}

	@Override
	public void setComponentListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackPressed() {

		  android.content.DialogInterface.OnClickListener exit 
				= new android.content.DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface arg0, int arg1) 
					{
						AppExit.getInstance().onTerminate();				
					}
				};
			new AlertDialog.Builder(this)   
			.setTitle("Exit？")  
			.setMessage("Confirmation of exit")  
			.setPositiveButton("Confirm", exit)  
			.setNegativeButton("Back", null)  
			.show();  
		
	  		
	}
}
