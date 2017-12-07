package com.quseit.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.quseit.android.R;
import com.quseit.cache.ACache;
import com.quseit.config.BASE_CONF;
import com.quseit.db.UserLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;

//import com.tapjoy.TapjoyConnect;

public class NAction {

	private static final String TAG = "NAction";
	// check rooted
	private final static int kSystemRootStateUnknow=-1;
	private final static int kSystemRootStateDisable=0;
	private final static int kSystemRootStateEnable=1;
	private static int systemRootState=kSystemRootStateUnknow;

	public static Notification getNotification(Context context, String contentTitle, String contentText, PendingIntent intent,
										int smallIconId, Bitmap largeIconId, int flags) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			Notification notification = new Notification.Builder(context) //new Notification(icon, tickerText, when);
					.setTicker(contentTitle)
					.setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSmallIcon(smallIconId)
					.setLargeIcon(largeIconId)
					.setAutoCancel(true)
					.setContentIntent(intent)
                    .build();

			return notification;
		} else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			Notification notification = new Notification.Builder(context) //new Notification(icon, tickerText, when);
					.setTicker(contentTitle)
					.setContentTitle(contentTitle)
					.setContentText(contentText)
					.setSmallIcon(smallIconId)
					.setSmallIcon(smallIconId)
					.setLargeIcon(largeIconId)
					.setAutoCancel(true)
					.setContentIntent(intent)
					.getNotification();
			return notification;
		} else {
			Notification notification = new Notification(smallIconId, contentTitle, System.currentTimeMillis());
			notification.tickerText = contentTitle;
			notification.contentIntent = intent;
			notification.flags |= flags;
			return null;
		}
	}

	@SuppressLint("NewApi")
	public static boolean isOpenGL2supported(Context context) {

		final ActivityManager activityManager =
			    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			final ConfigurationInfo configurationInfo =
			    activityManager.getDeviceConfigurationInfo();
			final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
			return supportsEs2;
	}

	public static String getMediaCenter(Context context) {
		return NStorage.getSP(context, "config.mediacenter");

	}

	public static void setMediCenter(Context context, String link) {
		NStorage.setSP(context, "config.mediacenter", link);

	}

	public static String getInstallLink(Context context) {
		return NStorage.getSP(context, "config.installlink");

	}

	public static void setInstallLink(Context context, String link) {
		NStorage.setSP(context, "config.installlink", link);
	}

	public static void setDefaultRoot(Context context, String value) {
		NStorage.setSP(context, "config.defaultroot", value);
	}

	public static String getDefaultRoot(Context context) {
		return NStorage.getSP(context, "config.defaultroot");
	}

	public static void sendEmail(Context context, String mailto, String title, String body) {
	    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        String[] strEmailReciver = new String[]{mailto};
		intent.putExtra(android.content.Intent.EXTRA_EMAIL, strEmailReciver); //设置收件人
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title); //设置主题

        intent.putExtra(android.content.Intent.EXTRA_TEXT, body); //设置内容
        context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.send_email)));
	}

	public static void setExtConf(Context context, String conf) {
		NStorage.setSP(context, "config.ext", conf);
	}

	public static String getExtConf(Context context) {
		return NStorage.getSP(context, "config.ext");
	}

	public static void setExtPluginsConf(Context context, String conf) {
		NStorage.setSP(context, "config.ext_plugins", conf);
	}

	public static String getExtPluginsConf(Context context) {
		return NStorage.getSP(context, "config.ext_plugins");
	}

	public static void setExtAdConf(Context context, String conf) {
		NStorage.setSP(context, "config.ext_ad", conf);
	}

	public static String getExtAdConf(Context context) {
		return NStorage.getSP(context, "config.ext_ad");
	}

	public static String getExtP(Context context, String key) {
		String conf = NAction.getExtConf(context);
		if (conf.equals("")) {
			return  "";
		} else {
			try {
				JSONObject a = new JSONObject(conf);
				return a.getString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				if (BASE_CONF.DEBUG) Log.d(TAG, "getExtP:"+key+"-not found");
				//e.printStackTrace();
				return "";
			}
		}
	}

	public static String getExtAdP(Context context, String key) {
		String conf = NAction.getExtAdConf(context);
		if (conf.equals("")) {
			return  "";
		} else {
			try {
				JSONObject a = new JSONObject(conf);
				return a.getString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				if (BASE_CONF.DEBUG) Log.d(TAG, "getExtAdP:"+key+"-not found");
				//e.printStackTrace();
				return "";
			}
		}
	}

	public static void setHtml5Index(Context context, String index) {
		NStorage.setSP(context, "service.html5index", index);
	}
	
	public static String getHtml5Index(Context context) {
		return NStorage.getSP(context, "service.html5index");
	}

	public static void setContentHost(Context context, String host) {
		NStorage.setSP(context, "service.contenthost", host);
	}

	public static String getContentHost(Context context) {
		return NStorage.getSP(context, "service.contenthost");
	}

	public static void setUpdateHost(Context context, String host) {
		NStorage.setSP(context, "service.updatehost", host);
	}

	public static String getUpdateHost(Context context) {
		String h = NStorage.getSP(context, "service.updatehost");

		return h;
	}
	
	public static boolean checkIfScriptExtend(Context context) {
		String extendEnable = NAction.getExtP(context, "script_extend");
		if (extendEnable.equals("1")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getPayIAPName(Context context, String iap) {
		String code = NAction.getCode(context);

        //String sign1 = NUtil.getSignString(context, context.getPackageName());
        //String sign2 = "";

        String pkgName2 = "com.greenrock."+code+"uad"+iap;
		String pkgNameConf = NAction.getExtP(context, "conf_no_ad_pkg");
		if (!pkgNameConf.equals("")) {
			pkgName2 = pkgNameConf;
		}
		return pkgName2;
	}

	public static boolean checkIfPayIAP(Context context, String iap) {
		if (NUtil.secGet(context, "iap_"+iap).equals("1")) {
			return true;

		} else {
			String code = NAction.getCode(context);

	        String sign1 = NUtil.getSignString(context, context.getPackageName());
	        String sign2 = "";

	        String pkgName2 = "com.greenrock."+code+"uad"+iap;
			String pkgNameConf = NAction.getExtP(context, "conf_no_ad_pkg");
			if (!pkgNameConf.equals("")) {
				pkgName2 = pkgNameConf;
			}
	        if (NUtil.checkAppInstalledByName(context, pkgName2)) {
	        	sign2 = NUtil.getSignString(context, pkgName2);
	        }
			return (sign1.equals(sign2));
		}
	}
	
	/*public static Intent openMarketLink(String pkgId) {
		Intent installIntent = new Intent("android.intent.action.VIEW");  
	    installIntent.setData(Uri.parse(pkgId));
	    return installIntent;
	}*/
	
	public static void setPluginIAPPayed(Context context, String iap) {
		NUtil.secSet(context, "iap_"+iap, "1");
	}

	public static long getRemouteSize(Context context, String downloadUrl, long startPos) {
		NAction.userProxy(context);

	     try {
	 		URL url = new URL(downloadUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		    httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.1; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		    //httpConnection.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		    //httpConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		     httpConnection.setRequestProperty("RANGE", "bytes=" + startPos + "-");

		     httpConnection.setConnectTimeout(30000);
		     httpConnection.setReadTimeout(30000);
		     httpConnection.connect();
		     long fileTotalSize = httpConnection.getContentLength();

		     if (httpConnection.getResponseCode() >= 400) {
			     httpConnection.disconnect();

				 return -1;
			 }
		     httpConnection.disconnect();

		     return fileTotalSize;

		} catch (IOException e) {
			if (BASE_CONF.DEBUG) Log.d(TAG, "getRemouteSize IOException:"+e.getMessage());
			e.printStackTrace();
		}
	     return -1;
	}
	
	public static void userProxy(Context context) {
		String proxyHost = NAction.getProxyHost(context);
		String proxyPort = NAction.getProxyPort(context);
		String proxyUsername = NAction.getProxyUsername(context);
		String proxyPwd = NAction.getProxyPwd(context);

		if (!proxyHost.equals("")) {
			Properties props = System.getProperties();
			props.put("http.proxyHost", proxyHost);
			props.put("http.proxyPort", proxyPort);
			if (!proxyUsername.equals("")) {
				props.put("http.proxyUsername", proxyUsername);
				props.put("http.proxyPassword", proxyPwd);

			}
		}
	}

	public static Intent getLinkAsIntent(Context context, String link) {
		//Log.d(TAG, "openRemoteLink:"+link);
		String vlowerFileName = link.toLowerCase();
		if (vlowerFileName.startsWith("lgmarket:")) {
			String[] xx = link.split(":");
			//Log.d(TAG, "lgmarket:"+xx[1]);

			Intent intent = new Intent("com.lge.lgworld.intent.action.VIEW");
			intent.setClassName("com.lge.lgworld", "com.lge.lgworld.LGReceiver");
			intent.putExtra("lgworld.receiver","LGSW_INVOKE_DETAIL");
			intent.putExtra("APP_PID", xx[1]);

			/*Intent intent = new Intent();
			intent.setClassName("com.lg.apps.cubeapp", "com.lg.apps.cubeapp.PreIntroActivity");
			intent.putExtra("type", "APP_DETAIL ");
			intent.putExtra("codeValue", ""); // value is not needed when moving to Detail page
			intent.putExtra("content_id", xx[1]);   */

			context.sendBroadcast(intent);

			return null;

		} else {
			Uri uLink = Uri.parse(link);

			Intent intent = new Intent( Intent.ACTION_VIEW, uLink );

			return intent;
		}
	}

	public static String getUserNoId(Context context) {
		String usernoid = NStorage.getSP(context, "user.usernoid");
		if (usernoid.equals("")) {
			// TODO
			//UUID uuid  =  UUID.randomUUID();
			usernoid = UUID.randomUUID().toString();
			NStorage.setSP(context, "user.usernoid", usernoid);
		}

		return usernoid;
	}

	public static void recordUseLog(Context context, String act, String content) {
		if (NAction.getExtP(context, "conf_log_user_enable").equals("1")) {
			UserLog pq = new UserLog(context);
			if (!pq.checkIfLogExists(act, content, "", "10", "")) {
				pq.insertNewLog(act, content, "", "10", "", 0);
			}
			//pq.close();
		}
	}

	public static void recordUserLog(Context context, String act, String content) {
		if (NAction.getExtP(context, "conf_log_user_enable").equals("1")) {
			UserLog pq = new UserLog(context);
			if (!pq.checkIfLogExists(act, content, "", "11", "")) {
				pq.insertNewLog(act, content, "", "11", "", 0);
			}
			//pq.close();
		}
	}

	public static void recordDataLog(Context context, String act, String content) {
		if (NAction.getExtP(context, "conf_log_data_enable").equals("1")) {
			UserLog pq = new UserLog(context);
			if (!pq.checkIfLogExists(act, content, "", "12", "")) {
				pq.insertNewLog(act, content, "", "12", "", 0);
			}
			//pq.close();
		}
	}

	public static void recordAdLog(Context context, String act, String key) {
		if (NAction.getExtP(context, "conf_log_ad_enable").equals("1")) {
			UserLog pq = new UserLog(context);
			if (!pq.checkIfLogExists(act, key, "", "13", "")) {

				pq.insertNewLog(act, key, "", "13", "", 0);
			}
			//pq.close();
		}
	}
	
	public static int getUpdateQ(Context context) {
		String seq = NStorage.getSP(context, "app.update_seq");
		if (BASE_CONF.DEBUG) Log.d(TAG, "getUpdateQ:"+seq);
		if (seq.equals("")) {
			return 0;
		} else {
			try {
				return Integer.parseInt(seq);
			} catch (Exception e) {
				return 3;
			}
		}
	}

	public static void setUpdateQ(Context context, String val) {
		NStorage.setSP(context, "app.update_seq", val);
	}

	public static String[] getAd(Context context) {
		String val1 = NStorage.getSP(context, "ad.who");
		String val2 = NStorage.getSP(context, "ad.banner");
		String val3 = NStorage.getSP(context, "ad.link");
		String val4 = NStorage.getSP(context, "ad.key");
		String val5 = NStorage.getSP(context, "ad.term");
		String val6 = NStorage.getSP(context, "ad.act");

		String[] ret = {val1, val2, val3, val4, val5, val6};

		if (BASE_CONF.DEBUG)  Log.d(TAG, "ad:["+val1+"]-banner:["+val2+"]-link:["+val3+"]"+"key["+val4+"]");
		return ret;
	}

	public static String[] getAppConf(Context context) {
		String val1 = NStorage.getSP(context, "app.about");
		String val2 = NStorage.getSP(context, "app.url");
		String val3 = NStorage.getSP(context, "app.feed");
		String val4 = NStorage.getSP(context, "app.feedurl");

		//String val5 = NStorage.getSP(context, "app.selfcheckurl");
		//String val6 = NStorage.getSP(context, "app.selfchecktitle");
		//String val7 = NStorage.getSP(context, "app.selfcheckurl");

		//Log.d(TAG, "getAppConf:about:"+val1+"-url:"+val2+"-feed:"+val3+"-feedurl:"+val4+"-selfcheck:"+val5+"-selfchecktitle:"+val6+"-selfcheckurl:"+val7);
		String[] ret = {val1, val2, val3, val4};
		return ret;
	}

	public static void setAppConf(Context context, String about, String url, String feed, String feedUrl) {
		//if (!about.equals("-") && !about.equals("")) {
			NStorage.setSP(context, "app.about", about);
		//}
		//if (!url.equals("-") && !url.equals("")) {
			NStorage.setSP(context, "app.url", url);
		//}
		//if(!feed.equals("-") && !feed.equals("")) {
			NStorage.setSP(context, "app.feed", feed);
		//}
		//if (!feedUrl.equals("-") && !feedUrl.equals("")) {
			NStorage.setSP(context, "app.feedurl", feedUrl);
		//}
		//NStorage.setSP(context, "app.selfcheck", check);
		//NStorage.setSP(context, "app.selfchecktitle", ctitle);
		//NStorage.setSP(context, "app.selfcheckurl", curl);

		//Log.d(TAG, "setAppConf:"+about+"-url:"+url+"-feed:"+feed+"-feedurl:"+feedUrl+"-selfcheck:"+check+"-selfchecktitle:"+ctitle+"-selfcheckurl:"+curl);

	}

	public static void setAd(Context context, String who, String banner, String link, String key, String term, String act) {
		NStorage.setSP(context, "ad.who", who);
		NStorage.setSP(context, "ad.banner", banner);
		NStorage.setSP(context, "ad.link", link);
		NStorage.setSP(context, "ad.key", key);
		NStorage.setSP(context, "ad.term", term);
		NStorage.setSP(context, "ad.act", act);


	}

	public static void setProxyHost(Context context, String val) {
		NStorage.setSP(context, "proxy.host", val);
	}

	public static void setProxyPort(Context context, String val) {
		NStorage.setSP(context, "proxy.port", val);
	}

	public static void setProxyUsername(Context context, String val) {
		NStorage.setSP(context, "proxy.username", val);
	}

	public static void setProxyPwd(Context context, String val) {
		NStorage.setSP(context, "proxy.pwd", val);
	}

	public static String getProxyHost(Context context) {
		String val = NStorage.getSP(context, "proxy.host");
		return val;
	}
	
	public static String getProxyPort(Context context) {
		String val = NStorage.getSP(context, "proxy.port");
		return val;
	}

	public static String getProxyUsername(Context context) {
		String val = NStorage.getSP(context, "proxy.username");
		return val;
	}

	public static String getProxyPwd(Context context) {
		String val = NStorage.getSP(context, "proxy.pwd");
		return val;
	}

	public static String getAcessOauthToken(Context context) {
		String token = NStorage.getSP(context, "oauth.access_token");
		return token;
	}

	public static String getAcessOauthVerify(Context context) {
		String token = NStorage.getSP(context, "oauth.access_token_verify");
		return token;
	}

	public static String getOauthToken(Context context) {
		String token = NStorage.getSP(context, "oauth.token");
		return token;
	}

	public static String getOauthTokenSecret(Context context) {
		String token = NStorage.getSP(context, "oauth.tokensecret");
		return token;
	}

	public static void setOauthAcessVerify(Context context, String token) {
		NStorage.setSP(context, "oauth.access_token_verify", token);
	}

	public static void setOauthAcessToken(Context context, String token) {
		NStorage.setSP(context, "oauth.access_token", token);
	}
	
	public static void setOauthToken(Context context, String token) {
		NStorage.setSP(context, "oauth.token", token);
	}

	public static void setOauthTokenSecret(Context context, String tokenSecret) {
		NStorage.setSP(context, "oauth.tokensecret", tokenSecret);
	}

	public static boolean ifNotLogin(Context context) {
		if (NAction.getUID(context).equals("") || NAction.getToken(context).equals("")) {
			return true;
		}
		return false;
	}

	public static void setLocationStreetAndRoad(Context context, String streetAndRoad) {
		NStorage.setSP(context, "position.street_road", streetAndRoad);
	}

	public static void setUHead(Context context, String head) {
		NStorage.setSP(context, "user.head", head);
	}
	
	public static String getUHead(Context context) {
		String head = NStorage.getSP(context, "user.head");
		return head;
	}
	
	public static void setLocationCity(Context context, String city) {
		NStorage.setSP(context, "position.city", city);
	}

	public static String getLoactionCity(Context context) {
		String city = NStorage.getSP(context, "position.city");
		return city;
	}

	public static String getLoactionStreetAndRoad(Context context) {
		String streetAndRoad = NStorage.getSP(context, "position.street_road");
		return streetAndRoad;
	}
	
	public static void setLocationUTag(Context context, String tag) {
		NStorage.setSP(context, "position.need_update", tag);
	}
	
	public static boolean ifLocationUTag(Context context) {
		String t = NStorage.getSP(context, "position.need_update");
		if (t.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static void setLocation(Context context, double latitude, double longitude) {
		String ola = NStorage.getSP(context, "position.latitude");
		String olo = NStorage.getSP(context, "position.longitude");
		if (BASE_CONF.DEBUG)  Log.d(TAG, "setLocation:"+latitude+"("+ola+")-"+longitude+"("+olo+")");

		NStorage.setSP(context, "position.latitude_old", String.valueOf(latitude));
		NStorage.setSP(context, "position.longitude_old", String.valueOf(longitude));

		NStorage.setSP(context, "position.latitude", String.valueOf(latitude));
		NStorage.setSP(context, "position.longitude", String.valueOf(longitude));
	}
	
	public static boolean ifLocationChange(Context context, String nla, String nlo) {
		String ola = NStorage.getSP(context, "position.latitude");
		String olo = NStorage.getSP(context, "position.longitude");


		if (ola.equals(nla) && olo.equals(nlo)) {
			return false;
		} else {
			return true;
		}
	}

	public static int getHiSrvProStat(Context context) {
		String xx = NStorage.getSP(context, "app_opt.srv_stat");
		if (xx.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(xx);
		}
	}

	public static void setHiSrvProStat(Context context, String val) {
		NStorage.setSP(context, "app_opt.srv_stat", val);
	}

	public static int getUpdateCheckTime(Context context) {
		String s = NStorage.getSP(context, "tmp.update_check_time");
		if (s.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(s);
		}
	}

	public static void setTotalEncounters(Context context, String val) {
		NStorage.setSP(context, "app_opt.total_encounters", val);
	}

	public static int getTotalEncounters(Context context) {
		String xx = NStorage.getSP(context, "app_opt.total_encounters");
		if (xx.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(xx);
		}
	}

	public static void setUpdateCheckTime(Context context) {
        NStorage.setSP(context, "tmp.update_check_time", String.valueOf(VeDate.getStringDateHourAsInt()));
	}

	public static int getUpdateConfCheckTime(Context context){
		String s = NStorage.getSP(context, "tmp.update_conf_check_time");
		if (s.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(s);
		}
	}

	public static void setUpdateConfCheckTime(Context context){
		NStorage.setSP(context,"tmp.update_conf_check_time",String.valueOf(VeDate.getStringDateHourAsInt()));
	}

	public static boolean welcomeRead(Context context) {
		String xx = NStorage.getSP(context, "app_opt.welcome_read");
		if (!xx.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static void setWelcomeReadStat(Context context) {
		NStorage.setSP(context, "app_opt.welcome_read",	"1");
	}

	public static int getHiSrvPrivacy(Context context) {
		String xx = NStorage.getSP(context, "hi_opt.hiprivacy");
		if (xx.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(xx);
		}
	}

	public static void setHiSrvStartStat(Context context, String val) {
		NStorage.setSP(context, "hi_opt.hisrv_start", val);
	}

	public static int getHiSrvStartStat(Context context) {
		String xx = NStorage.getSP(context, "hi_opt.hisrv_start");
		if (xx.equals("")) {
			return 0;
		} else {
			int yy =  Integer.parseInt(xx);

			if (yy == 1) {
				NStorage.setSP(context, "hi_opt.hisrv_start", "");
			}
			return yy;
		}
	}

	public static void setHiSrvPrivacy(Context context, String stat) {
		NStorage.setSP(context, "hi_opt.hiprivacy", stat);
	}

	public static void setHiFoundSrvStat(Context context, String stat) {
		if (BASE_CONF.DEBUG)  Log.d(TAG, "setHiFoundSrvStat:"+stat);
		NStorage.setSP(context, "hi_opt.hifoundsrv", stat);
	}

	public static int getHiFoundSrvStat(Context context) {
		String xx = NStorage.getSP(context, "hi_opt.hifoundsrv");
		if (xx.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(xx);
		}
	}
	
	public static void setHiSrvStat(Context context, String Stat) {
		NStorage.setSP(context, "hi_opt.hisrv", Stat);
	}

	/*
	 */
	public static int getHiSrvStat(Context context) {
		String xx = NStorage.getSP(context, "hi_opt.hisrv");

		if (xx.equals("") || xx.equals("0")) {
			return 0;
		} else {
			return Integer.parseInt(xx);
		}
	}
	
	// 0: 说声Hi, 1: 发送我的语音介绍 2: 什么也不做
	public static int getHiact2(Context context) {
		String act =  NStorage.getSP(context, "hi_opt.hiact2");
		if (act.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(act);
		}
	}

	public static void setHiact2(Context context, String val) {
		NStorage.setSP(context, "hi_opt.hiact2", val);
	}

	/*
	 * 0 铃声 1 振动 2 无声
	 */
	public static int getHiact(Context context) {
		String act =  NStorage.getSP(context, "hi_opt.hiact");
		if (act.equals("")) {
			return 0;
		} else {
			return Integer.parseInt(act);
		}
	}

	public static void setHiact(Context context, String val) {
		NStorage.setSP(context, "hi_opt.hiact", val);
	}
	
	public static void setReloadFlag(Context context) {
    	NStorage.setSP(context, "tmp.reload_mp", "1");	// reload flag
	}
	
	public static void setReloadFeedFlag(Context context) {
    	NStorage.setSP(context, "tmp.reload_feed_mp", "1");	// reload flag
	}

	public static boolean getReloadFlag(Context context) {
		String flag = NStorage.getSP(context, "tmp.reload_mp");
    	NStorage.setSP(context, "tmp.reload_mp", "");	// reload flag
		if (flag.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static void setSearchTerm(Context context, String term) {
		NStorage.setSP(context, "search_opt.searchterm", term);
	}

	public static String getSearchTerm(Context context) {
		String term = NStorage.getSP(context, "search_opt.searchterm");
		if (!term.equals("")) {
			NStorage.setSP(context, "search_opt.searchterm", "");
			return term;
		}
		return "";

	}

	public static boolean getReloadFeedFlag(Context context) {
		String flag = NStorage.getSP(context, "tmp.reload_feed_mp");
    	NStorage.setSP(context, "tmp.reload_feed_mp", "");	// reload flag
		if (flag.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static void setUName(Context context, String val) {
		NStorage.setSP(context, "user.name", val);
	}

	public static String getUName(Context context) {
		return NStorage.getSP(context, "user.name");
	}

	public static String getUserName(Context context) {
		return NStorage.getSP(context, "user.username");
	}

	public static void setUID(Context context, String uid) {
		NStorage.setSP(context, "user.uid", uid);
	}

	public static String getUID(Context context) {
		return NStorage.getSP(context, "user.uid");
	}

	public static String getToken(Context context) {
		return NStorage.getSP(context, "user.token");
	}
	
	public static String getLatitude(Context context) {
		return NStorage.getSP(context, "position.latitude");
	}

	public static String getLongitude(Context context) {
		return NStorage.getSP(context, "position.longitude");
	}

	public static void viewInfo(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static void focusAct(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static void blockAct(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static void chatAct(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static void logoutAct(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static void searchAct(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static void attentionAct(Context context) {
    	Toast.makeText(context, context.getString(R.string.not_implement), Toast.LENGTH_SHORT).show();
	}

	public static String getCode(Context context) {
		String packageName = context.getPackageName();
		String[] xcode = packageName.split("\\.");
		String code = xcode[xcode.length-1];
		return code;
	}

	public static String getUserUrl(Context context) {
		String sdk = "0";
		try {
			sdk = Build.VERSION.SDK;
		} catch (Exception e) {

		}
		return "uid="+NAction.getUID(context)+"&token="+NAction.getToken(context)+"&userno="+NAction.getUserNoId(context)+"&lang="+NUtil.getLang()+
				"&ver="+NUtil.getVersinoCode(context)+"&code="+NAction.getCode(context)+"&sdk="+sdk+"&appid="+context.getPackageName();
	}

	// ftp
	public static void setFtpRoot(Context context, String root) {
    	NStorage.setSP(context, "ftp.root", root);
	}

	public static String getFtpRoot(Context context) {
    	return NStorage.getSP(context, "ftp.root");
	}

	public static void setFtpUsername(Context context, String username) {
    	NStorage.setSP(context, "ftp.username", username);
	}

	public static void setFtpPwd(Context context, String pwd) {
    	NStorage.setSP(context, "ftp.pwd", pwd);
	}

	public static String getFtpUsername(Context context) {
    	return NStorage.getSP(context, "ftp.username");
	}

	public static String getFtpPwd(Context context) {
    	return NStorage.getSP(context, "ftp.pwd");
	}

	public static void setFtpPort(Context context, String port) {
    	NStorage.setSP(context, "ftp.port", port);
	}

	public static String getFtpPort(Context context) {
    	return NStorage.getSP(context, "ftp.port");
	}

	// licence check
	public static String getSamLicence(Context context) {
		return NStorage.getSP(context, "sam.lic");
	}

	public static void setSamLicence(Context context, String lic) {
		NStorage.setSP(context, "sam.lic", lic);
	}
	
	public static boolean isQPy3(Context context) {
		String code = NAction.getCode(context);
		if (code.contains("qpy3")) {
			return true;
		}

		if (NAction.getQPyInterpreter(context).equals("3.x")) {
			return true;
		} else {
			return false;
		}
	}

	public static String getQPyInterpreter(Context context) {
		String qpyInterVal = NStorage.getSP(context, "conf.default_qpy_interpreter");
        if (!qpyInterVal.equals("3.x")) {
        	qpyInterVal = "2.x";
        }

        return qpyInterVal;
	}

	public static void setQPyInterpreter(Context context, String qpyInterVal) {
		 NStorage.setSP(context, "conf.default_qpy_interpreter", qpyInterVal);
		// It shouldn't be here and need to be refactor
		ACache.get(context).clear();
	}
	
	public static void setPluginsEnable(Context context, String noad) {
		NStorage.setSP(context, "plugin.noad", noad);
	}
    
	public static boolean checkPluginNoAdEnable(Context context) {
		String noad = NStorage.getSP(context, "plugin.noad");
		if (noad.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean httpPing(String url, int timeout) {
		//Log.d(TAG, "httpPing:"+url+"-"+timeout);
	    url = url.replaceFirst("https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        //Log.d(TAG, "responseCode:"+responseCode);
	        return (responseCode>0);
	        //return (200 <= responseCode && responseCode <= 399);
	    } catch (IOException exception) {
	        Log.d(TAG, "exception:"+exception.getLocalizedMessage());

	        return false;
	    }
	}

    static public boolean portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // thread utils
    static public void setThreadStat(Context context, int threadid, int stat) {
    	Log.d(TAG, "setThreadStat:"+threadid+"-"+stat);
    	NStorage.setIntSP(context, "thread_stat_"+threadid,stat);
    }

  	static public boolean isThreadsStop(Context context) {
  		boolean st = true;
  		for (int i = 1; i<= BASE_CONF.THREA_STAT.length; i++) {
  			int j = NStorage.getIntSP(context, "thread_stat_"+i);
  			Log.d(TAG, "isThreadsStop i:"+i+"-j:"+j);
  			if (j == 1) {
  				st = false;
  			}
  		}
  		Log.d(TAG, "isThreadsStop:"+st);
  		return st;
  	}

  	static public void clearThreadsStat(Context context) {
  		for (int i = 1; i<= BASE_CONF.THREA_STAT.length; i++) {
  			NStorage.setIntSP(context, "thread_stat_"+i,0);
  		}
  	}

	public static boolean isRootEnable(Context context) {
		boolean enabledRoot = NStorage.getSP(context, "app.root").equals("1");
		return isRootSystem() && enabledRoot;
	}

	public static boolean isRootSystem() {
		if(systemRootState==kSystemRootStateEnable) {
			return true;
		} else if(systemRootState==kSystemRootStateDisable) {

			return false;
		}
		File f=null;
		final String kSuSearchPaths[]={"/su/bin/", "/system/bin/","/system/xbin/","/system/sbin/","/sbin/","/vendor/bin/"};
		try {
			for(int i=0;i<kSuSearchPaths.length;i++)
			{
				f=new File(kSuSearchPaths[i]+"su");
				if(f!=null&&f.exists())
				{
					systemRootState=kSystemRootStateEnable;
					return true;
				}
			}
		}catch(Exception e) {
		}
		systemRootState=kSystemRootStateDisable;
		return false;
	}

	public int getQualityDrawable(String size) {
		try {
			String ns = size.toLowerCase().replace("m", "");
			Float nsx = Float.parseFloat(ns);
			if (nsx>=5) {
				return R.drawable.ic_quaryty_x5;
			} else if (nsx>=4) {
				return R.drawable.ic_quaryty_x4;
			} else if (nsx>=3) {
				return R.drawable.ic_quaryty_x3;
			} else if (nsx>=2) {
				return R.drawable.ic_quaryty_x2;
			} else {
				return R.drawable.ic_quaryty_x1;
			}

		} catch (Exception e) {
			return 0;
		}
	}
	
}
