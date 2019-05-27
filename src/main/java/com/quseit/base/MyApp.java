package com.quseit.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.quseit.config.CONF;
import com.quseit.util.FileHelper;
import com.quseit.util.NAction;
import com.quseit.util.NUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
  
public class MyApp extends Application {  
	private static final String TAG = "MyApp";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Object> taskQueue = new ArrayList();

    private static MyApp instance;  

    public ArrayList<Object> getTaskQueue() {
    	return taskQueue;
    }
    
    private MyApp() {  
    }  
    public static MyApp getInstance() {
	    if(null == instance) {  
	    	instance = new MyApp();  
	    }  
	    return instance;   
  
    }
    /*public String getRoot() {
    	return Environment.getExternalStorageDirectory()+"/qpython";
	}*/

}  
