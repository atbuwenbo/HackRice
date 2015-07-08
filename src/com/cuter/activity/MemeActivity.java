package com.cuter.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cuter.img.ImageTools;

public class MemeActivity extends BaseActivity {

	private ImageView imgView;
	
	private Bitmap originalBm;
	
	private Bitmap result;
	
	private Button confirm;
	
	private Button cancel;
	
	private RelativeLayout memeResult;
	
	private ImageView memeImg;
	
	private EditText top;
	
	private EditText bottom;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meme);
		
		Bundle bundle = getIntent().getExtras();
		
		originalBm = (Bitmap)bundle.get("img");
		
		initComponent();
		setComponentListener();
	}

	@Override
	public void initComponent() {
		imgView = (ImageView) findViewById(R.id.meme_img);
		imgView.setImageBitmap(originalBm);
		
		confirm = (Button) findViewById(R.id.meme_confirm);
		
		cancel = (Button) findViewById(R.id.meme_cancel);
		
		memeResult = (RelativeLayout) findViewById(R.id.meme_result);
		
		memeImg = (ImageView) findViewById(R.id.meme_img_result);
		
		top = (EditText) findViewById(R.id.top_text);
		top.setDrawingCacheEnabled(true);
		
		bottom = (EditText) findViewById(R.id.bottom_text);
		bottom.setDrawingCacheEnabled(true);
	}

	@Override
	public void setComponentListener() {
		confirm.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				
				int width = getWindowManager().getDefaultDisplay().getWidth();
		        int height = getWindowManager().getDefaultDisplay().getHeight();
		        
		        result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		        Canvas comboImage = new Canvas(result);
		        
		        originalBm = Bitmap.createScaledBitmap(originalBm, width, height, true);
		        comboImage.drawBitmap(originalBm, 0, 0, null);
				if(!top.getText().equals("")){
					 Bitmap bmp = Bitmap.createBitmap(top.getDrawingCache());
				     comboImage.drawBitmap(bmp, width/2 - bmp.getWidth()/2, 0, null);
				}
				
				if(!bottom.getText().equals("")){
					 Bitmap bmp = Bitmap.createBitmap(bottom.getDrawingCache());
				     comboImage.drawBitmap(bmp, width/2 - bmp.getWidth()/2, height - bmp.getHeight() - 100, null);
				}
				
				ImageTools.savePhotoToSDCard(result, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cuter", 
						String.valueOf(System.currentTimeMillis()));
				Toast.makeText(getApplicationContext(), "Storage succeed!", 1000).show();
				
				memeResult.setVisibility(View.VISIBLE);
				memeImg.setImageBitmap(result);
			}
			
		});
		
		memeImg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				activityJump(SourceSelActivity.class);
			}
			
		});
		
		cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
			
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
