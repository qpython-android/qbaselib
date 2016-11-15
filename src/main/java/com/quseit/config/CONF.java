package com.quseit.config;

public class CONF {
	public static final String BA_SITE2 = "conf.quseit.com";
	public static final String FR_SITE = "play.quseit.com";
    public static final int TRY_COUNT = 3;
    public static final long TRY_DELAY = 1000;
    
	public final static int ROUND_PIX = 0;	// IMAGE ROUND PIX
	public final static int DOWNLOAD_NOTIFY_INDEX = 10001;

	public final static String MVEXT = "#3gp#amv#ape#asf#avi#flac#flv#hlv#mkv#mov#mp4#mpeg#mpg#rm#rmvb#tta#wma#wmv#webm#fv4#mts#";
	public final static String MUEXT = "#mp3#mid#wma#wav#midi#ogg#";

	public final static String COLLECT_INFO = "#fingerprint#model#brand#";

	public final static int UPDATEQ = 6;
	public final static int UPDATE_VER = 2;	// update backend

	public final static int CACHE_TYPE_COVER = 20;

	public final static long EXPIRED_GET2 = 3600000;	//get2 更新时间
	public final static String DCACHE = "cache";
	
	public final static String LOG_LIMIT = "30";

    public static final String EXTRA_CONTENT_URL0 = "com.quseit.common.extra.CONTENT_URL0";
    public static final String EXTRA_CONTENT_URL1 = "com.quseit.common.extra.CONTENT_URL1";
    public static final String EXTRA_CONTENT_URL2 = "com.quseit.common.extra.CONTENT_URL2";
    public static final String EXTRA_CONTENT_URL3 = "com.quseit.common.extra.CONTENT_URL3";
    public static final String EXTRA_CONTENT_URL4 = "com.quseit.common.extra.CONTENT_URL4";
    public static final String EXTRA_CONTENT_URL5 = "com.quseit.common.extra.CONTENT_URL5";
    public static final String EXTRA_CONTENT_URL6 = "com.quseit.common.extra.CONTENT_URL6";
    public static final String EXTRA_CONTENT_URL7 = "com.quseit.common.extra.CONTENT_URL7";
    public static final String EXTRA_CONTENT_URL8 = "com.quseit.common.extra.CONTENT_URL8";
    public static final String EXTRA_CONTENT_URL9 = "com.quseit.common.extra.CONTENT_URL9";
    public static final String EXTRA_CONTENT_URL10 = "com.quseit.common.extra.CONTENT_URL10";
    public static final String EXTRA_CONTENT_URL11 = "com.quseit.common.extra.CONTENT_URL11";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.19 (KHTML, like Gecko) Chrome/1.0.154.36 Safari/525.19";

	public static final int PAGE_NUM = 10;

	public static final boolean DEBUG = false;
	
	public static final String FEEDBACK_EMAIL = "quseitlab@gmail.com";

    public final static String DEFAULT_ROOT = "Quseit";
    
    public static final String EXT_PLG = "org.qpython.qpy";
    public static final String EXT_PLG_3 = "org.qpython.qpy3";
    
    public static final String EXT_PLG_URL = "http://"+FR_SITE+"/qpython.html";
    public static final String EXT_PLG_URL3 = "http://"+FR_SITE+"/qpython3.html";


	public static int[] THREA_STAT = {0,0,0};	//FIXME DOWNLOAD STATUS

}
