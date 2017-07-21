package com.quseit.backend;

import com.quseit.util.Log;
import com.quseit.util.UnhandledExceptionHandler;

import android.os.Handler;
import android.os.Looper;


public class LooperThread extends Thread {
	private boolean mRunning = false;
	private Handler mHandler = null;
	private static final String TAG = "LooperThread";
	
	@Override
	public void run() {
		Thread.setDefaultUncaughtExceptionHandler(new UnhandledExceptionHandler());
		
		mRunning = true;
		
		Looper.prepare();
		
		while (mRunning) {
			try {
				synchronized (this) {
					mHandler = new Handler();
					notifyAll();
				}
	
				Looper.loop();
			} catch (Throwable t) {
				Log.e(TAG, "halted due to an error", t);
			}
		}
		
		mRunning = false;
	}
	
	@Override
	public void interrupt() {
		if (mRunning) {
			mRunning = false;
			Looper.myLooper().quit();
		}
		super.interrupt();
	}
	
	public Handler getHandler() {
		synchronized (this) {
			while (mHandler == null) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
		}
		return mHandler;
	}
}
