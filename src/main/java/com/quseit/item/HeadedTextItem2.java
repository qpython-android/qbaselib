package com.quseit.item;

import com.quseit.android.R;
import android.content.Context;
import android.view.ViewGroup;


public class HeadedTextItem2  {

    public String headerText;
    public String desc;
    public String headUrl;
    public int seq;

    public String other;
    public int statImage;
    
    public HeadedTextItem2(String text, String desc) {
        this.desc = desc;
        this.headUrl = "";
        this.other = "";
        this.statImage = 0;
    }
    
    public HeadedTextItem2(String text, String desc, String headUrl, String other, int stat, int seq) {
        this.desc = desc;
        this.headUrl = headUrl;
        this.other = other;
        this.statImage = stat;
        this.seq = seq;
    }
}
