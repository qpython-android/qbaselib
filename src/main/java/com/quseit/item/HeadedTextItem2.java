package com.quseit.item;

import com.quseit.android.R;

import greendroid.widget.item.TextItem;
import greendroid.widget.itemview.ItemView;
import android.content.Context;
import android.view.ViewGroup;


public class HeadedTextItem2 extends TextItem {

    public String headerText;
    public String desc;
    public String headUrl;
    public int seq;

    public String other;
    public int statImage;
    
    public HeadedTextItem2(String text, String desc) {
        super(text);
        this.desc = desc;
        this.headUrl = "";
        this.other = "";
        this.statImage = 0;
    }
    
    public HeadedTextItem2(String text, String desc, String headUrl, String other, int stat, int seq) {
        super(text);
        this.desc = desc;
        this.headUrl = headUrl;
        this.other = other;
        this.statImage = stat;
        this.seq = seq;
    }

    @Override
    public ItemView newView(Context context, ViewGroup parent) {
        return createCellFromXml(context, R.layout.q_headed_text_item2_view, parent);
    }
}
