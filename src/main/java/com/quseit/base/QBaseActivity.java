package com.quseit.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager.BadTokenException;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.quseit.android.R;
import com.quseit.common.AssetExtract;
import com.quseit.config.BASE_CONF;
import com.quseit.common.db.AppLog;
import com.quseit.common.db.CacheLog;
import com.quseit.common.db.UserLog;
import com.quseit.service.ResourceManager;
import com.quseit.util.DateTimeHelper;
import com.quseit.util.FileHelper;
import com.quseit.util.FileUtils;
import com.quseit.util.NAction;
import com.quseit.util.NStorage;
import com.quseit.util.NUtil;
import com.quseit.util.Utils;
import com.quseit.util.VeDate;
import com.quseit.view.AdSlidShowView;
import com.quseit.view.AdSlidShowView.urlBackcall;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


//import greendroid.widget.QuickActionWidget;

//public abstract class QBaseActivity extends GDActivity  implements TapjoyFeaturedAppNotifier, TapjoyDisplayAdNotifier, TapjoyVideoNotifier {

public abstract class QBaseActivity extends Activity {
	protected static final String TAG = "QBaseActivity";

	private static int NOTIFICATION_ID = 0x20001;// 通知栏消息id

	// private AdLayout amazonAdView; // The ad view used to load and display
	// the ad.

	protected int limit = BASE_CONF.PAGE_NUM;

	protected int start = 0;

	protected int total = 0;

	protected boolean myload = true;

	protected ProgressDialog waitingWindow;

	protected QBaseDialog WBase;

	protected int dialogIndex;

	// private AdView adMob = null;

	// private QuickActionWidget mBar;
	public WebView wv;

	public ProgressBar wvProgressBar;

	private ProgressDialog pDialog;

	// Banner Ads.
//	boolean update_display_ad = false;
//	protected String tapjoyErr = "";
//
//	protected View adView;

	// MobclixMMABannerXLAdView mobClix = null;
	// MobFoxView mobFox = null;

	public void progress(String title, String msg, int x) {
		pDialog = ProgressDialog.show(this, title, msg, true, false);
		// progressHandler.sendEmptyMessage(0);
		if (x != 0) {
			pDialog.setContentView(x);
		}
	}

	public void xprogress() {
		progressHandler.sendEmptyMessage(0);
	}

	@SuppressLint("HandlerLeak")
	public Handler progressHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				pDialog.dismiss();
			}
			catch (IllegalArgumentException e) {

			}
			// handle the result here
		}
	};

	/*
	 * protected ItemAdapter adapter; protected GestureDetector mGestureDetector; protected static final int
	 * FLING_MIN_DISTANCE = 50; protected static final int FLING_MIN_VELOCITY = 0;
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WBase = new QBaseDialog(this, this);
		dialogIndex = 1;
	}

	@Override
	protected void onPause() {
		/*
		 * if (mobFox!=null) { mobFox.pause(); }
		 */
		/*
		 * if (mobClix!=null) { mobClix.pause(); }
		 */

		super.onPause();
	}

	@Override
	protected void onResume() {
		/*
		 * if (mobFox!=null) { mobFox.resume(); }
		 */

		/*
		 * if (mobClix!=null) { mobClix.resume(); }
		 */

		checkAD(TAG);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// modBanner = (LinearLayout)findViewById(R.id.modbanner);
		LinearLayout modBanner = (LinearLayout) findViewById(R.id.modbanner);

		/*
		 * if (adMob!=null) { adMob.destroy(); }
		 */

		/*
		 * if (mobClix!=null) { mobClix.pause(); mobClix.destroy(); if (modBanner!=null) {
		 * modBanner.removeView(mobClix); } }
		 */

		/*
		 * if (mobFox!=null) { mobFox.pause(); modBanner.removeView(mobFox); }
		 */

		if (modBanner != null) {
			modBanner.removeAllViews();
		}
		super.onDestroy();
	}

	protected void checkAD(String pageId) {
		LinearLayout modBanner = (LinearLayout) findViewById(R.id.modbanner);

		if (NAction.checkIfPayIAP(getApplicationContext(), "ad")) {

			/*
			 * if (adMob!=null) { adMob.destroy(); modBanner.removeView(adMob); adMob = null; }
			 */

			/*
			 * if (mobClix!=null) { mobClix.pause(); mobClix.destroy(); modBanner.removeView(mobClix); mobClix = null; }
			 */

			/*
			 * MobclixMMABannerXLAdView mobClixadview = (MobclixMMABannerXLAdView)
			 * findViewById(R.id.advertising_banner_view); if (mobClixadview!=null) {
			 * mobClixadview.setVisibility(View.GONE); }
			 */
			/*
			 * if (mobFox!=null) { mobFox.pause(); modBanner.removeView(mobFox); mobFox = null; }
			 */

			if (modBanner != null) {
				modBanner.removeAllViews();
			}
			/*
			 * MobFoxView mobFox = (MobFoxView) findViewById(R.id.mobFoxView); if (mobFox!=null) {
			 * mobFox.setVisibility(View.GONE); }
			 */

		}
	}

	final Runnable adUpdateResults = new Runnable() {
		public void run() {
			LinearLayout modBanner = (LinearLayout) findViewById(R.id.modbanner);
			Log.d(TAG, "initAD:" + modBanner.getHeight() + "-" + modBanner.getWidth());
		}
	};

	public void initAD(String pageId) {
		Log.d(TAG, "initAD:" + pageId);

		// LinearLayout modBanner = (LinearLayout) findViewById(R.id.modbanner);

		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

		LinearLayout BannerLayout = (LinearLayout) findViewById(R.id.modbanner_wrap);
		BannerLayout.setVisibility(View.VISIBLE);

		if (NUtil.netCheckin(getApplicationContext()) && !NAction.checkIfPayIAP(getApplicationContext(), "ad")) {

			if (!NAction.getExtP(getApplicationContext(), "ad_no_" + pageId).equals("1")) {

				LinearLayout.LayoutParams adViewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);

				String[] adConf = NAction.getAd(getApplicationContext());

				String ad = NAction.getExtAdConf(getApplicationContext());

				if (adConf[0].equals("selfx")) {
					final List<String> ltImgLink = new ArrayList<String>();
					List<String> ltResImg = new ArrayList<String>();
					try {
						JSONObject jsonObj = new JSONObject(ad);

						JSONArray arrAd = jsonObj.getJSONArray("banner");
						for (int i = 0; i < arrAd.length(); i++) {
							JSONObject json = arrAd.getJSONObject(i);
							String ad_code = json.getString("ad_code");
							if (!NUtil.checkAppInstalledByName(getApplicationContext(), ad_code)) {
								Log.d(TAG, "ad_code:" + ad_code);

								ltResImg.add(json.getString("ad_img"));
								String link = confGetUpdateURL(3) + "&linkid=" + json.getString("adLink_id");

								ltImgLink.add(link);
							}
							else {
								Log.d(TAG, "!ad_code:" + ad_code);

							}

						}

					}
					catch (JSONException e) {
						e.printStackTrace();
					}

					AdSlidShowView adSlid = (AdSlidShowView) findViewById(R.id.adSlid);
					adSlid.setImagesFromUrl(ltResImg);
					adSlid.setOnUrlBackCall(new urlBackcall() {
						@Override
						public void onUrlBackCall(int i) {
							Intent intent = NAction.getLinkAsIntent(getApplicationContext(), ltImgLink.get(i));
							startActivity(intent);
						}
					});
					adSlid.setVisibility(View.VISIBLE);
				}
				if (adConf[0].equals("self")) {
//					final String adBanner = adConf[1];
//					final String adLink = adConf[2];
//					final String adKey = adConf[3];
//					AsyncImageView imageAd = (AsyncImageView) findViewById(R.id.image_ad);
//					imageAd.setUrl(adBanner);
//					imageAd.setOnClickListener(new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							NAction.recordAdLog(getApplicationContext(), "ad", adKey);
//							Intent intent = NAction.getLinkAsIntent(getApplicationContext(), adLink);
//							startActivity(intent);
//						}
//					});
//					imageAd.setVisibility(View.VISIBLE);
//					findViewById(R.id.modbanner_wrap).setVisibility(View.VISIBLE);
				}
			}
		}
		disNotify(pageId);

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Dialog onCreateDialog(int id) {
		return WBase.onCreateDialog(id);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openWaitWindow() {
		Log.d(TAG, "openWaitWindow");
		if (waitingWindow == null) {
			waitingWindow = NUtil.progressWindow(this, R.string.wating_title);
		}
		else {
			waitingWindow.cancel();
			waitingWindow = NUtil.progressWindow(this, R.string.wating_title);
		}
		try {
			waitingWindow.show();
		}
		catch (BadTokenException e) {
			Log.d(TAG, "openWaitWindow: e:" + e.getLocalizedMessage());

		}
	}
	
	public void openWaitWindow(String message) {
		Log.d(TAG, "openWaitWindow");
		if (waitingWindow == null) {
			waitingWindow = NUtil.progressWindow(this, message);
		}
		else {
			waitingWindow.cancel();
			waitingWindow = NUtil.progressWindow(this, message);
		}
		try {
			waitingWindow.show();
		}
		catch (BadTokenException e) {
			Log.d(TAG, "openWaitWindow: e:" + e.getLocalizedMessage());

		}
	}

	public void closeWaitWindow() {
		if (waitingWindow != null) {
			try {
				if (waitingWindow.isShowing()) {
					waitingWindow.dismiss();
				}
			}
			catch (Exception e) {

			}
		}
	}

	public void onBack(View v) {
		finish();
	}

	public void disNotify(String pageId) { // 可以做到有针对性地推荐
		LinearLayout notifyBar = (LinearLayout) findViewById(R.id.notify_bar);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0);

		if (notifyBar != null) {
			try {
				notifyBar.setLayoutParams(p);
			}
			catch (Exception e) {

			}
			String notifyMsg = NAction.getExtP(getApplicationContext(), "notify_msg");
			String notifyVer = NAction.getExtP(getApplicationContext(), "notify_check");
			String notifyMsgExt = NAction.getExtP(getApplicationContext(), "notify_msg_" + pageId);
			String notifyVerExt = NAction.getExtP(getApplicationContext(), "notify_check_" + pageId);

			if (!notifyMsgExt.equals("")) {
				if (!notifyVerExt.equals("")) {
					if (!NUtil.checkAppInstalledByName(getApplicationContext(), notifyVerExt)) { // 如果未安装，则提示
						disNotifyContent(notifyMsgExt);
					}

				}
				else {
					if (!notifyMsgExt.equals("-")) {
						disNotifyContent(notifyMsgExt);
					}
				}

			}
			else {
				if (notifyVer.equals("")) {
					disNotifyContent(notifyMsg);
				}
				else {
					if (!NUtil.checkAppInstalledByName(getApplicationContext(), notifyVer)) {
						disNotifyContent(notifyMsg);
					}
				}
			}
		}
	}

	public void disNotifyContent(String msg) {
		LinearLayout notifyBar = (LinearLayout) findViewById(R.id.notify_bar);
		TextView notifyMsg = (TextView) findViewById(R.id.notify);
		if (!(msg.equals("-") || msg.equals(""))) {
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			notifyMsg.setText(msg);
			try {
				notifyBar.setLayoutParams(p);
				notifyMsg.setLayoutParams(p);
			}
			catch (Exception e) {

			}

			notifyMsg.setVisibility(View.VISIBLE);
			notifyBar.setVisibility(View.VISIBLE);
			findViewById(R.id.modbanner_wrap).setVisibility(View.VISIBLE);

		}
		else {
			notifyMsg.setVisibility(View.GONE);
			notifyBar.setVisibility(View.GONE);
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected boolean sendErrorLog(final Context context) {
		if (NUtil.netCheckin(getApplicationContext())) {
			Log.d(TAG, "sendErrorLog");

			RequestParams myParam = new RequestParams();
			String updateUrl = NAction.getUpdateHost(getApplicationContext());
			if (updateUrl.equals("")) {
				updateUrl = confGetUpdateURL(2);
			}

			String collectInfos = BASE_CONF.COLLECT_INFO;
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					String k = field.getName();
					if (k != null) {
						k = k.toLowerCase().trim();
					}
					if (collectInfos.contains("#" + k + "#")) {
						myParam.put(k, field.get(null).toString());
					}
				}
				catch (Exception e) {
					Log.e(TAG, "an error occured when collect crash info", e);
				}
			}

			AppLog appDB = new AppLog(context);
			final ArrayList<String[]> logs = appDB.getLogs(0);
			if (logs.size() != 0) {
				JSONArray descArray = new JSONArray();
				// Log.d(TAG, "sendErrorLog:"+logs);
				for (int i = 0; i < logs.size(); i++) {
					String[] items = logs.get(i);
					myParam.put("act", "report_err");
					descArray.put(items[5]);


					appDB.deleteLog(Long.parseLong(items[0]));
				}
				myParam.put("report_desc", descArray.toString());

                QBaseApp.getInstance().getAsyncHttpClient().post(updateUrl + "?" + NAction.getUserUrl(getApplicationContext()),
				myParam, new JsonHttpResponseHandler() { });

				return true;
			}
			else {
				// Log.d(TAG, "sendErrorLog no need");
				return false;
			}
		}
		else {
			return false;
		}
	}

	protected void checkUpdate(final Context context, final boolean auto) {
		String upVer = NAction.getExtP(this, "conf_update_ver");

		if (upVer.equals(""))
			upVer = "0";
		int upVerNum = Integer.parseInt(upVer);
		// Log.d(TAG, "vercheck:"+upVerNum+"-"+BASE_CONF.UPDATE_VER);
		if (upVerNum < BASE_CONF.UPDATE_VER) {

			NAction.setUpdateHost(this, "");
		}

		if (NUtil.netCheckin(getApplicationContext())) {
			// clear db cache
			CacheLog cDB = new CacheLog(context);
			cDB.cleanCache();

			if (!auto)
				openWaitWindow();
			RequestParams myParam = new RequestParams();
			String types = NAction.getExtP(context, "conf_get_log_types");
			String limit = NAction.getExtP(context, "conf_get_log_limit");
			if (limit.equals("")) {
				limit = BASE_CONF.LOG_LIMIT;
			}
			UserLog pq = new UserLog(getApplicationContext());
			String xlogs;
			try {
				xlogs = pq.getLogs(types, 0, limit, "ASC");
			}
			catch (OutOfMemoryError e) {
				Log.d(TAG, "err when getLogs:" + e.getMessage());
				xlogs = "";
				pq.deleteAllStat_0_Log();
			}
			final String logs = xlogs;

			myParam.put("time", DateTimeHelper.getDateMin());
			myParam.put("logs", logs);

			// Log.d(TAG, "up logs:"+logs);

			/* 手机客户信息 */
			String collectInfos = NAction.getExtP(context, "conf_get_log_cls");
			if (collectInfos.equals("")) {
				collectInfos = BASE_CONF.COLLECT_INFO;
			}
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					String k = field.getName();
					if (k != null) {
						k = k.toLowerCase().trim();
					}
					if (collectInfos.contains("#" + k + "#")) {
						myParam.put(k, field.get(null).toString());
					}
					// Log.d(TAG, field.getName() + " : " + field.get(null));

				}
				catch (Exception e) {
					Log.e(TAG, "an error occured when collect crash info", e);
				}
			}

			if (BASE_CONF.DEBUG)
				Log.d(TAG, "checkUpdate:" + logs);
			String updateUrl = NAction.getUpdateHost(getApplicationContext());
			if (updateUrl.equals("")) {
				updateUrl = confGetUpdateURL(1);
			}
			if (!BASE_CONF.DEBUG)
				Log.d(TAG, "checkUpdate:" + updateUrl + "?" + NAction.getUserUrl(getApplicationContext()));

			QBaseApp.getInstance().getAsyncHttpClient().post(updateUrl + "?" + NAction.getUserUrl(getApplicationContext()),
				myParam,
				new JsonHttpResponseHandler() {
			    @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject result) {
					if (!auto)
						closeWaitWindow();
					if (BASE_CONF.DEBUG)
						Log.d(TAG, "checkUpdate-result:" + result.toString());

					// if (!logs.equals("")) {
					UserLog pq = new UserLog(context);
					pq.deleteAllStat_0_Log();
					// }

					int localVersion = NUtil.getVersinoCode(context);

					try {
						List<String> ks = Utils.copyIterator(result.keys());
						String k;
						if (ks.size() == 1) {
							k = ks.get(0);
						}
						else {
							k = NAction.getCode(context);
						}
						JSONObject info = result.getJSONObject(k);
						int serverVersion = info.getInt("ver");
						String versionTitle = info.getString("verName");
						String versionDesc = info.getString("verDesc");
						String versionLink = info.getString("link");
						String versionType = info.getString("type");

						NAction.setInstallLink(getApplicationContext(), versionLink);

						if (BASE_CONF.DEBUG)
							Log.d(TAG, "serverVersion:" + serverVersion + "-versionTitle:" + versionTitle
									+ "-versionDesc:" + versionDesc + "-versionLink:" + versionLink);
						// 检查
						if (localVersion < serverVersion) {
							alertUpdateDialog(getString(R.string.up_soft) + versionTitle + "\n" + versionDesc,
									versionLink, versionType);

						}
						else {
							// 提示已经是最新版本，无需升级
							if (!auto) {
								alertUpdateDialog2(versionTitle);
							}
						}

						String adWho = info.getString("adWho");
						String adBanner = info.getString("adBanner");
						String adLink = info.getString("adLink");
						String adKey = info.getString("adKey");
						String adTerm = info.getString("adTerm");
						String adAct = info.getString("adAct");

						NAction.setAd(context, adWho, adBanner, adLink, adKey, adTerm, adAct);

						String appAbout = info.getString("appAbout");
						String appUrl = info.getString("appUrl");
						String appPrivacyTitle = "";
						if (info.has("privacyTitle")) {
							appPrivacyTitle = info.getString("privacyTitle");
						}

						String appPrivacyUrl = "";
						if (info.has("privacyUrl")) {
							appPrivacyUrl = info.getString("privacyUrl");
						}

						NAction.setAppConf(context, appAbout, appUrl, appPrivacyTitle, appPrivacyUrl);
						String updateQ = info.getString("updateQ");
						NAction.setUpdateQ(getApplicationContext(), updateQ);

						// 插件
						String pluginNoAd = info.getString("plugin_noad");
						NAction.setPluginsEnable(context, pluginNoAd);

						//
						String updateHost = info.getString("update_host");
						NAction.setUpdateHost(context, updateHost);
						String contentHost = info.getString("content_host");
						NAction.setContentHost(context, contentHost);
						String html5Index = info.getString("html5_index");
						NAction.setHtml5Index(context, html5Index);

						String extConf = "";
						if (info.has("ext2")) {
							JSONObject xx = info.getJSONObject("ext2");
							if (xx != null) {
								extConf = xx.toString();
							}
						}
						NAction.setExtConf(context, extConf);

						String extAdConf = "";
						if (info.has("ext_ad")) {
							JSONObject xx = info.getJSONObject("ext_ad");
							if (xx != null) {
								extAdConf = xx.toString();
							}
						}
						NAction.setExtAdConf(context, extAdConf);

						// 是否要push消息
						String msg_ver = NAction.getExtP(context, "msg_ver");
						String msg_ver_local = NStorage.getSP(context, "msg.ver");
						int msg_ver_num = 0;
						int msg_ver_local_num = 0;
						if (!msg_ver.equals("")) {
							msg_ver_num = Integer.parseInt(msg_ver);
						}
						if (!msg_ver_local.equals("")) {
							msg_ver_local_num = Integer.parseInt(msg_ver_local);

						}
						if (msg_ver_num > msg_ver_local_num) {
							String msg_msg = NAction.getExtP(context, "msg_msg");
							String msg_link = NAction.getExtP(context, "msg_link");
							String msg_type = NAction.getExtP(context, "msg_type");

							NotificationManager updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
							Intent updateIntent;
							if (msg_type.equals("activity")) {

								updateIntent = new Intent();
								updateIntent.setClassName(context.getPackageName(), msg_link);
								updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL0,
										NAction.getExtP(context, "msg_param"));

							}
							else {

								updateIntent = new Intent(Intent.ACTION_VIEW);
								updateIntent.setData(Uri.parse(msg_link));

							}

							PendingIntent updatePendingIntent = PendingIntent.getActivity(context,
									NOTIFICATION_ID, updateIntent, 0);

							Notification updateNotification = NAction.getNotification(getApplicationContext(),getString(R.string.app_name), msg_msg, updatePendingIntent, R.drawable.icon_nb, null,Notification.FLAG_AUTO_CANCEL);

							// 发出通知

							updateNotificationManager.notify(NOTIFICATION_ID, updateNotification);

							NStorage.setSP(context, "msg.ver", msg_ver);
						}
						// 检查是否需要升级Py包
						String py_core_ver = NAction.getExtP(context, "py_core_ver");
						String py_core_path = NAction.getExtP(context, "py_core_path");
						String py_core_url = NAction.getExtP(context, "py_core_url");
						String py_core_ver_local = NStorage.getSP(context, "py_core.ver");
						int py_core_ver_num = 0;
						int py_core_ver_local_num = 0;
						if (!py_core_ver.equals("")) {
							py_core_ver_num = Integer.parseInt(py_core_ver);

						}

						if (!py_core_ver_local.equals("")) {
							py_core_ver_local_num = Integer.parseInt(py_core_ver_local);

						}
						Log.d(TAG, "py ver check:" + py_core_ver_num + ":" + py_core_ver_local_num);
						if (py_core_ver_num > py_core_ver_local_num) {
							// 升级Pycore
							Log.d(TAG, "pycore need updating:" + py_core_path + ":" + py_core_url + ":"
									+ py_core_ver + ":" + FileHelper.getExt(py_core_url, ""));

							Intent updateIntent = new Intent();
							updateIntent.setClassName(context.getPackageName(), context.getPackageName()
									+ ".QBaseUpdateService");
							updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL1, R.string.app_name);
							updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL2, py_core_url);
							updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL3, FileHelper.getExt(py_core_url, ""));
							updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL4, py_core_path);
							updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL5, "");
							updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL6, "0");

							context.startService(updateIntent);

							NStorage.setSP(context, "py_core.ver", py_core_ver);
						}

						// 设置已经升级
						NAction.setUpdateCheckTime(context);
					}
					catch (JSONException e) {
						Log.d(TAG, "checkUpdate e:" + e.getMessage());
						e.printStackTrace();
					}

				}
                @Override
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
					// waitingWindow.dismiss();
					Log.d(TAG, "error:" + throwable.getMessage());
					if (!auto)
						closeWaitWindow();

				}
			});
		}
	}

	public void checkUpdate(String root) {
		int now = VeDate.getStringDateHourAsInt();
		int lastCheck = NAction.getUpdateCheckTime(this);
		String upVer = NAction.getExtP(this, "conf_update_ver");

		if (upVer.equals(""))
			upVer = "0";
		int upVerNum = Integer.parseInt(upVer);
		// Log.d(TAG, "vercheck:"+upVerNum+"-"+BASE_CONF.UPDATE_VER);
		if (upVerNum < BASE_CONF.UPDATE_VER) {

			NAction.setUpdateHost(this, "");
			checkUpdate(getApplicationContext(), true);

		}
		else {

			if (!sendErrorLog(getApplicationContext())) {

				int q = NAction.getUpdateQ(getApplicationContext());
				if (q == 0) {
					q = BASE_CONF.UPDATEQ;
				}
				if ((now - lastCheck) >= q) { // 每q小时检查一次更新/清空一下不必要的cache
					checkUpdate(getApplicationContext(), true);
					// 清空图片目录的缓存
					String cacheDir = Environment.getExternalStorageDirectory() + "/" + root + "/" + BASE_CONF.DCACHE + "/";
					FileHelper.clearDir(cacheDir, 0, false);

					// 清理DB缓存

				}
			}
		}

	}

	public void onCheckUpdate(View v) {
		if (NUtil.netCheckin(getApplicationContext())) {
			checkUpdate(getApplicationContext(), false);

		}
		else {
			Toast.makeText(getApplicationContext(), R.string.net_error, Toast.LENGTH_SHORT).show();
		}
	}

	protected void alertUpdateDialog2(String desc) {
		try {
			if (BASE_CONF.DEBUG)
				Log.d(TAG, "no need to update");
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(getString(R.string.up_soft))
					.setMessage(getString(R.string.up_soft_state_no_found) + "\n" + desc)
					.setPositiveButton(getString(R.string.promote_ok), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					}).setNegativeButton(getString(R.string.promote_cancel), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alert.create().show();
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(), R.string.up_soft_state_no_found, Toast.LENGTH_SHORT).show();
		}
	}

	protected void alertUpdateDialog(String desc, final String updatelink, final String type) {
		try {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(getString(R.string.up_soft))
					.setMessage(getString(R.string.up_soft_state_found) + "\n" + desc)
					.setPositiveButton(getString(R.string.up_soft), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (BASE_CONF.DEBUG)
								Log.d(TAG, "alertUpdateDialog updatelink:" + updatelink);
							NAction.recordAdLog(getApplicationContext(), "update", "");

							if (type.equals("link")) {
								Intent intent = NAction.getLinkAsIntent(getApplicationContext(), updatelink);
								startActivity(intent);

							}
							else {
								Intent updateIntent = new Intent(getApplicationContext(), getUpdateSrv());
								updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL1, R.string.app_name);
								updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL2, updatelink);
								updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL3, "apk");
								updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL4, "");
								updateIntent.putExtra(BASE_CONF.EXTRA_CONTENT_URL5, "");

								startService(updateIntent);
							}
						}
					}).setNegativeButton(getString(R.string.promote_cancel), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			alert.create().show();
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(), R.string.up_soft_state_no_found, Toast.LENGTH_SHORT).show();
		}

	}

	abstract public Class<?> getUpdateSrv();

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void sendWithBt(String file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("video/*");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(file)));
		startActivityForResult(Intent.createChooser(intent, getString(R.string.info_share_withbt)), 1000);
	}


	/*
	 * @Override protected void onStart() { super.onStart(); Log.e(TAG, "start onStart~~~"); }
	 * @Override protected void onRestart() { super.onRestart(); Log.e(TAG, "start onRestart~~~"); }
	 * @Override protected void onPause() { super.onPause(); Log.e(TAG, "start onPause~~~"); }
	 * @Override public void onConfigurationChanged(Configuration newConfig) { }
	 * @Override protected void onStop() { super.onStop(); Log.e(TAG, "start onStop~~~"); }
	 */
	// 文件排序 - 名称排序
	@SuppressWarnings("rawtypes")
	protected final Comparator sortTypeByName = new Comparator<File>() {
		@Override
		public int compare(File arg00, File arg11) {
			String arg0 = arg00.toString();
			String arg1 = arg11.toString();
			String ext = null;
			String ext2 = null;
			int ret;

			try {
				ext = arg0.substring(arg0.lastIndexOf(".") + 1, arg0.length()).toLowerCase();
				ext2 = arg1.substring(arg1.lastIndexOf(".") + 1, arg1.length()).toLowerCase();

			}
			catch (IndexOutOfBoundsException e) {
				return 0;
			}
			ret = ext.compareTo(ext2);

			if (ret == 0)
				return arg0.toLowerCase().compareTo(arg1.toLowerCase());
			return ret;
		}
	};

	@SuppressWarnings("rawtypes")
	protected final Comparator sortTypeByTime = new Comparator<File>() {
		@Override
		public int compare(File arg00, File arg11) {
			long diff = arg00.lastModified() - arg11.lastModified();
			if (diff > 0)
				return -1;
			else if (diff == 0)
				return 0;
			else
				return 1;
		}
	};

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void openURL(String url) {
		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
		startActivity(it);
	}

	private String wvCookie = "";
	private String wvDocument = "";

	@SuppressLint("NewApi")
	public String getwvAU() {
		if (wv != null) {
			WebSettings settings = wv.getSettings();
			return settings.getUserAgentString();
		}
		else {
			return BASE_CONF.USER_AGENT;
		}
	}

	public String getwvCookie() {
		return wvCookie;
	}

	public String getwvUrl() {
		if (wv != null) {
			return wv.getUrl();
		}
		else {
			return "";
		}
	}

	public String getwvDocument() {
		return wvDocument;

	}

	class QPyLib {
		public QPyLib(Context context) {
		}

		@JavascriptInterface
		public void processHTML(String cookie, String data) {
			// Log.d(TAG, "processHTML called(cookie):"+cookie);
			// Log.d(TAG, "processHTML called(data):"+data);
			wvCookie = cookie;
			wvDocument = data;

		}
	}

	protected ValueCallback<Uri> mUploadMessage;

	protected final static int FILECHOOSER_RESULTCODE = 1;

	// webview
	@TargetApi(7)
	public void initWebView() {// 初始化
		useProxyInWebView();
		if (wvProgressBar == null) {
			wvProgressBar = (ProgressBar) findViewById(R.id.WebViewProgress);
		}
		if (wvProgressBar != null)
			wvProgressBar.setMax(100);

		if (wv == null) {
//			wv = (WebView) findViewById(R.id.wv);
		}
		// wv = new WebView(this);
		wv.getSettings().setJavaScriptEnabled(true);// 可用JS
		wv.addJavascriptInterface(new QPyLib(this), "qpylib");
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
		wv.getSettings().setRenderPriority(RenderPriority.HIGH);
		wv.getSettings().setBlockNetworkImage(false);
		wv.getSettings().setAllowFileAccess(true);
		/*
		 * try { wv.getSettings().setPluginsEnabled(true); } catch (Exception e) { }
		 */
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
				Log.d(TAG, "setWebViewClient URL:" + url);
				loadurl(view, url);// 载入网页
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Log.d(TAG, "errcode:" + errorCode + "-desc:" + description + "-url:" + failingUrl);
				// loadurl(wv,
				// "file:///android_asset/mbox/md3.html?act=err&info="+description+"&"+NAction.getUserUrl(getApplicationContext()));
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				//view.loadUrl("javascript:window.qpylib.processHTML(document.cookie, document.getElementsByTagName('html')[0].innerHTML);");
				// view.loadUrl("javascript:(function(){document.getElementById('snapNSendBtn').onclick=function(){var bean=window.bean;var title=bean.getTitle();alert(title);}})()");
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				wv.requestFocus();
				EditText et = (EditText) findViewById(R.id.url_input);
				if (et != null) {
					et.setText(url);
				}
			}

		});

		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				if (progress == 100) {
					// handler.sendEmptyMessage(1);//如果全部载入,隐藏进度对话框
					// wvProgressBar.setVisibility(View.GONE);
				}
				if (wvProgressBar != null)
					wvProgressBar.setProgress(wv.getProgress());
				super.onProgressChanged(view, progress);
			}

		});

//		wv.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				// Log.d(TAG, "click detected");
//				return true;
//			}
//		});
		// wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		wv.getSettings().setLoadWithOverviewMode(true);
		// wv.getSettings().setJavaScriptEnabled(true);

		// wv.addJavascriptInterface(new JavascriptInterface(MSearchAct.this),
		// "bean");
		// registerForContextMenu(wv);
		// openWaitWindow();

		wv.getSettings().setJavaScriptEnabled(true);// 可用JS
		wv.addJavascriptInterface(new QPyLib(this), "qpylib");

		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
		wv.getSettings().setRenderPriority(RenderPriority.HIGH);
		wv.getSettings().setBlockNetworkImage(false);
		wv.getSettings().setAllowFileAccess(true);
		// wv.getSettings().setBuiltInZoomControls(true);// 设置支持缩放

		String ua = wv.getSettings().getUserAgentString();
		wv.getSettings().setUserAgentString(ua + " :QUSEIT");

		/*
		 * try { wv.getSettings().setPluginsEnabled(true); } catch (Exception e) { }
		 */
		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
				Log.d(TAG, "setWebViewClient URL:" + url);
				loadurl(view, url);// 载入网页
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Log.d(TAG, "errcode:" + errorCode + "-desc:" + description + "-url:" + failingUrl);
				// loadurl(wv,
				// "file:///android_asset/mbox/md3.html?act=err&info="+description+"&"+NAction.getUserUrl(getApplicationContext()));
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:window.qpylib.processHTML(document.cookie, document.getElementsByTagName('html')[0].innerHTML);");
				// view.loadUrl("javascript:(function(){document.getElementById('snapNSendBtn').onclick=function(){var bean=window.bean;var title=bean.getTitle();alert(title);}})()");
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				wv.requestFocus();
				EditText et = (EditText) findViewById(R.id.url_input);
				if (et != null) {
					et.setText(url);
				}
			}
		});

		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				if (progress == 100) {
					// handler.sendEmptyMessage(1);//如果全部载入,隐藏进度对话框
					// wvProgressBar.setVisibility(View.GONE);
				}
				if (wvProgressBar != null)
					wvProgressBar.setProgress(wv.getProgress());
				super.onProgressChanged(view, progress);
			}

			// The undocumented magic method override
			// Eclipse will swear at you if you try to put @Override here
			// For Android 3.0+

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
			}

			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

			}

		});

		// wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		// wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		wv.getSettings().setLoadWithOverviewMode(true);
		// wv.getSettings().setJavaScriptEnabled(true);

		// wv.addJavascriptInterface(new JavascriptInterface(MSearchAct.this), "bean");
		// registerForContextMenu(wv);
		// openWaitWindow();
	}

	Handler wvHandler = new Handler() {
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case 0:
					openWaitWindow();
					break;
				case 1:
					closeWaitWindow();
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	public void loadurl(final WebView view, final String url) {
		view.loadUrl(url);// 载入网页
		/*
		 * new Thread(){ public void run(){ //handler.sendEmptyMessage(0); try{ view.loadUrl(url);//载入网页 if (BASE_CONF.DEBUG)
		 * Log.d(TAG, "load url:"+url); } catch (Exception e) { } } }.start();
		 */
	}

	public void loadContent(final WebView view, final String content, final String historyUrl) {
		new Thread() {
			public void run() {
				// handler.sendEmptyMessage(0);
				view.loadDataWithBaseURL("file:///android_asset/", content, "text/html", "UTF-8", historyUrl);
				if (BASE_CONF.DEBUG)
					Log.d(TAG, "load content:" + content);
			}
		}.start();
	}

	public void onRefresh(View v) {
		if (wv != null) {
			// openWaitWindow();
			wv.reload();
		}
	}

	public void useProxyInWebView() {
		HttpHost hcProxyHost = null;
		try {
			String proxyHost = NAction.getProxyHost(this);
			if (!proxyHost.equals("")) {
				String proxyPort = NAction.getProxyPort(this);
				// String proxyUsername = NAction.getProxyUsername(this);
				// String proxyPwd = NAction.getProxyPwd(this);
				hcProxyHost = new HttpHost(proxyHost, Integer.parseInt(proxyPort), "http");
			}
		}
		catch (Exception e) {
			Log.e(TAG, "ERROR WHEN GET PROXY");
		}

		if (hcProxyHost != null) {
			setProxyHostField(hcProxyHost);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean setProxyHostField(HttpHost proxyServer) {
		// Getting network
		Class networkClass = null;
		Object network = null;
		try {
			networkClass = Class.forName("android.webkit.Network");
			Field networkField = networkClass.getDeclaredField("sNetwork");
			network = getFieldValueSafely(networkField, null);
		}
		catch (Exception ex) {
			Log.e(TAG, "error getting network");
			return false;
		}
		if (network == null) {
			Log.e(TAG, "error getting network : null");
			return false;
		}
		Object requestQueue = null;
		try {
			Field requestQueueField = networkClass.getDeclaredField("mRequestQueue");
			requestQueue = getFieldValueSafely(requestQueueField, network);
		}
		catch (Exception ex) {
			Log.e(TAG, "error getting field value");
			return false;
		}
		if (requestQueue == null) {
			Log.e(TAG, "Request queue is null");
			return false;
		}
		Field proxyHostField = null;
		try {
			Class requestQueueClass = Class.forName("android.net.http.RequestQueue");
			proxyHostField = requestQueueClass.getDeclaredField("mProxyHost");
		}
		catch (Exception ex) {
			Log.e(TAG, "error getting proxy host field");
			return false;
		}
		synchronized (this) {
			boolean temp = proxyHostField.isAccessible();
			try {
				proxyHostField.setAccessible(true);
				proxyHostField.set(requestQueue, proxyServer);
			}
			catch (Exception ex) {
				Log.e(TAG, "error setting proxy host");
			}
			finally {
				proxyHostField.setAccessible(temp);
			}
		}
		return true;
	}

	private Object getFieldValueSafely(Field field, Object classInstance) throws IllegalArgumentException,
			IllegalAccessException {
		boolean oldAccessibleValue = field.isAccessible();
		field.setAccessible(true);
		Object result = field.get(classInstance);
		field.setAccessible(oldAccessibleValue);
		return result;
	}

	public abstract String confGetUpdateURL(int flag);

	// //////////////////////////////////////////////////////

	// feedback
	public void onFeedback(View v) {
		String mailto = NAction.getExtP(getApplicationContext(), "conf_feedback_email");
		if (mailto.equals("")) {
			mailto = BASE_CONF.FEEDBACK_EMAIL;
		}

		String app = getString(R.string.app_name_label);
		int ver = NUtil.getVersinoCode(getApplicationContext());
		String title = MessageFormat.format(getString(R.string.feeback_email_title), app, ver, Build.PRODUCT);
		String lastError = "";
		String code = NAction.getCode(getApplicationContext());
		File log = new File(Environment.getExternalStorageDirectory() + "/" + code + "_last_err.log");
		if (log.exists()) {
			lastError = FileHelper.getFileContents(log.getAbsolutePath());
		}
		String body = MessageFormat.format(getString(R.string.feedback_email_body), Build.PRODUCT,
				Build.VERSION.RELEASE, Build.VERSION.SDK, lastError);
		NAction.sendEmail(this, mailto, title, body);

		// Intent intent = new Intent(getApplicationContext(),
		// OFeedBackAct.class);
		// startActivity(intent);
	}
	//
    public void unpackData(final String resource, File target) {
    	ResourceManager resourceManager = new ResourceManager(this);

        // The version of data in memory and on disk.
        String data_version = resourceManager.getString(resource + "_version");
        String disk_version = "0";
        boolean isPrivate =  resource.startsWith("private");

        //Log.d(TAG, "data_version:"+data_version+"-"+resource + "_version"+"-"+resourceManager);
        // If no version, no unpacking is necessary.
        if (data_version == null) {
            return;
        }

        // Check the current disk version, if any.
        String filesDir = target.getAbsolutePath();
        String disk_version_fn = filesDir + "/" + resource + ".version";

        try {
            byte buf[] = new byte[64];
            InputStream is = new FileInputStream(disk_version_fn);
            int len = is.read(buf);
            disk_version = new String(buf, 0, len);
            is.close();
        } catch (Exception e) {
            disk_version = "0";
        }

        // If the disk data is out of date, extract it and write the
        // version file.
        /*boolean extract = false;
        if (disk_version.equals("")) {
        	extract = true;
        } else {
        	Float data_v = Float.parseFloat(data_version);
        	Float disk_v = Float.parseFloat(disk_version);
        	if (data_v.intValue()>disk_v.intValue()) {
        		extract = true;
        	}
        }*/
        
        //Log.d(TAG, "data_version:"+Math.round(Double.parseDouble(data_version))+"-disk_version:"+Math.round(Double.parseDouble(disk_version))+"-RET:"+(int)(Double.parseDouble(data_version)-Double.parseDouble(disk_version)));
        if ((int)(Double.parseDouble(data_version)-Double.parseDouble(disk_version))>0 || disk_version.equals("0")) {
            Log.v(TAG, "Extracting " + resource + " assets.");
            //recursiveDelete(target);

            target.mkdirs();

            AssetExtract ae = new AssetExtract(this);
            if (!ae.extractTar(resource + ".mp3", target.getAbsolutePath())) {
                Toast.makeText(this,"Could not extract " + resource + " data.", Toast.LENGTH_SHORT).show();
            }

            try {
            	/*if (resource.equals("private")) {
            		Toast.makeText(getApplicationContext(), R.string.first_load, Toast.LENGTH_SHORT).show();
            	}*/
                // Write .nomedia.
                new File(target, ".nomedia").createNewFile();

                // Write version file.
                FileOutputStream os = new FileOutputStream(disk_version_fn);
                os.write(data_version.getBytes());
                os.close();
            } catch (Exception e) {
                Log.w("python", e);
                Toast.makeText(this, "Could not extract " + resource + " data, make sure your device have enough space.", Toast.LENGTH_LONG).show();
            }
        } else {
        	Log.d(TAG, "No extract:"+resource);

        }
        if (isPrivate) {
			File bind = new File(getFilesDir()+"/bin");
			for (File bin : bind.listFiles()) {
				try {
		  			//Log.d(TAG, "chmod:"+bin.getAbsolutePath());
		          
					FileUtils.chmod(bin, 0755);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
        }

    }

    public void onNotify(View v){}

	public void onPrivacy(View v) {
		String[] appConf = NAction.getAppConf(getApplicationContext());
		String privacyUrl = appConf[3];
		if (privacyUrl.equals("")) {
			privacyUrl = getString(R.string.privacy_url);
		}

		Intent intent = NAction.getLinkAsIntent(getApplicationContext(), privacyUrl);
		startActivity(intent);

	}

	/*
	 * // Notifier for receiving the full screen ad data on a successful connect. public void
	 * getFeaturedAppResponse(TapjoyFeaturedAppObject featuredApObject) { Log.i(TAG, "Displaying Full Screen Ad..");
	 * TapjoyConnect.getTapjoyConnectInstance().showFeaturedAppFullScreenAd(); } final Handler tapjoyHandler = new
	 * Handler(); // Notifier for when there is an error or no full screen ad to display. public void
	 * getFeaturedAppResponseFailed(String error) { tapjoyErr = error; // We must use a handler since we cannot update
	 * UI elements from a different thread. tapjoyHandler.post(tapjoyUpdateResults); } public void
	 * getDisplayAdResponse(View view) { adView = view; int ad_width = adView.getLayoutParams().width; int ad_height =
	 * adView.getLayoutParams().height; Log.i(TAG, "getDisplayAdResponse adView dimensions: " + ad_width + "x" +
	 * ad_height); // Using screen width, but substitute for the any width. update_display_ad = true; // We must use a
	 * handler since we cannot update UI elements from a different thread. tapjoyHandler.post(tapjoyUpdateResults); }
	 * public void getDisplayAdResponseFailed(String error) { // We must use a handler since we cannot update UI
	 * elements from a different thread. tapjoyErr = error; //Toast.makeText(getApplicationContext(), error,
	 * Toast.LENGTH_SHORT).show(); tapjoyHandler.post(tapjoyUpdateResults); } // video ad
	 * @Override public void videoReady() { Log.i(TAG, "videoReady.."); }
	 * @Override public void videoError(int statusCode) { Log.i(TAG, "videoError.."+statusCode); }
	 * @Override public void videoComplete() { Log.i(TAG, "videoComplete.."); }
	 */
	/*
	 * public boolean onKeyDown(int keyCode, KeyEvent event) { if (wv!=null) { if ((keyCode == KeyEvent.KEYCODE_BACK) &&
	 * wv.canGoBack()) { wv.goBack(); return true; } else { return wv.onKeyDown(keyCode, event); } } return
	 * super.onKeyDown(keyCode, event); }
	 */
}
