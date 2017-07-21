package com.quseit.lib;

import java.util.Vector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

//import com.paypal.android.MECL.PayPal;

public class PPPlugin  {
	
	//The instance of our activity
	private static PPPlugin _instance;
	
	//The reference token that we get from initializing the MECL library
	public static String _deviceReferenceToken;
	
	// The PayPal server to be used - can also be ENV_NONE and ENV_LIVE
	//private static final int server = PayPal.ENV_SANDBOX;
	// The ID of your application that you received from PayPal
	//private static final String appID = "APP-80W284485P519543T";
	@SuppressWarnings("unused")
	private static final String appID = "APP-80W284485P519543T";
	
	public static final String build = "11.01.04.8174";
	
	//The possible results from initializing MECL
	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;
	
	//The pages of our application
	public static final int PAGE_HOME 		= 0;
	public static final int PAGE_CUSTOMIZE 	= 1;
	public static final int PAGE_FINALIZE 	= 2;
	public static final int PAGE_WEB		= 3;
	
	//The pizza sizes
	public static final int SIZE_SMALL 		= 0;
	public static final int SIZE_MEDIUM 	= 1;
	public static final int SIZE_LARGE 		= 2;
	
	//The pizza toppings
	public static final int TOPPING_EXTRA_CHEESE 	= 0;
	public static final int TOPPING_PEPPERONI 		= 1;
	public static final int TOPPING_MUSHROOMS 		= 2;
	public static final int TOPPING_ONIONS 			= 3;
	
	//The pizza acquiring methods
	public static final int METHOD_PICKUP 		= 0;
	public static final int METHOD_DELIVERY 	= 1;
	
	//Our current selections that are given defaults to start
	private int currentSize = SIZE_SMALL;
	private Vector<Integer> currentToppings = new Vector<Integer>();
	private int currentCount = 1;
	private int currentMethod = METHOD_PICKUP;
	
	//The WebView that we'll use to display MECL
	private WebView _webView;
	
	private Context context;
	
	//The popup we'll use to show loading when initializing the library
	private ProgressDialog mProgDialog;
	
	// This handler will allow us to properly update the UI. You cannot touch Views from a non-UI thread.
	Handler hRefresh = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
		    	case INITIALIZE_SUCCESS:
		    		//We have initialized the application, close the dialog and launch the WebView
		    		mProgDialog.cancel();
		    		launchWeb();
		    		Toast.makeText(context, "OK to initialize PayPal", Toast.LENGTH_SHORT).show();

		            break;
		    	case INITIALIZE_FAILURE:
		    		//Initialization failure, close the dialog, update the page and show a toast
		    		mProgDialog.cancel();
		    		Toast.makeText(context, "Failed to initialize PayPal", Toast.LENGTH_SHORT).show();
		    		break;
			}
		}
	};
	
    /** Called when the activity is first created. */
    public PPPlugin(Context context, WebView webview) {        
        //Set the instance
        _instance = this;
        
        this.context = context;
        this._webView = webview;
        
        //Set our defaults
        currentSize = SIZE_SMALL;
    	currentToppings = new Vector<Integer>();
    	currentCount = 1;
    	currentMethod = METHOD_PICKUP;
    }
    
    /*
     * This method will be used every time we want to change the page
     */
    public void changePage(int page) {
    	switch(page) {
    	case PAGE_WEB:    		
    		//Show the loading popup
    		mProgDialog = ProgressDialog.show(context, "", "Loading");
    		
    		//Create a separate thread to do the initialization
    		Thread libraryInitializationThread = new Thread() {
    			public void run() {
    				//Initialize the library
    				initLibrary();
    				
    				// The library is initialized so let's launch it by notifying our handler
    				/*if (PayPal.getInstance().isLibraryInitialized()) {
    					hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
    				}
    				else {
    					hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
    				}*/
    			}
    		};
    		libraryInitializationThread.start();
    		break;
    	}
    }
    
    private void initLibrary() {
		// This is the main initialization call that takes in your Context, the Application ID, the server you would like to connect to, and your PayPalListener
		//PayPal.fetchDeviceReferenceTokenWithAppID(context, appID, server, new PluginResultDelegate());
   			
		// -- These are required settings.
		//PayPal.getInstance().setLanguage("en_US"); // Sets the language for the library.
        // --
	}
    
    private void launchWeb() {
    	//Setup the url for our pizza app to connect to
    	String buf = new String("http://paydemo.sms4me.com/ECDemo-server/cart.jsp?");
	    buf += "delivery=" + (currentMethod == METHOD_PICKUP ? "false" : "true");
	    buf += "&count=" + currentCount;
	    String size = new String("Small");
	    if (currentSize == SIZE_MEDIUM)
	    	size = "Medium";
	    else if (currentSize == SIZE_LARGE)
	    	size = "Large";
	    buf += "&size=" + size;
	    if (currentToppings.size() > 0) {
	    	buf += "&toppings=";
	    	for (int i = 0; i < currentToppings.size(); i++) {
	    		if (i != 0)
	    			buf += ",";
	    		String topping = "Extra%20Cheese";
	    		if (currentToppings.elementAt(i) == TOPPING_PEPPERONI)
	    			topping = "Pepperoni";
	    		else if (currentToppings.elementAt(i) == TOPPING_MUSHROOMS)
	    			topping = "Mushrooms";
	    		else if (currentToppings.elementAt(i) == TOPPING_ONIONS)
	    			topping = "Onions";
	    		buf += topping;
	    	}
	    }
	    buf += "&drt=" + _deviceReferenceToken;
	    
	    android.util.Log.d("PIZZA_REQUEST", buf.replaceAll("&", "\n&"));
	   
	    //Construct the WebView
	    //_webView = new WebView(context);
	    
	    //Set our view to the WebView
	    _webView.getSettings().setJavaScriptEnabled(true);
	    
	    //Setup a WebViewClient so we know when the url changes
	    _webView.setWebViewClient(new WebViewClient() {
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    		//Our servlet only uses one url for both success or failure
	    		if (url.equals("http://paydemo.sms4me.com/ECDemo-server/cancel.html")) {
	    			//The url was hit so it's time to go back to our home page
	    			//changePage(PAGE_HOME);
	    			Toast.makeText(context, "You cancl the deal", Toast.LENGTH_SHORT).show();
	    			return true;
	    		}
	    		return false;
	    	}
	    });

	    //Load our url
	    _webView.loadUrl(buf);
	    
	    //The android WebView sometimes does not have focus and this affects different UI elements so we'll force the focus to work around this
	    _webView.requestFocus(View.FOCUS_DOWN);
    }
    
    public static PPPlugin getInstance() {
    	return _instance;
    }

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public Vector<Integer> getCurrentToppings() {
		return currentToppings;
	}

	public void setCurrentToppings(Vector<Integer> currentToppings) {
		this.currentToppings = currentToppings;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public int getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(int currentMethod) {
		this.currentMethod = currentMethod;
	}
	
	public WebView getWebView() {
		return _webView;
	}
	

}
