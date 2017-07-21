package com.quseit.util;

import android.content.Context;
import android.util.Log;

import com.quseit.asihttp.AsyncHttpClient;
import com.quseit.asihttp.AsyncHttpResponseHandler;
import com.quseit.asihttp.RequestParams;
import com.quseit.config.CONF;
import com.quseit.db.CacheLog;

import org.apache.http.HttpHost;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NRequest {
	private static final String TAG = "NRequest";

    
    public static void get2(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	if (!CONF.DEBUG)  Log.d(TAG, "get2:"+url+":p:"+params);
		HttpHost hcProxyHost = null;

    	String proxyHost = NAction.getProxyHost(context);    	
    	try {
	    	if (!proxyHost.equals("")) {
	        	String proxyPort = NAction.getProxyPort(context);
	        	//String proxyUsername = NAction.getProxyUsername(context);
	        	//String proxyPwd = NAction.getProxyPwd(context);
	        	hcProxyHost = new HttpHost(proxyHost, Integer.parseInt(proxyPort), "http");
	    	}
    	} catch (Exception e) {
    		Log.e(TAG, "ERROR WHEN GET PROXY");
    	}
    	
    	CacheLog dbCL = new CacheLog(context);
    	String ret[] = dbCL.get(Base64.encode(url), 0);
    	String content = ret[0];
    	String expired = ret[1];
    	
    	if (expired.equals("1") || content.equals("")) {
	    	AsyncHttpClient client = new AsyncHttpClient(hcProxyHost);
	    	String token = NStorage.getSP(context, "user.sec_token");
	    	if (!token.equals("")) {
	    		client.addHeader("QTOKEN", token);
	    	}
	    	
	    	client.get(url, params, responseHandler);
    	} else {
    		responseHandler.onSuccess(content);
    		
    		if (CONF.DEBUG) Log.d(TAG, "get2 onsucess from cache url("+url+")");
    	}
    }

    public static void post2(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	if (CONF.DEBUG)  Log.d(TAG, "post:"+url+"-param:"+params.toString());
		HttpHost hcProxyHost = null;

    	String proxyHost = NAction.getProxyHost(context);    	
    	if (!proxyHost.equals("")) {
        	String proxyPort = NAction.getProxyPort(context);
        	//String proxyUsername = NAction.getProxyUsername(context);
        	//String proxyPwd = NAction.getProxyPwd(context);
        	hcProxyHost = new HttpHost(proxyHost, Integer.parseInt(proxyPort), "http");
    	}

    	AsyncHttpClient client = new AsyncHttpClient(hcProxyHost);
    	String token = NStorage.getSP(context, "user.sec_token");
    	if (!token.equals("")) {
    		client.addHeader("QTOKEN", token);
    	}
        client.post(url, params, responseHandler);
    }

    
    public static Boolean isLinkable(String link){
	  HttpURLConnection urlConnection = null;  
	  try {  
	    URL url = new URL(link);  
	    urlConnection = (HttpURLConnection) url.openConnection();  
	    urlConnection.setRequestMethod("HEAD");  
	    urlConnection.setConnectTimeout(10000); /* timeout after 5s if can't connect */  
	    urlConnection.setReadTimeout(10000); /* timeout after 5s if the page is too slow */  
	    urlConnection.connect();  
	    String redirectLink = urlConnection.getHeaderField("Location");  
	    if (redirectLink != null && !link.equals(redirectLink)) {  
	      return isLinkable(redirectLink);  
	    } else {  
	      return urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK;  
	    }  
	  } catch (Exception e) {  
	    return false;  
	  } finally {  
	    if (urlConnection != null) {  
	      urlConnection.disconnect();  
	    }  
	  }  
    }
    
    public static String filterUrl(ArrayList<String> links){
    	Log.d(TAG, "here!"+links.size());
		for (int i=0;i<links.size();i++) {
			Log.d(TAG, "is checking " + links.get(i));
			if( isLinkable(links.get(i))) {
				Log.d(TAG, links.get(i) + " is good");
				return (String) links.get(i);
			}
		}
		return "";
    }
}

