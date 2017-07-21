package com.quseit.backend;

import java.util.LinkedList;

import com.quseit.util.Log;

import android.os.AsyncTask;
import android.os.Handler;
//import android.os.HandlerThread;
import android.os.Looper;

interface ThreadManagable {
	public void startThread();
}

public abstract class ManagedThread<Params,Progress,Result> extends AsyncTask<Params,Progress,Result> implements ThreadManagable {	
	private static final ThreadManager mThreadManager = ThreadManager.getInstance();
	private ThreadState mThreadState = ThreadState.PENDING;
	private ThreadPriority mPriority = ThreadPriority.LOW;
	
	public enum ThreadState {
		PENDING,
		QUEUED,
		RUNNING,
		FINISHED
	}
	
	public enum ThreadPriority {
		HIGH,
		MEDIUM,
		LOW
	}
	
	public final void execute() {
		this.execute(ThreadPriority.LOW);
	}
	
	public final void execute(ThreadPriority priority) {
		mPriority = priority;
		mThreadState = ThreadState.QUEUED;
		mThreadManager.queueThread(this, priority);
	} 
	
	public boolean setThreadPriority(ThreadPriority tp) {
		if ((mPriority != tp) && 
			(mThreadState == ThreadState.QUEUED) &&
			(mThreadManager.changeThreadPriority(this, mPriority, tp))) {
			mPriority = tp;
			return true;
		} else {
			return false;
		}
	}
	
	public void startThread() {
		mThreadState = ThreadState.RUNNING;
		super.execute((Params[])null);
	}
	
	@Override
	protected final void onPostExecute(final Result result) {
		mThreadManager.finishThread(this);

		// Make again sure we are in the right thread
		if (!Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
			Log.e("ManagedThread", "Posting runnable to main thread");
			ThreadManager.mHandler.post(new Runnable(){
				public void run() {
					onTaskFinished(result);
					mThreadState = ThreadState.FINISHED;
				}
			});
		} else {
			onTaskFinished(result);
			mThreadState = ThreadState.FINISHED;
		}
	}
	
	abstract protected void onTaskFinished(Result result);
	
	public ThreadState getThreadState() {
		return mThreadState;
	}
	
	private static class ThreadManager {
		private final static String TAG = "ThreadManager";
		
		LinkedList<ThreadManagable> mThreadsHigh 	= new LinkedList<ThreadManagable>();
		LinkedList<ThreadManagable> mThreadsMedium 	= new LinkedList<ThreadManagable>();
		LinkedList<ThreadManagable> mThreadsLow 	= new LinkedList<ThreadManagable>();
		
		private static Handler mHandler = new Handler(Looper.getMainLooper());
		
		private int mThreadsRunning = 0;
		private static int MAX_NUMBER_OF_CONCURRENT_THREADS = 4;
		
		private static ThreadManager INSTANCE = new ThreadManager();
		private ThreadManager() {}
		
		public static ThreadManager getInstance() {return INSTANCE;}
		
		synchronized private void queueThread(ThreadManagable t, ThreadPriority p) {
			switch (p) {
			case HIGH:
				mThreadsHigh.addLast(t);
				break;
			case MEDIUM:
				mThreadsMedium.addLast(t);
				break;
			default:
			case LOW:
				mThreadsLow.addLast(t);
				break;
			}
			
			if (mThreadsRunning < MAX_NUMBER_OF_CONCURRENT_THREADS) {
				startNextThread();
			}
		}

		synchronized private void finishThread(ThreadManagable t) {
			mThreadsRunning--;
			startNextThread();
		}

		synchronized private void startNextThread() {
			final ThreadManagable nextThread;
			
			if (!mThreadsHigh.isEmpty()) {
				nextThread = mThreadsHigh.removeFirst();
			} else if (!mThreadsMedium.isEmpty()) {
				nextThread = mThreadsMedium.removeFirst();
			} else if (!mThreadsLow.isEmpty()) {
				nextThread = mThreadsLow.removeFirst();
			} else {
				return;
			}

			if (!mHandler.getLooper().getThread().equals(Thread.currentThread())) {
				// Special case when thread is executed from another thread instead of the UI thread
				// We must init execution from the UI thread for AsyncThread to work
				Log.d(TAG, "Posting runnable to main thread");
				mHandler.post(new Runnable() {
					public void run() {
						nextThread.startThread();						
					}
				});
			} else {
				nextThread.startThread();
			}
			
			mThreadsRunning++;
		}
		
		synchronized private boolean changeThreadPriority(ThreadManagable t, ThreadPriority oldPriority, ThreadPriority newPriority) {
			switch (oldPriority) {
			case HIGH:
				if (!mThreadsHigh.remove(t)) {
					return false;
				}
				break;
			case MEDIUM:
				if (!mThreadsMedium.remove(t)) {
					return false;
				}
				break;
			case LOW:
				if (!mThreadsLow.remove(t)) {
					return false;
				}
				break;
			}
			
			//Log.d("ManagedThread", "Changed thread priority to " + newPriority);
			queueThread(t, newPriority);
			return true;
		}
	}
}
