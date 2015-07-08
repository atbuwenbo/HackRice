package com.cuter.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.cuter.util.AppExit;

public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public void initComponent() {
		
	}

	@Override
	public void setComponentListener() {
		
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
