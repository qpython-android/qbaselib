package com.quseit.media;

import java.io.File;
import java.io.IOException;

import com.quseit.base.MyApp;
import com.quseit.config.CONF;
import com.quseit.util.Base64;
import com.quseit.util.DateTimeHelper;
import com.quseit.util.FileHelper;
import com.quseit.util.ImageUtil;
import com.quseit.util.MD5;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;

public class MVUtil {
	@SuppressWarnings("unused")
	private static final String TAG = "MVUtil";

	/*static Bitmap createVideoThumbnail(String filePath, int kind) {
		
	}*/

	@SuppressWarnings("finally")
	@TargetApi(8)
	public static Bitmap createVideoThumbnail(String filePath, final int width, final int height) {
		//return null;
		try {
	        Bitmap bitmap=ThumbnailUtils.createVideoThumbnail(filePath,Thumbnails.MINI_KIND); 
	        if (bitmap == null) return null;
	        return android.media.ThumbnailUtils.extractThumbnail(bitmap, width, height);
        } catch (NoSuchMethodError e) {
        	return null;
        } catch (NoClassDefFoundError e) {
        	return null;
        } catch (Exception e) {
        	return null;
        	
        } finally {
        	return null;
        }
        /*
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //retriever.setMode(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            retriever.setDataSource(filePath);
            
            bitmap = retriever.getFrameAtTime(0);
        } catch(IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
        } catch (NoSuchMethodError e) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;*/
    }
	@SuppressWarnings("unused")
	public static Bitmap getThumbnailWithCreator(final Uri uri, final int width, final int height) {
		final String cacheDir = Environment.getExternalStorageDirectory()+"/"+MyApp.getInstance().getRoot()+"/"+CONF.DCACHE+"/";
    	final String imgHash = MD5.encrypByMd5(Base64.encode(uri.getPath()));
		String imgHashPath;
		try {
			imgHashPath = FileHelper.getBasePath(MyApp.getInstance().getRoot(),CONF.DCACHE)+"/"+imgHash;
	    	File imgCache = new File(imgHashPath);

			Bitmap img = MVUtil.createVideoThumbnail(uri.getPath(), width, height);
			
			if (img!=null) {
				String thumb = ImageUtil.saveBitmap(cacheDir+imgHash, img);
			} else {
		    	if (!imgCache.exists()) {
		    		try {
						imgCache.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
		    	}
			}
			return img;
		} catch (IOException e) {
			//e.printStackTrace();
		}


		return null;
	}
	
	public static Bitmap getThumbnailCache(Context context, ContentResolver cr, final Uri uri, final int width, final int height, String root) {
    	Bitmap img = MVUtil.getVideoThumbnail(cr, uri);
    	if (img != null) {
    		return img;
    	} else {
	    	final String imgHash = MD5.encrypByMd5(Base64.encode(uri.getPath()));
			String imgHashPath;
			try {
				imgHashPath = FileHelper.getBasePath(root, CONF.DCACHE)+"/"+imgHash;
		    	File imgCache = new File(imgHashPath);
	
		    	if (imgCache.canRead()) {
			    	imgCache.setLastModified(DateTimeHelper.getNowTime());
		    		return ImageUtil.getBitFromImg(imgHashPath);
		    	}
			} catch (Exception e) {
			}
    	}
		return null;

	}
    public static Bitmap getThumbnail(Context context, ContentResolver cr, final Uri uri, boolean createFlag, final int width, final int height, final int after) {
    	Bitmap img = MVUtil.getVideoThumbnail(cr, uri);
    	
    	if (img != null) {
    		return img;
    	} else {
    		final String cacheDir = Environment.getExternalStorageDirectory()+"/"+MyApp.getInstance().getRoot()+"/"+CONF.DCACHE+"/";
	    	final String imgHash = MD5.encrypByMd5(Base64.encode(uri.getPath()));
			String imgHashPath;
			try {
				imgHashPath = FileHelper.getBasePath(MyApp.getInstance().getRoot(), CONF.DCACHE)+"/"+imgHash;
		    	File imgCache = new File(imgHashPath);

		    	if (imgCache.canRead()) {
			    	imgCache.setLastModified(DateTimeHelper.getNowTime());
		    		return ImageUtil.getBitFromImg(imgHashPath);
		    	} else {
		    		if (createFlag && after<4) {
			    		Handler mHandler = new Handler();
			    		mHandler.postDelayed(new Runnable() {
							@SuppressWarnings("unused")
							@Override
							public void run() {
								Bitmap img = MVUtil.createVideoThumbnail(uri.getPath(), width, height);
								if (img!=null) {
									String thumb = ImageUtil.saveBitmap(cacheDir+imgHash, img);
								} else {
						        	File imgCache = new File(cacheDir+imgHash);
							    	if (!imgCache.exists()) {
							    		try {
											imgCache.createNewFile();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
							    	}
								}
							}
			    			
			    		},200+after*100);
		    		}
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


    		return null;
    	}
    }
	@TargetApi(5)
	public static Bitmap getVideoThumbnail(ContentResolver cr, Uri uri) {  
		/*try {
	        long id = ContentUris.parseId(uri);
	        Bitmap miniThumb = Video.Thumbnails.getThumbnail(cr, id,
	                Video.Thumbnails.MINI_KIND, null);
	        return miniThumb;  
		} catch (NumberFormatException e) {
			Log.d(TAG, "error:"+e.getMessage());
			return null;
		}*/

        try {
        Bitmap bitmap = null;  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        
        //options.inDither = false;  
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;  
        Cursor cursor = cr.query(uri,new String[] { MediaStore.Video.Media._ID }, null, null, null);   
      
        if (cursor == null || cursor.getCount() == 0) {  
        	//Log.d(TAG, "null");
            return null;  
        }  
        cursor.moveToFirst();  
        String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));  //image id in image table.s  
  
        if (videoId == null) {  
        	return null;  
        }  
        cursor.close();  
        long videoIdLong = Long.parseLong(videoId);  
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,Images.Thumbnails.MINI_KIND, options);  
  		return bitmap;
        } catch (Exception e) {
        	return null;
        }
  		
	}  
}
