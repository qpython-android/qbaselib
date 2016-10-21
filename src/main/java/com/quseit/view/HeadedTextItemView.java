/*
 * Copyright (C) 2010 Cyril Mottier (http://www.cyrilmottier.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.quseit.view;

import com.quseit.android.R;
import com.quseit.item.HeadedTextItem;

import greendroid.widget.AsyncImageView;
import greendroid.widget.item.Item;
import greendroid.widget.itemview.ItemView;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class HeadedTextItemView extends LinearLayout implements ItemView {
	private static final String TAG = "HeadedTextItemView";

    private TextView mHeaderView;
    private TextView mTextView;
    private TextView dTextView;
    
    private TextView oTextView;
    private AsyncImageView imageThumb;
    private ImageView  imageStat;

    public HeadedTextItemView(Context context) {
        this(context, null);
    }

    public HeadedTextItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void prepareItemView() {
        mHeaderView = (TextView) findViewById(R.id.gd_separator_text);
        mTextView = (TextView) findViewById(R.id.gd_text);
        dTextView = (TextView) findViewById(R.id.gd_text_desc);
        imageThumb = (AsyncImageView) findViewById(R.id.item_thumbnail);
        oTextView = (TextView) findViewById(R.id.gd_text_other);
        imageStat = (ImageView) findViewById(R.id.gd_stat_image);
    }

    public void setObject(Item object) {
    	try {
	        final HeadedTextItem item = (HeadedTextItem) object;
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
