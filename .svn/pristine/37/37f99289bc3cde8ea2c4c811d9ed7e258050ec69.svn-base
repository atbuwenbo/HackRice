package com.cuter.util;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
/**
 * @author buwenbo International School of Software, Wuhan University
 */

public class  AppExit extends Application {
    
private List<Activity> activities = new ArrayList<Activity>();

private static AppExit instance;

     private AppExit(){
      
     }
    
     public static AppExit getInstance(){
       if(null==instance){
           
        instance = new AppExit();
       
       }
      
       return instance;
      
     }

     public void addActivity(Activity activity) {
         
       activities.add(activity);
    
     }
@Override
public void onTerminate() {
    
       super.onTerminate();
      
       for (Activity activity : activities) { 
            activity.finish();
       }
      
       android.os.Process.killProcess(android.os.Process.myPid()) ;
      
     }
}


