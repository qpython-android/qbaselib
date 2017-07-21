package com.quseit.lib;

/**
 * Created by sky on 2017/6/7.
 */

public class PluginInfo {
    private String src;
    private int ver;
    private String name;
    private String link;
    private String dst;
    private String title;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String desc;

    public PluginInfo(String src, int ver, String name, String link, String dst, String title, String desc) {
        this.src = src;
        this.ver = ver;
        this.name = name;
        this.link = link;
        this.dst = dst;
        this.title = title;
        this.desc = desc;
    }
}
