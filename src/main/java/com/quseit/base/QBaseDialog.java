package com.quseit.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quseit.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

//import android.media.AudioManager;

public class QBaseDialog {
	//private static final String TAG = "_FBaseAct";

    /** Called when the activity is first created. */
    public static final int DIALOG_NOTIFY_MESSAGE = 1000;
    public static final int DIALOG_YES_NO_MESSAGE = 2000;
    public static final int DIALOG_YES_NO_LONG_MESSAGE = 3000;
    public static final int DIALOG_LIST = 4000;
    public static final int DIALOG_PROGRESS = 5000;
    public static final int DIALOG_SINGLE_LIST = 5500;
    public static final int DIALOG_SINGLE_CHOICE = 6000;
    public static final int DIALOG_MULTIPLE_CHOICE = 7000;
    public static final int DIALOG_TEXT_ENTRY = 8000;
    public static final int DIALOG_MULTIPLE_CHOICE_CURSOR = 9000;
    public static final int DIALOG_DATE_ENTRY = 10000;
    public static final int DIALOG_TEXT_ENTRY2 = 11000;
    public static final int DIALOG_TEXT_ENTRY3 = 12000;
    public static final int DIALOG_BTN_ENTRY1 = 13000;

    public static final int DIALOG_EXIT = 1;

    public static final int MAX_PROGRESS = 100;
    
    public ProgressDialog mProgressDialog;
    public int mProgress;
    public Handler mProgressHandler;

    int dialogIcon;
    String dialogTitle;
    CharSequence[] dialogArray;
    boolean[] dialogArrayinit;
    int selectedIndex = 0;
    String hitTxt = null;
    String dialogTxt;
    String dialogTxt2;
    String dialogTxt3;
    int dateYear = 0;
    int dateMonth = 0;
    int dateDay = 0;
    String dialogVal;
    String dialogVal2;
    String dialogVal3;
   // private AudioManager AM;
    
    private Context mContext;
    private Activity mActivity;
    
    SoundPool mySoundpool;  
    HashMap<Integer,Integer> soundPoolMap;  
    public MediaPlayer myMediaplayer;  
    
    DialogInterface.OnClickListener dialogObj;
    DialogInterface.OnMultiChoiceClickListener dialogObj2;
    DialogInterface.OnClickListener dialogOkObj;
    DialogInterface.OnClickListener dialogCancelObj;
    DatePickerDialog.OnDateSetListener dateListener;
    
    public QBaseDialog(Context context, Activity activity) {
    	mContext = context;
    	mActivity = activity;
    	
        dialogTitle = context.getResources().getString(R.string.dialog_title);
        dialogTxt = context.getResources().getString(R.string.dialog_txt);
        dialogTxt2 = context.getResources().getString(R.string.dialog_txt);
    }
    /*
     * USER METHODS
     */
    
    public int getSelectedIndex() {
    	return selectedIndex;
    }

    public void setSingleListDialogParam(int icon, int title, final CharSequence[] array, DialogInterface.OnClickListener obj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogArray = array;
    	this.dialogObj = obj;
    }
    public void setSingleChoiceDialogParam(int icon, int title, final CharSequence[] array, DialogInterface.OnClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogArray = array;
    	this.dialogObj = obj;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }
    public void setSingleChoiceDialogParam(int icon, int title, ArrayList<String> array, DialogInterface.OnClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogArray = new String[array.size()];
    	//String s;
    	for (int i=0;i<array.size(); i++) {
    		this.dialogArray[i] =  array.get(i);
    	}
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	//this.dialogArray = array;
    	this.dialogObj = obj;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }
    public void setSingleChoiceDialogParam(int icon, int title, final CharSequence[] array, int selectedIndex, DialogInterface.OnClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.selectedIndex = selectedIndex;
    	setSingleChoiceDialogParam(icon, title, array, obj, okObj, cancelObj);
    }
    public void setSingleChoiceDialogParam(int icon, int title, int array, int selectedIndex, DialogInterface.OnClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.selectedIndex = selectedIndex;
    	setSingleChoiceDialogParam(icon, title, array, obj, okObj, cancelObj);
    }
    public void setSingleChoiceDialogParam(int icon, int title, int array, DialogInterface.OnClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	CharSequence[] array1 = mContext.getResources().getStringArray(array);
    	setSingleChoiceDialogParam(icon, title, array1, obj, okObj, cancelObj);
    }
    
    public void setTxtDialogParam(int icon, int title, DialogInterface.OnClickListener okObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = null;
    }   
    
    public void setTxtDialogParam(int icon, String title, DialogInterface.OnClickListener okObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = title;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = null;
    } 
    
    public void setTxtDialogParam(int icon, int title, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    } 
    public void setTxtDialogParam(int icon, String title, String content, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = title;
    	this.dialogTxt = content;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }   

    public void setTxtDialogParam(int icon, int title, String content, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogTxt = content;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }   
    public void setTxtDialogParam(int icon, int title, String content,String content2, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogTxt = content;
    	this.dialogTxt2 = content2;
    	
    	this.dialogVal = null;
    	this.dialogVal2 = null;

    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }  
    public void setTxtDialogParam(int icon, int title, String content,String content2, String hit, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogTxt = content;
    	this.dialogTxt2 = content2;
    	
    	this.dialogVal = null;
    	this.dialogVal2 = null;
    	this.hitTxt = hit;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }  
    

    public void setTxtDialogParam2(int icon, int title, String label1,String label2, String content,String content2, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogTxt = label1;
    	this.dialogTxt2 = label2;

    	this.dialogVal = content;
    	this.dialogVal2 = content2;
    	
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    } 
    public void setTxtDialogParam2(int icon, int title, String label1,String label2, String label3, String content,String content2, String content3, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogTxt = label1;
    	this.dialogTxt2 = label2;
    	this.dialogTxt3 = label3;

    	this.dialogVal = content;
    	this.dialogVal2 = content2;
    	this.dialogVal3 = content3;
    	
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }   
    
    public void setDateDialogParam(int icon, int title, DatePickerDialog.OnDateSetListener dateListener, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dateListener = dateListener;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    } 
    
    public void setDateDialogParam(int icon, int title, int year, int month, int day, DatePickerDialog.OnDateSetListener dateListener, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	setDateDialogParam(icon, title, dateListener, okObj, cancelObj);
    	this.dateYear = year;
    	this.dateMonth = month;
    	this.dateDay = day;
    } 
    
    public void setDialogParamMultiple(int icon, int title, int array,boolean[] arrayInit,  DialogInterface.OnMultiChoiceClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	CharSequence[] array1 = mContext.getResources().getStringArray(array);
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogArray = array1;
    	this.dialogArrayinit = arrayInit;

    	this.dialogObj2 = obj;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }
    public void setDialogParamMultiple(int icon, int title, CharSequence[] array1,boolean[] arrayInit,  DialogInterface.OnMultiChoiceClickListener obj, DialogInterface.OnClickListener okObj, DialogInterface.OnClickListener cancelObj) {
    	this.dialogIcon = icon;
    	this.dialogTitle = mContext.getResources().getString(title);
    	this.dialogArray = array1;
    	this.dialogArrayinit = arrayInit;

    	this.dialogObj2 = obj;
    	this.dialogOkObj = okObj;
    	this.dialogCancelObj = cancelObj;
    }
    
    
    @TargetApi(5)
	public Dialog onCreateDialog(int id) {
    	if (id>DIALOG_BTN_ENTRY1) {
	            LayoutInflater factory = LayoutInflater.from(mContext);
	            final View textEntryView = factory.inflate(R.layout.opt_prompt4, null);
	            TextView title1 = (TextView)textEntryView.findViewById(R.id.title1);
	            TextView title2 = (TextView)textEntryView.findViewById(R.id.title2);
	            TextView title3 = (TextView)textEntryView.findViewById(R.id.title3);
	
	            if (this.dialogTxt!=null && !this.dialogTxt.equals("")) {
	            	title1.setText(this.dialogTxt);
	            } else {
	            	title1.setVisibility(View.GONE);
	            }
	            
	            if (this.dialogTxt2!=null && !this.dialogTxt2.equals("")) {
	            	title2.setText(this.dialogTxt2);
	            } else {
	            	title2.setVisibility(View.GONE);
	            }
	            
	            if (this.dialogTxt3!=null && !this.dialogTxt3.equals("")) {
            		title3.setText(this.dialogTxt3);
	            } else {
	            	title3.setVisibility(View.GONE);
	            }
	            
	            Button val1 = (Button)textEntryView.findViewById(R.id.editText_prompt1);
	            val1.setText(this.dialogVal);
	            
	            Button val2 = (Button)textEntryView.findViewById(R.id.editText_prompt2);
	            if (this.dialogVal2==null || this.dialogVal2.equals("")) {
	            	val2.setVisibility(View.GONE);
	            } else {
	            	val2.setText(this.dialogVal2);
	            }
	
	            Button val3 = (Button)textEntryView.findViewById(R.id.editText_prompt3);
	            if (this.dialogVal3==null || this.dialogVal3.equals("")) {
	            	val3.setVisibility(View.GONE);
	            } else {
	            	val3.setText(this.dialogVal3);
	            }
            //Button val3 = (Button)textEntryView.findViewById(R.id.editText_prompt3);
            
            Dialog dia = new AlertDialog.Builder(mContext)
                .setIcon(this.dialogIcon)
                .setTitle(this.dialogTitle)
                .setView(textEntryView)
                .setPositiveButton(R.string.alert_dialog_exit, this.dialogOkObj)
                .setNegativeButton(R.string.alert_dialog_no, this.dialogCancelObj)
                .create();
            
            
            return dia;
            
    	} else if (id>DIALOG_TEXT_ENTRY3) {
			AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
	        alert.setTitle(this.dialogTitle).setMessage(this.dialogTxt)
	        	.setPositiveButton(mContext.getString(R.string.promote_ok), this.dialogOkObj).setNegativeButton(mContext.getString(R.string.promote_cancel),this.dialogCancelObj);
	            alert.create().show();

            
    	} else if (id>DIALOG_TEXT_ENTRY2) {
            // This example shows how to add a custom layout to an AlertDialog
            LayoutInflater factory = LayoutInflater.from(mContext);
            final View textEntryView = factory.inflate(R.layout.opt_prompt2, null);
            TextView title1 = (TextView)textEntryView.findViewById(R.id.title1);
            TextView title2 = (TextView)textEntryView.findViewById(R.id.title2);
            title1.setText(this.dialogTxt);
            title2.setText(this.dialogTxt2);
            
            EditText val1 = (EditText)textEntryView.findViewById(R.id.editText_prompt1);
            EditText val2 = (EditText)textEntryView.findViewById(R.id.editText_prompt2);
            if (this.dialogVal!= null) {
            	val1.setText(this.dialogVal);
            } 
            if (this.dialogVal2!=null) {
            	val2.setText(this.dialogVal2);

            }
            
            return new AlertDialog.Builder(mContext)
                .setIcon(this.dialogIcon)
                .setTitle(this.dialogTitle)
                .setView(textEntryView)
                .setPositiveButton(R.string.promote_ok, this.dialogOkObj)
                .setNegativeButton(R.string.alert_dialog_cancel, this.dialogCancelObj)
                .create();
    	} else if (id>DIALOG_DATE_ENTRY) {
        	Calendar calendar = Calendar.getInstance();
        	DatePickerDialog dialog = new DatePickerDialog(mContext,  
                    this.dateListener,  
                    this.dateYear!=0?this.dateYear:calendar.get(Calendar.YEAR),  
                    this.dateMonth!=0?this.dateMonth:calendar.get(Calendar.MONTH),  
                    this.dateDay!=0?this.dateDay:calendar.get(Calendar.DAY_OF_MONTH)); 
        	dialog.setTitle(this.dialogTitle);
        	
        	dialog.show();
        	return dialog;
    	} else if (id>DIALOG_MULTIPLE_CHOICE_CURSOR) {
            String[] projection = new String[] {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.SEND_TO_VOICEMAIL
            };
            @SuppressWarnings("deprecation")
			Cursor cursor = mActivity.managedQuery(ContactsContract.Contacts.CONTENT_URI,
                    projection, null, null, null);
            return new AlertDialog.Builder(mContext)
                .setIcon(R.drawable.ic_popup_reminder)
                .setTitle(R.string.alert_dialog_multi_choice_cursor)
                .setMultiChoiceItems(cursor,
                        ContactsContract.Contacts.SEND_TO_VOICEMAIL,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton,
                                    boolean isChecked) {
                                Toast.makeText(mContext,
                                        "Readonly Demo Only - Data will not be updated",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
               .create();
    	} else if (id>DIALOG_TEXT_ENTRY) {
            // This example shows how to add a custom layout to an AlertDialog
            LayoutInflater factory = LayoutInflater.from(mContext);
            final View textEntryView = factory.inflate(R.layout.opt_prompt, null);
            EditText textEntry = (EditText)textEntryView.findViewById(R.id.editText_prompt);
            if (this.hitTxt!=null) {
                textEntry.setHint(this.hitTxt);

            } else {
                textEntry.setText(this.dialogTxt);

            }
            return new AlertDialog.Builder(mContext)
                .setIcon(this.dialogIcon)
                .setTitle(this.dialogTitle)
                .setView(textEntryView)
                .setPositiveButton(R.string.promote_ok, this.dialogOkObj)
                .setNegativeButton(R.string.alert_dialog_cancel, this.dialogCancelObj)
                .create();
    	} else if (id>DIALOG_MULTIPLE_CHOICE) {
    		if (this.dialogCancelObj == null) {
                return new AlertDialog.Builder(mContext)
                .setIcon(this.dialogIcon)
                .setTitle(this.dialogTitle)
                .setMultiChoiceItems(this.dialogArray,this.dialogArrayinit, this.dialogObj2)
                .setPositiveButton(R.string.alert_dialog_close,
                        this.dialogOkObj)
               .create();
    		} else {
	            return new AlertDialog.Builder(mContext)
	            .setIcon(this.dialogIcon)
	            .setTitle(this.dialogTitle)
	            .setMultiChoiceItems(this.dialogArray,this.dialogArrayinit, this.dialogObj2)
	            .setPositiveButton(R.string.promote_ok,
	                    this.dialogOkObj)
	            .setNegativeButton(R.string.alert_dialog_cancel,
	                    this.dialogCancelObj)
	           .create();
    		}
    	} else if (id>DIALOG_SINGLE_CHOICE) {
    		if (dialogCancelObj!=null) {
	            return new AlertDialog.Builder(mContext)
	            .setIcon(this.dialogIcon)
	            .setTitle(this.dialogTitle)
	            .setSingleChoiceItems(this.dialogArray, this.selectedIndex, dialogObj)
	            .setPositiveButton(R.string.promote_ok, dialogOkObj)
	            .setNegativeButton(R.string.alert_dialog_cancel, dialogCancelObj)
	           .create();
    		} else {
                return new AlertDialog.Builder(mContext)
                .setIcon(this.dialogIcon)
                .setTitle(this.dialogTitle)
                .setSingleChoiceItems(this.dialogArray, this.selectedIndex, dialogObj)
                .setPositiveButton(R.string.alert_dialog_close, dialogOkObj)
               .create();
    		}
            
    	} else if (id>DIALOG_SINGLE_LIST) {

            return new AlertDialog.Builder(mContext)
            .setIcon(this.dialogIcon)
            .setTitle(this.dialogTitle)
            .setItems(this.dialogArray, dialogObj)
            //.setPositiveButton(R.string.promote_ok, dialogOkObj)
            //.setNegativeButton(R.string.alert_dialog_cancel, dialogCancelObj)
           .create();

            
    	}  else if (id>DIALOG_PROGRESS) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setIcon(R.drawable.alert_dialog_icon);
            mProgressDialog.setTitle(R.string.select_dialog);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(MAX_PROGRESS);
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
            		mContext.getResources().getText(R.string.alert_dialog_hide), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked Yes so do some stuff */
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
            		mContext.getResources().getText(R.string.alert_dialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked No so do some stuff */
                }
            });
            return mProgressDialog;
    	} else if (id>DIALOG_LIST) {
            final String[] items = (String[]) this.dialogArray;

            return new AlertDialog.Builder(mContext)
            .setTitle(R.string.select_dialog)
            .setItems(this.dialogArray, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    /* User clicked so do some stuff */
                    new AlertDialog.Builder(mContext)
                            .setMessage("You selected: " + which + " , " + items[which])
                            .show();
                }
            })
            .create();
    	} else if (id>DIALOG_YES_NO_LONG_MESSAGE) {
            return new AlertDialog.Builder(mContext)
        	.setIcon(this.dialogIcon)
        	.setTitle(this.dialogTitle)
            .setMessage(this.dialogTxt)
            .setPositiveButton(R.string.promote_ok, this.dialogOkObj)
            .setNegativeButton(R.string.alert_dialog_cancel, this.dialogCancelObj)
            .create();
    	} else if (id>DIALOG_YES_NO_MESSAGE) {
            return new AlertDialog.Builder(mContext)
            .setIcon(this.dialogIcon)
            .setTitle(this.dialogTitle)
            .setPositiveButton(R.string.promote_ok, this.dialogOkObj)
            .setNegativeButton(R.string.alert_dialog_no, this.dialogCancelObj)
            .create();
    	} else if (id>DIALOG_NOTIFY_MESSAGE) {
            return new AlertDialog.Builder(mContext)
            .setIcon(this.dialogIcon)
            .setTitle(R.string.alert_dialog_notify)
            .setMessage(this.dialogTitle)
            .setPositiveButton(R.string.promote_ok, this.dialogOkObj)
            .create();
            
    	}  else if (id>=DIALOG_EXIT) {
            return new AlertDialog.Builder(mContext)
                .setIcon(this.dialogIcon)
                .setTitle(this.dialogTitle)
                .setPositiveButton(R.string.promote_ok, this.dialogOkObj)
                .setNegativeButton(R.string.alert_dialog_no, this.dialogCancelObj)
                .create();
    	}
    	return null;
    }

    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView(int)} to
     * describe what is to be displayed in the screen.
     */
    /*
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.alert_dialog);
                
        Button twoButtonsTitle = (Button) findViewById(R.id.two_buttons);
        twoButtonsTitle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_MESSAGE);
            }
        });
        
        Button twoButtons2Title = (Button) findViewById(R.id.two_buttons2);
        twoButtons2Title.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_YES_NO_LONG_MESSAGE);
            }
        });
        
        
        Button selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_LIST);
            }
        });
        
        Button progressButton = (Button) findViewById(R.id.progress_button);
        progressButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_PROGRESS);
                mProgress = 0;
                mProgressDialog.setProgress(0);
                mProgressHandler.sendEmptyMessage(0);
            }
        });
        
        Button radioButton = (Button) findViewById(R.id.radio_button);
        radioButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_SINGLE_CHOICE);
            }
        });
        
        Button checkBox = (Button) findViewById(R.id.checkbox_button);
        checkBox.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_MULTIPLE_CHOICE);
            }
        });
        
        Button checkBox2 = (Button) findViewById(R.id.checkbox_button2);
        checkBox2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_MULTIPLE_CHOICE_CURSOR);
            }
        });

        Button textEntry = (Button) findViewById(R.id.text_entry_button);
        textEntry.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_TEXT_ENTRY);
            }
        });
        
        mProgressHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mProgress >= MAX_PROGRESS) {
                    mProgressDialog.dismiss();
                } else {
                    mProgress++;
                    mProgressDialog.incrementProgressBy(1);
                    mProgressHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };
    }    */


    
	public long downloadVoiceFile(String downloadUrl, File saveFile) throws Exception {
		int downloadCount = 0;
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;
		             
		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;
		             
		try {
			URL url = new URL(downloadUrl);
		    httpConnection = (HttpURLConnection)url.openConnection();
		    httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
		    if (currentSize > 0) {
		    	httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
		     }
		     httpConnection.setConnectTimeout(10000);
		     httpConnection.setReadTimeout(20000);
		     updateTotalSize = httpConnection.getContentLength();
		     if (httpConnection.getResponseCode() == 404) {
		    	 throw new Exception("fail!");
		     }
		     is = httpConnection.getInputStream();                  
		     fos = new FileOutputStream(saveFile, false);
		     byte buffer[] = new byte[4096];
		     int readsize = 0;
		     while ((readsize = is.read(buffer)) > 0) {
		    	 fos.write(buffer, 0, readsize);
		         totalSize += readsize;
		         //为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
		         if ((downloadCount == 0)||(int) (totalSize*100/updateTotalSize)-10>downloadCount) {
		        	 downloadCount += 10;
		         }                       
		     }
		} finally {
			if (httpConnection != null) {
		    	httpConnection.disconnect();
		    }
		    if (is != null) {
		    	is.close();
		    }
		    if (fos != null) {
		    	fos.close();
		    }
		}
		return totalSize;
	}
	
	public void msgNotify(String dialogTitle,int dialogIcon, DialogInterface.OnClickListener dialogOkObj) {
		new AlertDialog.Builder(mContext)
        .setIcon(dialogIcon)
        .setTitle(dialogTitle)
        .setPositiveButton(R.string.promote_ok, dialogOkObj)
        .create().show();
	}
}
