package com.quseit.view.item;

    public class HeadedTextItem {

    public String headerText;
    public String desc;
    public String headUrl;
    public String other;
    public int statImage;
    
    public HeadedTextItem(String text, String desc) {
        this.desc = desc;
        this.headUrl = "";
        this.other = "";
        this.statImage = 0;
    }
    
    public HeadedTextItem(String text, String desc, String headUrl, String other, int stat) {
        this.desc = desc;
        this.headUrl = headUrl;
        this.other = other;
        this.statImage = stat;
    }

}
