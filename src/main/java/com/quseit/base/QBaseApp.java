package com.quseit.base;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.loopj.android.http.AsyncHttpClient;
import com.quseit.config.BASE_CONF;
import com.quseit.util.FileUtils;

import java.io.File;

import java.util.ArrayList;

public class QBaseApp {
    private static final String TAG = "QBaseApp";

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public AsyncHttpClient getAsyncHttpClient() {
        return asyncHttpClient;
    }

    private static QBaseApp instance;

    private QBaseApp() {
    }

    public static QBaseApp getInstance() {
        if (null == instance) {
            instance = new QBaseApp();
        }
        return instance;
    }

    // Activity 队列
    ArrayList<Activity> activities = new ArrayList<>();

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void popActivity(Activity activity) {
        this.activities.remove(activity);
    }

    // 本地目录
    // TODO: 允许设置本地目录
    private String root = null;

    public String getOrCreateRoot(Context context, String subDir) {
//	    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ (root==null?BASE_CONF.DEFAULT_ROOT:root)
//        FileUtils.createTestDir(context,"test");
        String path = FileUtils.getQyPath(context) + (root == null ? BASE_CONF.DEFAULT_ROOT : root)
                + (subDir != null
                ? ("/" + subDir)
                : "");
        File root = new File(path);
        if (!root.exists()) {
            root.mkdirs();
        }
        return path;
    }

    // 资源回收
    public void gc() {
        System.gc();
        Runtime.getRuntime().gc();
    }
}  
