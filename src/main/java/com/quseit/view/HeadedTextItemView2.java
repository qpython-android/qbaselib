package com.quseit.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quseit.android.R;
import com.quseit.item.HeadedTextItem2;
import com.quseit.widget.AsyncImageView;

import greendroid.widget.item.Item;
import greendroid.widget.itemview.ItemView;


public class HeadedTextItemView2 extends LinearLayout implements ItemView {
	private static final String TAG = "HeadedTextItemView";

    private TextView mHeaderView;
    private TextView mTextView;
    private TextView dTextView;
    private TextView sTextView;
    
    private TextView oTextView;
    private AsyncImageView imageThumb;
    private ImageView  imageStat;

    public HeadedTextItemView2(Context context) {
        this(context, null);
    }

    public HeadedTextItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void prepareItemView() {
        mHeaderView = (TextView) findViewById(R.id.gd_separator_text);
        mTextView = (TextView) findViewById(R.id.gd_text);
        dTextView = (TextView) findViewById(R.id.gd_text_desc);
        imageThumb = (AsyncImageView) findViewById(R.id.item_thumbnail);
        sTextView = (TextView) findViewById(R.id.item_seq);

        oTextView = (TextView) findViewById(R.id.gd_text_other);
        imageStat = (ImageView) findViewById(R.id.gd_stat_image);
    }

    public void setObject(Item object) {
    	try {
	        final HeadedTextItem2 item = (HeadedTextItem2) object;
	        final String headerText = item.headerText;
	
	        if (!TextUtils.isEmpty(headerText)) {
	            mHeaderView.setText(headerText);
	            mHeaderView.setVisibility(View.VISIBLE);
	        } else {
	            mHeaderView.setVisibility(View.GONE);
	        }
	
	        mTextView.setText(item.text);
	        dTextView.setText(item.desc);
	        
	        if (!item.headUrl.equals("")) {
	        	imageThumb.setUrl(item.headUrl);
	        	imageThumb.setVisibility(View.VISIBLE);
	        }
	        
	        if (item.seq!=0) {
	        	sTextView.setText(String.valueOf(item.seq));
	        }
	        if (!item.other.equals("")) {
	        	oTextView.setText(item.other);
	        }
	        
	        if (item.statImage!=0) {
	        	//imageStat.setImageDrawable(item.statImage);
	        	imageStat.setImageResource(item.statImage);
	        }
    	} catch (ClassCastException e) {
    		Log.e(TAG, "ClassCastException:"+e.getMessage());
    	}

    }

}
