package com.cuter.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cuter.img.BilateralFilter;
import com.cuter.img.ImageTools;
import com.cuter.util.AppExit;
import com.cuter.util.ThreadPoolUtil;

public class SourceSelActivity extends BaseActivity {

	private ImageButton photoChoiceBtn;
	
	private ImageView photoView;
	
	private Bitmap originalBitmap;
	
	private Bitmap processedBitmap = null;
	
	private Button rechoose;
	
	private Button meme;
	
	private Button compare;
	
	private GestureDetector sGestureDetector;
	
	private String imgPath;
	
	private LinearLayout bottom;
	
	private RelativeLayout navigation;
	
	private AbsoluteLayout styles;
	
	private LinearLayout style1;
	
	private LinearLayout style2;
	
	private LinearLayout style3;
	
	private LinearLayout style4;
	
	private LinearLayout style5;
	
	private RelativeLayout pbLayout;
	
	private BilateralFilter filter;
	
	private int currentX = 0;
	
	private MarginLayoutParams params;
	

	
	private Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			photoView.setImageBitmap(processedBitmap);
			pbLayout.setVisibility(View.GONE);
			Log.e("msg", msg.what + "");
		}
	};
	
	private ProgressBar pb;
	
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	private static final int CROP = 2;
	private static final int SCALE = 2;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_source_sel);
		
		
		
		sGestureDetector = new GestureDetector(new StyleGestureListener());
		filter = new BilateralFilter();
		initComponent();
		setComponentListener();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initComponent() {
		photoChoiceBtn = (ImageButton) findViewById(R.id.photo_choice_btn);
		photoView = (ImageView) findViewById(R.id.photo_view);
		
		rechoose = (Button) findViewById(R.id.rechoose);
		compare = (Button) findViewById(R.id.compare);
		meme = (Button) findViewById(R.id.meme);
		pbLayout = (RelativeLayout) findViewById(R.id.pb_layout);
		pb = (ProgressBar) findViewById(R.id.img_pb);
		pb.setIndeterminate(false);
		
		navigation = (RelativeLayout) findViewById(R.id.navigation);
		style1 = (LinearLayout) findViewById(R.id.style1);
		style2 = (LinearLayout) findViewById(R.id.style2);
		style3 = (LinearLayout) findViewById(R.id.style3);
		style4 = (LinearLayout) findViewById(R.id.style4);
		style5 = (LinearLayout) findViewById(R.id.style5);
		
		bottom = (LinearLayout) findViewById(R.id.bottom);
		styles = (AbsoluteLayout) findViewById(R.id.styles);
	}
	
	
	@Override
	public void setComponentListener(){
		photoChoiceBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				showPicturePicker(SourceSelActivity.this, false);
			}
			
		});
		
		rechoose.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				photoChoiceBtn.setVisibility(View.VISIBLE); 
				photoView.setVisibility(View.INVISIBLE);
				navigation.setVisibility(View.GONE);
			}
			
		});
		
		compare.setOnTouchListener(new OnTouchListener(){

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if(v.getId() == R.id.compare){
					switch(action){
						case MotionEvent.ACTION_DOWN:
							photoView.setImageBitmap(originalBitmap);
							break;
						case MotionEvent.ACTION_UP:
							photoView.setImageBitmap(processedBitmap);
							break;
					}
				}
				return true;
			}
			
		});
		
		bottom.setOnTouchListener(new OnTouchListener(){

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(v.getId() == R.id.bottom){
					sGestureDetector.onTouchEvent(event);
				}
				return true;
			}
			
		});
		
		style1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				pbLayout.setVisibility(View.VISIBLE);
				pb.setProgress(0);
				ThreadPoolUtil.execute(new Runnable(){

					@Override
					public void run() {
						processedBitmap = filter.bflt(originalBitmap, 0);
						handler.sendEmptyMessage(0);
					}
					
				});
				
				
			}
			
		});
		
		style2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				pbLayout.setVisibility(View.VISIBLE);
				pb.setProgress(0);
				ThreadPoolUtil.execute(new Runnable(){

					@Override
					public void run() {
						processedBitmap = filter.bflt(originalBitmap, 1);
						handler.sendEmptyMessage(0);
					}
					
				});
				
				
			}
			
		});
		
		style3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				pbLayout.setVisibility(View.VISIBLE);
				pb.setProgress(0);
				ThreadPoolUtil.execute(new Runnable(){

					@Override
					public void run() {
						processedBitmap = filter.bflt(originalBitmap, 2);
						handler.sendEmptyMessage(0);
					}
					
				});
				
				
			}
			
		});
		
		style4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				pbLayout.setVisibility(View.VISIBLE);
				pb.setProgress(0);
				ThreadPoolUtil.execute(new Runnable(){

					@Override
					public void run() {
						processedBitmap = filter.bflt(originalBitmap, 3);
						handler.sendEmptyMessage(0);
					}
					
				});
				
				
			}
			
		});
		
		style5.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				pbLayout.setVisibility(View.VISIBLE);
				pb.setProgress(0);
				ThreadPoolUtil.execute(new Runnable(){

					@Override
					public void run() {
						processedBitmap = filter.bflt(originalBitmap, 4);
						handler.sendEmptyMessage(0);
					}
					
				});
				
				
			}
			
		});
		
		meme.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SourceSelActivity.this, MemeActivity.class);
				intent.putExtra("img", processedBitmap);
				startActivity(intent);
			}
			
		});
		
		photoView.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(SourceSelActivity.this);
					builder.setNegativeButton("Cancel", null);
					builder.setTitle("What to do?");
					builder.setItems(new String[]{"Share to...", "Save it!"},  new DialogInterface.OnClickListener(){
						
						

						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch(which){
							case 0:
								activityJump(LoginActivity.class);
								break;
							case 1:
						 		ImageTools.savePhotoToSDCard(processedBitmap, Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Cuter", 
						 				String.valueOf(System.currentTimeMillis()));
						 		Toast.makeText(getApplicationContext(),
						 				"Storage succeed" + Environment.getExternalStorageDirectory().getAbsolutePath() + " /Cuter", 1000).show();
								break;
							}
						}
						
					});
				builder.create().show();
				return true;
			}
			
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				imgPath = Environment.getExternalStorageDirectory()+"/image.jpg";
				Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
				bitmap.recycle();
				
				originalBitmap = newBitmap;
				photoView.setImageBitmap(newBitmap);
				photoChoiceBtn.setVisibility(View.INVISIBLE);
				photoView.setVisibility(View.VISIBLE);
				navigation.setVisibility(View.VISIBLE);
				
				imgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cuter" + String.valueOf(System.currentTimeMillis());
		 		ImageTools.savePhotoToSDCard(newBitmap, imgPath, String.valueOf(System.currentTimeMillis()));
				
				break;
 
			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				Uri originalUri = data.getData(); 
	            try {
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
						photo.recycle();
						
						originalBitmap = smallBitmap;
						photoView.setImageBitmap(smallBitmap);
						photoView.setVisibility(View.VISIBLE);
						navigation.setVisibility(View.VISIBLE);
					}
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			
			default:
				break;
			}
		}
	
	}
	
	@SuppressLint("WorldWriteableFiles")
	public void showPicturePicker(Context context,boolean isCrop){
		final boolean crop = isCrop;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Cuter, make the art");
		builder.setNegativeButton("Cancel", null);
		builder.setItems(new String[]{"Use Camera","Select Photo"}, new DialogInterface.OnClickListener() {
			int REQUEST_CODE;
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case TAKE_PICTURE:
					Uri imageUri = null;
					String fileName = null;
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (crop) {
						REQUEST_CODE = CROP;
						SharedPreferences sharedPreferences = getSharedPreferences("temp",Context.MODE_WORLD_WRITEABLE);
						ImageTools.deletePhotoAtPathAndName(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Cuter", sharedPreferences.getString("tempName", ""));
						
						fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
						Editor editor = sharedPreferences.edit();
						editor.putString("tempName", fileName);
						editor.commit();
					}else {
						REQUEST_CODE = TAKE_PICTURE;
						fileName = "image.jpg";
					}
					imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, REQUEST_CODE);
					break;
					
				case CHOOSE_PICTURE:
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					if (crop) {
						REQUEST_CODE = CROP;
					}else {
						REQUEST_CODE = CHOOSE_PICTURE;
					}
					openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(openAlbumIntent, REQUEST_CODE);
					break;

				default:
					break;
				}
			}
		});
		builder.create().show();
	}

	
	class StyleGestureListener extends SimpleOnGestureListener {

		 @Override
		 public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//		       		Log.e("Scrolling", distanceX +", " + distanceY);
		       		params = new MarginLayoutParams(styles.getLayoutParams());
		       		params.setMargins(currentX, params.topMargin, currentX + params.width, params.bottomMargin);
		       		currentX -= distanceX;
		       		LinearLayout.LayoutParams newParams = new LinearLayout.LayoutParams(params);
		       		styles.setLayoutParams(newParams);
		       		return false;
		         }
		 
		 @Override
		 public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	    			Log.e("Flinging", distanceX +", " + distanceY);
	    			
	    			return false;
		         }
		
		
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
