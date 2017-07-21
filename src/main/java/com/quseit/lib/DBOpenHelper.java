package com.quseit.lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

	public DBOpenHelper(Context context) {
		super(context, "theanddownload.db", null,1);
	}

    @Override  
    public void onCreate(SQLiteDatabase db) {  
        db.execSQL("CREATE TABLE info(path VARCHAR(1024), thid INTEGER, done INTEGER, PRIMARY KEY(path, thid))");  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
    } 

}
