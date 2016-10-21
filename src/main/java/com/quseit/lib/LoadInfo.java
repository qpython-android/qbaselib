package com.quseit.lib;
 /**
  *自定义的一个记载下载器详细信息的类 
  */
 public class LoadInfo {
     public long fileSize;//文件大小
     private long complete;//完成度
     private String urlstring;//下载器标识
     public LoadInfo(long fileSize, int complete, String urlstring) {
         this.fileSize = fileSize;
         this.complete = complete;
         this.urlstring = urlstring;
     }
     public LoadInfo() {
     }
     public long getFileSize() {
         return fileSize;
     }
     public void setFileSize(int fileSize) {
         this.fileSize = fileSize;
     }
     public long getComplete() {
         return complete;
     }
     public void setComplete(int complete) {
         this.complete = complete;
     }
     public String getUrlstring() {
         return urlstring;
     }
     public void setUrlstring(String urlstring) {
         this.urlstring = urlstring;
     }
     @Override
     public String toString() {
         return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete
                 + ", urlstring=" + urlstring + "]";
     }
 }
