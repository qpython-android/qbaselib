package com.quseit.util;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;


public class NMedia {
	private static final String TAG = "NMedia";

	public static Intent itUseCamera(Context context, Uri uri) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
	}
	
	public static Intent getCropImageIntent(Uri photoUri, Uri targetUri) {  
		Intent intent = new Intent("com.android.camera.action.CROP");  
	    intent.setDataAndType(photoUri, "image/*");  
	    intent.putExtra("crop", "true");  
	    intent.putExtra("aspectX", 1);  
	    intent.putExtra("aspectY", 1);  
	    intent.putExtra("outputX", 300);  
	    intent.putExtra("outputY", 300);  
        intent.putExtra("output", targetUri); 
        intent.putExtra("outputFormat", "JPEG");
	    intent.putExtra("return-data", false);
	    return intent;  
	  } 
	
	public static Intent itUsePholib(Context context, Uri uri, String title) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); 
        intent.putExtra("crop", "true");
        intent.putExtra("output", uri); 
	    intent.putExtra("aspectX", 1);  
	    intent.putExtra("aspectY", 1);  
	    intent.putExtra("outputX", 300);  
	    intent.putExtra("outputY", 300);  
        intent.putExtra("outputFormat", "JPEG");
        return Intent.createChooser(intent, title);
	}
	
	public static void doVibrator(Context context) {
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		//long[] pattern = {20000, 1000, 2000, 1000}; // OFF/ON/OFF/ON...
		vibrator.vibrate(500);
		//vibrator.vibrate(pattern, -1);
		//vibrator.cancel();
		Log.d(TAG, "doVibrator");
	}
}
