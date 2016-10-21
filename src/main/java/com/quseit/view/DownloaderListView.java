package com.quseit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class DownloaderListView extends ListView {
	private static final String TAG = "DownloaderListView";

	public DownloaderListView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}
	
	public void render(int flag) {
		
		switch (flag) {
		case 0:
			loadHistory();
			break;
		case 1:
			loadLocal();
			break;
		case 2:
			loadLocalByCategory();
			break;
		}
	}
	
	public void loadHistory() {
		Log.d(TAG, "loadHistory");
		//ListAdapter adapter = this.getAdapter();
	}
	
	public void loadLocal() {
		Log.d(TAG, "loadLocal");
	}
	public void loadLocalByCategory() {
		Log.d(TAG, "loadLocalByCategory");
	}
}
