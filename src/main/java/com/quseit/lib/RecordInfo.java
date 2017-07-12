package com.quseit.lib;

/**
 * Created by sky on 2017/7/6.
 */

public class RecordInfo {
    private int threadId;
    private long complime;





    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public long getComplime() {
        return complime;
    }

    public void setComplime(long complime) {
        this.complime = complime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public RecordInfo(int threadId,  long complime,  String url) {
        this.threadId = threadId;
        this.complime = complime;
        this.url = url;
    }

    private String url;
}
