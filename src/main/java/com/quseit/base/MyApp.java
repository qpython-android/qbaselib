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
    @SuppressWarnings("rawtypes")
	//public List activityList = new LinkedList();
    
    private static MyApp instance;  
    
    public String root;
    public String subDir = "";
    public boolean isMvMode;
    public boolean flag1 = false;
    
    public ArrayList<Object> getTaskQueue() {
    	return taskQueue;
    }
    
    private MyApp() {  
    }  
    //单例模式中获取唯一的ExitApplication实例  
//    public int size() {
//    	//Log.d(TAG, "size:"+activityList.size());
//    	return activityList.size();
//    }
    public static MyApp getInstance() {  
	    if(null == instance) {  
	    	instance = new MyApp();  
	    }  
	    return instance;   
  
    }  
//    public Context getContext() {
//    	if (activityList.size()>0) {
//    		if (CONF.DEBUG) Log.d(TAG, "get Context ok");
//    		Activity act = (Activity) activityList.get(0);
//    		return act.getApplicationContext();
//    	} else {
//    		if (CONF.DEBUG) Log.d(TAG, "get Context null");
//
//    		return null;
//    	}
//    }
    //添加Activity到容器中  
	public void addActivity(Activity activity, String rootDir, String subDirectory) {  
    	//this.activityList.add(activity);
    	root = rootDir;
    	subDir = subDirectory;
    	//NStorage.setSP(this.getContext(), "global.root", rootDir);
    }
    
	public void addActivity(Activity activity) {  
    	//this.activityList.add(activity);
    } 
    public void exit() {  
//    	for (int i=0;i<this.activityList.size();i++) {
//    		Activity act = (Activity) this.activityList.get(i);
//    		act.finish();
//
//    	}
//    	this.activityList = new LinkedList();
    }  
    // 本地目录缓存
    Boolean cacheSet = false;
    Map<String, Integer> movieDirs = new HashMap<String, Integer>();
    public Map<String, Integer> getAvaiDirs(String dirname, boolean force, boolean isMvMode) {
    	this.isMvMode = isMvMode;
        if (!cacheSet || force) {
            setDirCache();
            cacheSet = true;
        }
        return movieDirs;
    }

    public void setDirCache() {
        // 遍例整个files目录下的所有文件及文件夹
        //String filename="";
        String fullfn;
        int filesCount;

        if (NUtil.isExternalStorageExists()) {

            try {
                File[] files = FileHelper.getABSPath(Environment.getExternalStorageDirectory().getAbsolutePath()).listFiles();
                if (files!=null) {
                    //Arrays.sort(files, sortType);
                    for (File file : files) {
                        fullfn = file.getPath().toString();

                        //filename = file.getName();
                        if (file.isDirectory()) {
                            filesCount = checkMvNums(fullfn);
                            if (CONF.DEBUG) Log.d(TAG, "cache: fullfn:"+fullfn+"-count:"+filesCount);
                            movieDirs.put(fullfn, filesCount);
                        }
                    }
                }
            } catch (IOException e) {
            	Log.d(TAG, "IOException:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public int checkMvNums(String curDir) {
        int total = 0;
        String filename,fullfn;
        int filesCount;

        try {
            File[] files = FileHelper.getABSPath(curDir).listFiles();
            if (files!=null) {
                //Arrays.sort(files, sortType);
                for (File file : files) {
                    fullfn = file.getPath().toString();

                    filename = file.getName();
                    if (file.isDirectory()) {
                    	int depth = fullfn.split("/").length;
                    	if (depth>5) {	// 忽略5层后
                    		return 0;
                    	}
                        filesCount = checkMvNums(fullfn+"/");
                        if (filesCount>0) {
                            return 1;
                        }
                        /*
                        total = total+checkMvNums(fullfn+"/");*/

                    }  else {
                        String ext = FileHelper.getExt(filename.toLowerCase(),"").toLowerCase();
                        if (this.isMvMode) {
	                        if (!ext.equals("") && CONF.MVEXT.contains("#"+ext+"#")) {
	                        	return 1;
	                        }
                        } else {
	                        if (!ext.equals("") && CONF.MUEXT.contains("#"+ext+"#")) {
	                        	return 1;
	                        }
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }
    
    public String getRoot() {
    	//String root = NStorage.getSP(this.getContext(), "global.root");
    	//Log.d(TAG, "getRoot:"+root);
    	if (root == null) {
    		return CONF.DEFAULT_ROOT;
    	} else {
    		return root;
    	}
    }
    
    public String getSaveRoot(Context context, int Q) {
    	try {
	        String root = NAction.getDefaultRoot(context);
	        String rootDir;
			if (root.equals("")) {
                rootDir = new File(FileHelper.getBasePath(CONF.DEFAULT_ROOT, "Quseit"),"").getAbsolutePath();

			} else {
				rootDir = root;
			}
			return rootDir;
		} catch (IOException e) {
			return "";
		}
    }
    public String getFullRoot() {
    	//String root = NStorage.getSP(this.getContext(), "global.root");
    	//Log.d(TAG, "getRoot:"+root);
    	if (subDir.equals("")) {
    		return root;
    	} else {
    		return root+"/"+subDir;
    	}
    }

    public void gc() {
		System.gc();
	    Runtime.getRuntime().gc();
    }
    
    public void onEditTextFocus(View view){
    	EditText v = (EditText)view;
    	v.setSelection(v.length());
    }

}  
