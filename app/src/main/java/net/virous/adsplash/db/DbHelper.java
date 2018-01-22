package net.virous.adsplash.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.virous.adsplash.config.DbConfig;


public class DbHelper extends SQLiteOpenHelper implements DbConfig {

    public static final String sql = "create table " + TABLE_CACHE + " (" + COL_NAME_ID + " integer primary key autoincrement , " //
            + COL_NAME_KEY + " text, "//
            + COL_NAME_VALUE + " text, "//
            + COL_NAME_REMARK + " text )";

    public DbHelper(Context context){
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //
        db.execSQL(sql);
        Log.e("db","建表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
