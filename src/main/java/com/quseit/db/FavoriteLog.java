package com.quseit.db;
import java.util.ArrayList;

import com.quseit.config.CONF;
import com.quseit.util.DateTimeHelper;
import com.quseit.util.VeDate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class FavoriteLog {
	private static final String TAG = "FavLog";

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITLE = "m_title";  
    public static final String KEY_ARTIST = "m_artist";   
    public static final String KEY_catelog = "m_catelog";   

    public static final String KEY_TYPE = "m_type";    
    public static final String KEY_PATH = "m_path";    
    public static final String KEY_TIME = "m_time";
    public static final String KEY_TIME2 = "m_time2";

    public static final String KEY_DESC = "m_desc";
    public static final String KEY_THUMBNAIL = "m_thumbnail";
    
    public static final String KEY_STAT = "stat";

    private static final String DATABASE_NAME = "favlog.db";
    private static final String DATABASE_TABLE = "favlog";
    private static final int DATABASE_VERSION = 1;
    
    // 包括创建titles表的SQL语句
    private static final String DATABASE_CREATE1 = "create table favlog (_id integer primary key autoincrement, "
            + "m_title char(64), m_artist char(64), m_catelog char(64), m_type char(32), m_path char(255), "
    		+ "m_desc text, m_thumbnail char(255),"
    		+ "m_time date,m_time2 date,"
            + "stat int(1));";
    
    /*private static final String DATABASE_CREATE2 = "create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
    		+ "start_pos integer, end_pos integer, compelete_size integer,url char)";
	*/
    private final Context context;
 
    public FavoriteLog(Context ctx) {
        this.context = ctx;
        
    	//Log.d(TAG, "db init");
    }
 
    // 扩展SQLiteOpenHelper类，用于数据库创建和版本管理。可以覆盖onCreate和onUpgrade犯法
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        // 创建一个新的数据库
        @Override
        public void onCreate(SQLiteDatabase db) {
        	if (CONF.DEBUG) Log.d(TAG, "db create"+DATABASE_CREATE1);
            db.execSQL(DATABASE_CREATE1);
            //db.execSQL(DATABASE_CREATE2);

        }
 
        // 用于升级数据库，可以通过检查DATABASE_VERSION常量定义的值来实现，对于onUpgrader()
        // 方法而言，只不过是简单地删除表，然后再创建表
        // 定义不同的方法来打开和关闭数据库，添加/编辑/删除/行的函数
        // 定义打开和关闭数据库以及增加/编辑/删除表中行的方法
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS enc");
            onCreate(db);
        }
    }
 
    // ---打开数据库---
    /*public FavoriteLog open() throws SQLException {
    	if (CONF.DEBUG) Log.d(TAG, "db open");
        db = DBHelper.getWritableDatabase();
        return this;
    }*/
 
    // ---关闭数据库---
   /* public void close() {
    	if (CONF.DEBUG) Log.d(TAG, "db close");
        DBHelper.close();
    }*/
    
    /*
     * 获得最后需要播放的歌曲
     */
    public String[] getLogByPath(String path) { 
    	if (CONF.DEBUG) Log.d(TAG, "getLastNewLog");
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
                KEY_ROWID, KEY_TITLE, KEY_ARTIST, KEY_TYPE, KEY_TIME, KEY_TIME2  }, 
                KEY_PATH+ "='"+path+"'", null, null, null, KEY_ROWID+" ASC", "1");
        
        if (mCursor.getCount()!=0) {
            mCursor.moveToFirst();
            String id = mCursor.getString(0);
            String title = mCursor.getString(1);
            String artist = mCursor.getString(2);
            String type = mCursor.getString(3);

            String[] ret = {id, title, artist, type};
            mCursor.close();
            db.close();
            
            DBHelper.close();
            return ret;
        } else {
            db.close();
            
            DBHelper.close();
        	return null;
        }    	
    }
    public String[] getLogById(String rowId) { 
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	if (CONF.DEBUG) Log.d(TAG, "getLastNewLog");
    	SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
                KEY_ROWID, KEY_TITLE, KEY_ARTIST, KEY_PATH, KEY_TYPE, KEY_TIME, KEY_TIME2, KEY_STAT }, 
                KEY_ROWID+ "='"+rowId+"'", null, null, null, KEY_ROWID+" ASC", "1");
        
        if (mCursor.getCount()!=0) {
            mCursor.moveToFirst();
            String id = mCursor.getString(0);
            String title = mCursor.getString(1);
            String artist = mCursor.getString(2);
            String path = mCursor.getString(3);

            String[] ret = {id, title, artist, path};
            mCursor.close();
            db.close();
            
            DBHelper.close();
            return ret;
        } else {
            db.close();

            DBHelper.close();
        	return null;
        }    	
    }
    public boolean checkIfLogExists(String path) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
                KEY_ROWID, KEY_TITLE, KEY_ARTIST, KEY_PATH, KEY_TYPE, KEY_TIME, KEY_TIME2, KEY_STAT }, 
                KEY_PATH+ "='"+path+"' AND stat=0", null, null, null, KEY_ROWID+" ASC", "1");
        
        if (mCursor.getCount()!=0) {
        	//if (closeDb)
    		db.close();
    		DBHelper.close();
        	if (CONF.DEBUG) Log.d(TAG, "checkLogPlayedByPath-yes:"+path);

        	return true;
        } else {
        	//if (closeDb)
    		db.close();
    		DBHelper.close();
        	if (CONF.DEBUG) Log.d(TAG, "checkLogPlayedByPath-no:"+path);

        	return false;
        }    	
    }
    
    public String[] getLastNewLog() { 
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	if (CONF.DEBUG) Log.d(TAG, "getLastNewLog");
    	SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor mCursor = db.query(DATABASE_TABLE, new String[] {
                KEY_ROWID, KEY_TITLE, KEY_ARTIST, KEY_PATH, KEY_TYPE, KEY_TIME, KEY_TIME2, KEY_STAT }, 
                KEY_STAT+ "='0'", null, null, null, KEY_ROWID+" ASC", "1");
        
        if (mCursor.getCount()!=0) {
            mCursor.moveToFirst();
            String id = mCursor.getString(0);
            String title = mCursor.getString(1);
            String artist = mCursor.getString(2);
            String path = mCursor.getString(3);

            String[] ret = {id, title, artist, path};
            mCursor.close();
            db.close();
            
            DBHelper.close();
            return ret;
        } else {
            db.close();
            DBHelper.close();

        	return null;
        }    	
    }
    /*
     *  更新
     */
    public boolean stopAllLog() {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_STAT, "21"); 
        args.put(KEY_TIME2, VeDate.getStringDate()); 

        boolean ret = (db.update(DATABASE_TABLE, args, KEY_STAT+"=0", null) > 0);
        DBHelper.close();
        db.close();
        
        return ret;
    }
    
    public boolean updateLogType(long rowId, String type) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	if (CONF.DEBUG)  Log.d(TAG, "updateLogStat:"+rowId);

    	SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_TYPE, type); 
        args.put(KEY_TIME2, VeDate.getStringDate()); 

        boolean ret = (db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0);
        db.close();
        DBHelper.close();
        return ret;
    }      
    public boolean updateLogStat(long rowId, String stat) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	if (CONF.DEBUG)  Log.d(TAG, "updateLogStat:"+rowId);

    	SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(KEY_STAT, stat); 
        args.put(KEY_TIME2, VeDate.getStringDate()); 

        boolean ret = (db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0);
        db.close();
        DBHelper.close();
        
        return ret;
    }
    
    public boolean deleteLog(long rowId) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	if (CONF.DEBUG)  Log.d(TAG, "deleLog:"+rowId);

    	SQLiteDatabase db = DBHelper.getWritableDatabase();
        //ContentValues args = new ContentValues();

        boolean ret = (db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0);
        db.close();
        DBHelper.close();
        
        return ret;
    }
    
    // 插入信息
    public long insertNewLog(String title, String artist, String catelog, String type, String path, String desc, String thumbnail) {
    	if (CONF.DEBUG)  Log.d(TAG, "insertNewLog:"+title+"-type:"+type+"-path:"+path);
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	try {
	    	SQLiteDatabase db = DBHelper.getWritableDatabase();
	
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_TITLE, title);
	        initialValues.put(KEY_ARTIST, artist);
	        initialValues.put(KEY_catelog, catelog);
	
	        initialValues.put(KEY_TYPE, type);
	        initialValues.put(KEY_PATH, path);

	        initialValues.put(KEY_DESC, desc);
	        initialValues.put(KEY_THUMBNAIL, thumbnail);
	        
	        String now = VeDate.getStringDate();
	        initialValues.put(KEY_TIME, now);
	        initialValues.put(KEY_STAT, "0");
	        
	        long id =  db.insert(DATABASE_TABLE, null, initialValues);    	
	        db.close();
	        DBHelper.close();

	        return id;
    	} catch (SQLiteException e) {
    		Log.e(TAG, "insertNewLog exception:"+e.getMessage());
            DBHelper.close();

    		return 0;
    	}
    }
    
    // 获取所有的信息
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<String[]> getLogs() {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	SQLiteDatabase db = DBHelper.getReadableDatabase();
    	Cursor result;
    	result = db.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_TITLE, KEY_ARTIST,KEY_TYPE, KEY_PATH, KEY_TIME,
    			KEY_TIME2, KEY_STAT,KEY_DESC,KEY_THUMBNAIL,KEY_catelog }, KEY_STAT + "=" + "0", null, null, null, KEY_TIME+" DESC", null);   
	
	    ArrayList<String[]> feedsa = new ArrayList();
		//String feeds[] = new String[result.getCount()];
		//int i=0;
		if (result.getCount()!=0) {
			result.moveToFirst();
			do {

				String id = result.getString(0);
				String title = result.getString(1);
				String artist = result.getString(2);
				

				String type = result.getString(3);
				String path = result.getString(4);
	
				String time = result.getString(5);
				//String time2 = result.getString(6);
				//String stat = result.getString(7);
				String desc = result.getString(8);
				String thumbnail = result.getString(9);
				String cat = result.getString(10);

				String[] f = new String[9];
				f[0] = id;
				f[1] = title;
				f[2] = artist;
				f[3] = type;
				f[4] = time;
				f[5] = cat;
				f[6] = path;
				f[7] = thumbnail;
				f[8] = desc;
				feedsa.add(f);

				//i++;

			} while (result.moveToNext());
		}
		result.close();
		Log.d(TAG, "getLogs:"+feedsa.size());

		db.close();
		DBHelper.close();
		return feedsa;
    }
    
    public int getAllLogToday() {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	SQLiteDatabase db = DBHelper.getReadableDatabase();
    	
        Cursor result = db.query(DATABASE_TABLE, new String[] { KEY_ROWID }, KEY_TIME+">='"+DateTimeHelper.getTodayFull()+"'", null, null, null, null);
        int total = result.getCount();
        result.close();
    	db.close();
    	DBHelper.close();
    	return total;
    }
    
    public int getNewLogCount() {
        DatabaseHelper DBHelper = new DatabaseHelper(context);

    	SQLiteDatabase db = DBHelper.getReadableDatabase();
    	
        Cursor result = db.query(DATABASE_TABLE, new String[] { KEY_ROWID }, KEY_STAT + "=" + "0", null, null, null, null);
        int total = result.getCount();
        result.close();
    	db.close();
    	DBHelper.close();
    	return total;
    }
}
