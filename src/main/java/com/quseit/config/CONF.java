package com.quseit.config;

public class CONF {
    // 插件版本
    public static int plugin_ver=0;

    public static boolean open=false;


	public static final String BA_SITE2 = "apu2.quseit.com";
    public static final int TRY_COUNT = 3;
    public static final long TRY_DELAY = 1000;

    public final static String SMAATOBKEY = "1100010539";

	public final static int ROUND_PIX = 0;	// IMAGE ROUND PIX
	public final static int DOWNLOAD_NOTIFY_INDEX = 10001;

	public final static String MVEXT = "#3gp#amv#ape#asf#avi#flac#flv#hlv#mkv#mov#mp4#mpeg#mpg#rm#rmvb#tta#wma#wmv#webm#fv4#mts#";
	public final static String MUEXT = "#mp3#mid#wma#wav#midi#ogg#";

	public final static String COLLECT_INFO = "#fingerprint#model#brand#";

	public final static int UPDATEQ = 6;

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

	public static final boolean DEBUG = false;

	public static final String FEEDBACK_EMAIL = "support@qpython.org";

    public final static String DEFAULT_ROOT = "qpython";

    public static final String EXT_PLG = "org.qpython.qpy";
    public static final String EXT_PLG3 = "org.qpython.qpy3";


	public static int[] THREA_STAT = {0,0,0};	//FIXME DOWNLOAD STATUS

    //版本更新
    public static final String VERSION_KEY="ver";
    public static final String DOWNLOAD_LINK_KEY="link";
    public static final String VERIOSN_DESC_KEY="ver_desc";
    public static final String APP_KEY="app";
    public static final String VERSION_NAME_KEY="ver_name";
    public static final String VERSION_PLUGIN_KEY="ver_plugin";
    public static final String PLUGIN_KEY="plugins";

     //speed
    public static double TIME_SPAN = 2000d;
    public static long CHANGE_DELAY = 300;
    public static int SMALL_WINDOW_TYPE = 0;
    public static int BIG_WINDOW_TYPE = 1;
    public static String SP_BOOT = "SP_BOOT";
    public static String SP_BG = "SP_BG";
    public static String SP_LOC = "SP_LOC";
    public static String SP_X = "SP_X";
    public static String SP_Y = "SP_Y";
    public static String SP_STATUSBAR_HEIGHT = "SP_STATUSBAR_HEIGHT";


    public final static String BASE_PATH = "qpython";
    public final static String CACHE_DIR = "cache";
    public final static String TMP_DIR = "tmp";
    public final static String LIB_DIR = "lib";

}
