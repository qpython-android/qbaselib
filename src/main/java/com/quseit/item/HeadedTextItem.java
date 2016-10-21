package com.quseit.item;

import com.quseit.android.R;

import greendroid.widget.item.TextItem;
import greendroid.widget.itemview.ItemView;
import android.content.Context;
import android.view.ViewGroup;


public class HeadedTextItem extends TextItem {

    public String headerText;
    public String desc;
    public String headUrl;
    public String other;
    public int statImage;
    
    public HeadedTextItem(String text, String desc) {
        super(text);
        this.desc = desc;
        this.headUrl = "";
        this.other = "";
        this.statImage = 0;
    }
    
    public HeadedTextItem(String text, String desc, String headUrl, String other, int stat) {
        super(text);
        this.desc = desc;
        this.headUrl = headUrl;
        this.other = other;
        this.statImage = stat;
    }

    @Override
    public ItemView newView(Context context, ViewGroup parent) {
        return createCellFromXml(context, R.layout.q_headed_text_item_view, parent);
    }
}
