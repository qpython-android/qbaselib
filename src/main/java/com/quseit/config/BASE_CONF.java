package com.quseit.config;

public interface BASE_CONF {
    boolean DEBUG = false;
    String UPDATER_URL="http://dl.qpy.io/update.json";
	String BA_SITE2 = DEBUG?"apu.quseit.com:10001":"apu.quseit.com";
	String FR_SITE = "play.quseit.com";
    int TRY_COUNT = 3;
    long TRY_DELAY = 1000;
	int ROUND_PIX = 0;	// IMAGE ROUND PIX
	int DOWNLOAD_NOTIFY_INDEX = 10001;
	String MVEXT = "#3gp#amv#ape#asf#avi#flac#flv#hlv#mkv#mov#mp4#mpeg#mpg#rm#rmvb#tta#wma#wmv#webm#fv4#mts#";
	String MUEXT = "#mp3#mid#wma#wav#midi#ogg#";
	String COLLECT_INFO = "#fingerprint#model#brand#";
	int UPDATEQ = 6;
	int UPDATE_VER = 2;	// update backend
	int CACHE_TYPE_COVER = 20;
	long EXPIRED_GET2 = 3600000;	//get2 更新时间
	String DCACHE = "cache";
	String LOG_LIMIT = "30";
    String EXTRA_CONTENT_URL0 = "com.quseit.common.extra.CONTENT_URL0";
    String EXTRA_CONTENT_URL1 = "com.quseit.common.extra.CONTENT_URL1";
    String EXTRA_CONTENT_URL2 = "com.quseit.common.extra.CONTENT_URL2";
    String EXTRA_CONTENT_URL3 = "com.quseit.common.extra.CONTENT_URL3";
    String EXTRA_CONTENT_URL4 = "com.quseit.common.extra.CONTENT_URL4";
    String EXTRA_CONTENT_URL5 = "com.quseit.common.extra.CONTENT_URL5";
    String EXTRA_CONTENT_URL6 = "com.quseit.common.extra.CONTENT_URL6";
    String EXTRA_CONTENT_URL7 = "com.quseit.common.extra.CONTENT_URL7";
    String EXTRA_CONTENT_URL8 = "com.quseit.common.extra.CONTENT_URL8";
    String EXTRA_CONTENT_URL9 = "com.quseit.common.extra.CONTENT_URL9";
    String EXTRA_CONTENT_URL10 = "com.quseit.common.extra.CONTENT_URL10";
    String EXTRA_CONTENT_URL11 = "com.quseit.common.extra.CONTENT_URL11";
    String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.19 (KHTML, like Gecko) Chrome/1.0.154.36 Safari/525.19";
	int PAGE_NUM = 10;
	String FEEDBACK_EMAIL = "support@quseit.com";
    String DEFAULT_ROOT = "qpython";
    String EXT_PLG = "com.hipipal.qpyplus";
    String EXT_PLG_3 = "org.qpython.qpy3";
    String EXT_PLG_URL = "http://"+FR_SITE+"/qpython.html";
    String EXT_PLG_URL3 = "http://"+FR_SITE+"/qpython3.html";
    //版本更新
    String VERSION_KEY="ver";
    String DOWNLOAD_LINK_KEY="link";
    String VERIOSN_DESC_KEY="ver_desc";
    String APP_KEY="app";
    String VERSION_NAME_KEY="ver_name";
    String VERSION_PLUGIN_KEY="ver_plugin";
    String PLUGIN_KEY="plugins";
    // 插件版本
    int plugin_ver=0;
	int[] THREA_STAT = {0,0,0};	//FIXME DOWNLOAD STATUS

     //speed
     double TIME_SPAN = 2000d;
    long CHANGE_DELAY = 300;
    int SMALL_WINDOW_TYPE = 0;
    int BIG_WINDOW_TYPE = 1;
    int OVERLAY_PERMISSION_REQ_CODE = 11;
    String SP_BOOT = "SP_BOOT";
    String SP_BG = "SP_BG";
    String SP_LOC = "SP_LOC";
    String SP_X = "SP_X";
    String SP_Y = "SP_Y";
    String SP_STATUSBAR_HEIGHT = "SP_STATUSBAR_HEIGHT";



}
