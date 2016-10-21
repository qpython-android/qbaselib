package com.quseit.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class AVUtil {
	@SuppressWarnings("unused")
	private static final String TAG = "AVUtil";

	public void getMP3Info() {
		/*
		MediaScannerConnection msc = new MediaScannerConnection(this,
				                                new MediaScannerConnectionClient() {
				         public void onMediaScannerConnected() {
				                for (final File file : fileList) {
				                         mpFile = file;
				                         msc.scanFile(mpFile.getAbsolutePath(), null);
				                         Log.d("MSC", mpFile.getAbsolutePath());
				                 }
				         }
				         public void onScanCompleted(String path, Uri uri) {
				                 Log.d("MPSCAN", "Complete"+ MediaStore.getMediaScannerUri());
				                  msc.disconnect();
				          }
				});
				 msc.connect();
				Uri Uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				String[] Selection = new String[] {
				                                android.provider.MediaStore.Audio.Media._ID,
				                                android.provider.MediaStore.Audio.Media.TITLE,
				                                android.provider.MediaStore.Audio.Media.DATA,
				                                android.provider.MediaStore.Audio.Media.ARTIST,
				                                android.provider.MediaStore.Audio.Media.ALBUM, };
				 
				Cursor mCursor = managedQuery(Uri, Selection,null, null, null);
				mCursor.moveToFirst();
				while(mCursor.moveToNext()) {
				      mp3List.add(mCursor.getString(mCursor.getColumnIndexOrThrow("ARTIST")));
				}
	*/
	}
	
	public Mp3Info getItem(Context context) {
		Mp3Info minfo = new Mp3Info();

		ContentResolver resolver = context.getContentResolver();
		try {
			Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String [] {android.provider.MediaStore.Audio.Media.TITLE,android.provider.MediaStore.Audio.Media.ARTIST,android.provider.MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATA }, null, null, null);
			cursor.moveToNext();
			minfo.title = cursor.getString(0);
			minfo.artist = cursor.getString(1);
			minfo.album = cursor.getString(2);
			minfo.duration = cursor.getInt(3);
			cursor.close();
		} catch (Exception e) {
			
		}
		return minfo;
	}
	/*
	 * private void scanSdCard() {

                   IntentFilter intentFilter = new IntentFilter(

                                     Intent.ACTION_MEDIA_SCANNER_STARTED);

                   intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);

                   intentFilter.addDataScheme("file");

                   scanReceiver = new ScanSdFilesReceiver();

                   registerReceiver(scanReceiver, intentFilter);

                   sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,

                                     Uri.parse("file://" + getExternalStorageDirectory().getAbsolutePath)));

         }

 

         private class ScanSdFilesReceiver extends BroadcastReceiver {

                   public void onReceive(Context context, Intent intent) {

                            String action = intent.getAction();

                            if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {

//当系统开始扫描sd卡时，为了用户体验，可以加上一个等待框

                                     scanHandler.sendEmptyMessage(STARTED);

                            }

                            if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {

                            //当系统扫描完毕时，停止显示等待框，并重新查询ContentProvider

                                     scanHandler.sendEmptyMessage(FINISHED);

                            }

                   }

         }*/
	 
	
	@SuppressWarnings("unused")
	private Cursor getCursor(Context context,String filePath) {  
		String path = null;  

	    Cursor c = context.getContentResolver().query(  
	              MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,  
	              MediaStore.Audio.Media.DEFAULT_SORT_ORDER);  
	      // System.out.println(c.getString(c.getColumnIndex("_data")));  
	    if (c.moveToFirst()) {  
	    	do {  
	    		// 通过Cursor 获取路径，如果路径相同则break； 
	            path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)); 
	              // 查找到相同的路径则返回，此时cursorPosition 便是指向路径所指向的Cursor 便可以返回了  
	            if (path.equals(filePath)) {  
	            	// System.out.println("audioPath = " + path);  
	                // System.out.println("filePath = " + filePath);  
	                // cursorPosition = c.getPosition();
	            	break;
	            }  
	    	} while (c.moveToNext());  
	    }  
	    // 这两个没有什么作用，调试的时候用  
	    // String audioPath = c.getString(c  
	    // .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));  
	    //  
	    // System.out.println("audioPath = " + audioPath);  
	    return c;  
	} 
		 
	@SuppressWarnings("unused")
	private static String getAlbumArt(Context context, int album_id) {  
		String mUriAlbums = "content://media/external/audio/albums";  
	    String[] projection = new String[] { "album_art" };  
	    Cursor cur = context.getContentResolver().query(  
	              Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  
	              projection, null, null, null);  
	    String album_art = null;  
	    if (cur.getCount() > 0 && cur.getColumnCount() > 0) {  
	    	cur.moveToNext();  
	        album_art = cur.getString(0);  
	    }  
	    cur.close();  
	    cur = null;  
	    return album_art;  
	 }
	     /*
	     private void getImage(Context context){
	      Cursor currentCursor = getCursor("/mnt/sdcard/"+mp3Info);
	   int album_id = currentCursor.getInt(currentCursor  
	                  .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)); 
	      String albumArt = AVUtil.getAlbumArt(context,album_id);
	      Bitmap bm = null;
	      if (albumArt == null) {  
	       mImageView.setBackgroundResource(R.drawable.staring);  
	      } else {  
	          bm = BitmapFactory.decodeFile(albumArt);  
	          BitmapDrawable bmpDraw = new BitmapDrawable(bm);  
	          mImageView.setImageDrawable(bmpDraw);  
	      } 
	     }*/
}
