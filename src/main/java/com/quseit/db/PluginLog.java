package com.quseit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.quseit.config.CONF;
import com.quseit.lib.PluginInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/6/7.
 */

public class PluginLog {
    private Context context;
    private static final String TAG="PluginLog";
    private static final String PLUGIN_DATABASE_NAME="plugin.db";
    private static final String PLUGIN_DATABASE_TABLE = "plugin_info";
    private static final int PLUGIN_DATABASE_VERSION = 1;
    private static final String PLUGIN_DATABASE_CREATE="create table plugin_info("
            + "src char(255), ver int(3), name char(64) primary key, link char(255), dst char(255),"
            + "title char(32),desc text);";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_SRC= "src";
    public static final String KEY_VER = "ver";
    public static final String KEY_NAME = "name";
    public static final String KEY_LINK = "link";
    public static final String KEY_DST= "dst";
    public static final String KEY_TITLE= "title";
    public static final String KEY_DESC= "desc";




    public PluginLog(Context context){
        this.context=context;
    }

    public long insertPluginInfo(String src,int ver,String name,String link,String dst,String title,String desc){
        if (CONF.DEBUG)  Log.e(TAG, "insert a new info");
        PluginDataBaseHelper helper=new PluginDataBaseHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SRC, src);
        initialValues.put(KEY_VER, ver);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_LINK, link);
        initialValues.put(KEY_DST, dst);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_DESC, desc);
        long id =  db.insert(PLUGIN_DATABASE_TABLE, null, initialValues);
        db.close();
        helper.close();
        return id;
    }

    public List<PluginInfo> getPlugins(){
        ArrayList<PluginInfo> plugins=new ArrayList();
        PluginDataBaseHelper helper=new PluginDataBaseHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor c=db.rawQuery("select * from plugin_info",null);
        while (c.moveToNext()){
            plugins.add(new PluginInfo(c.getString(0),c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
        }
        c.close();
        db.close();
        helper.close();
        return plugins;
    }

    public boolean getPluginWithName(String name){
        PluginInfo plugin=null;
        if (TextUtils.isEmpty(name)){
            return false;
        }
        PluginDataBaseHelper helper=new PluginDataBaseHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor c=db.rawQuery("select * from plugin_info where name like ?",new String[]{name});
        if (c.moveToNext()) {
          plugin=new PluginInfo(c.getString(0),c.getInt(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
        }
        if (plugin!=null){
            return true;
        }else {
            return false;
        }
    }

    public void deletePlugin(String name){
        PluginDataBaseHelper helper=new PluginDataBaseHelper(context);
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(PLUGIN_DATABASE_TABLE,"name=?",new String[]{name});

        db.close();
        helper.close();
    }

    private static class PluginDataBaseHelper extends SQLiteOpenHelper{

        public PluginDataBaseHelper(Context context) {
            super(context, PLUGIN_DATABASE_NAME, null, PLUGIN_DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (CONF.DEBUG) Log.e(TAG, "db create"+PLUGIN_DATABASE_CREATE);
            db.execSQL(PLUGIN_DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS enc");
            onCreate(db);
        }
    }

}
