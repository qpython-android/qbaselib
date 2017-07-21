/*
  */
package com.quseit.view;

//import com.quseit.widget.AsyncImageView;
//import com.quseit.android.R;
//import com.quseit.config.CONF;
//import com.quseit.item.VideoItem;

//import greendroid.widget.AsyncImageView;
//import greendroid.widget.item.Item;
//import greendroid.widget.itemview.ItemView;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quseit.android.R;


public class VideoItemView extends LinearLayout /*implements ItemView*/ {
	private static final String TAG = "VideoItemView";

    private TextView mHeaderView;
    private TextView mTextView;
    private TextView dTextView;
    //private TextView vTextView;

    private TextView oTextView;
//    private AsyncImageView imageThumb;
    private ImageView  imageStat;

    public VideoItemView(Context context) {
        this(context, null);
    }

    public VideoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void prepareItemView() {
//        mHeaderView = (TextView) findViewById(R.id.gd_separator_text);
        mTextView = (TextView) findViewById(R.id.gd_text);
        dTextView = (TextView) findViewById(R.id.gd_text_desc);
        //vTextView = (TextView) findViewById(R.id.gd_text_viwes);

//        imageThumb = (AsyncImageView) findViewById(R.id.item_thumbnail);
        oTextView = (TextView) findViewById(R.id.gd_text_other);
        imageStat = (ImageView) findViewById(R.id.gd_stat_image);
    }

    public void setObject(/*Item object*/) {
//    	try {
//	        final VideoItem item = (VideoItem) object;
//	        final String headerText = item.headerText;
//
//	        if (!TextUtils.isEmpty(headerText)) {
//	            mHeaderView.setText(headerText);
//	            mHeaderView.setVisibility(View.VISIBLE);
//	        } else {
//	            mHeaderView.setVisibility(View.GONE);
//	        }
//
//	        mTextView.setText(item.text);
//	        dTextView.setText(item.desc);
//
//	        if (CONF.DEBUG) Log.d(TAG, "item.headUrl:"+item.headUrl);
//	        if (CONF.DEBUG) Log.d(TAG, "item.coverBitmap:"+item.coverBitmap);
//
//    		//imageThumb.setImageResource(R.drawable.ic_movie_small);
//
//	        if (item.headUrl!=null&&!item.headUrl.equals("")) {
//	        	imageThumb.setUrl(item.headUrl);
//
//	        } else if (item.coverBitmap!=null) {
//	        	imageThumb.setImageBitmap(item.coverBitmap);
//
//    		} else {
//	        	//if (CONF.DEBUG) Log.d(TAG, "item.thumbImage:"+item.thumbImage);
//	        	if (item.thumbImage!=0) {
//	        		//imageThumb.setMaxHeight(80);
//	        		imageThumb.setImageResource(item.thumbImage);
//	        		//Log.d(TAG, "here");
//
//	        	} else {
//	        		imageThumb.setImageResource(R.drawable.video_icon);
//	        	}
//	        }
//
//	        if (!item.other.equals("")) {
//	        	//vTextView.setText(item.other);
//	        	oTextView.setText(item.other);
//	        }
//
//	        if (item.statImage!=0) {
//	        	//imageStat.setImageDrawable(item.statImage);
//	        	try {
//	        		imageStat.setImageResource(item.statImage);
//	        	} catch (OutOfMemoryError e) {
//	        		//System.gc();
//	                //Runtime.getRuntime().gc();
//	                /*try {
//	                	imageStat.setImageResource(item.statImage);
//	                } catch (Exception e2) {
//	                }*/
//	        	}
//	        	//Log.d(TAG, "here:"+item.statImage);
//
//	        } else {
//	        	imageStat.setImageResource(0);
//	        }
//    	} catch (ClassCastException e) {
//    		Log.e(TAG, "ClassCastException:"+e.getMessage());
//    	}
    }
}
