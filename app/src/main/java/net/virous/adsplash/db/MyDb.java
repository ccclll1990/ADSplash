package net.virous.adsplash.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import net.virous.adsplash.config.DbConfig;
import net.virous.adsplash.utils.MD5;


/**
 * 数据库操作类
 *
 * @author cl1
 */
public class MyDb implements DbConfig {

    /**
     * 插入数据
     *
     * @param mkey
     * @param mvalue
     * @param mremark 备注
     * @return 是否成功
     */
    public static boolean insert(String mkey,String mvalue,String mremark){
        SQLiteDatabase db = DatabaseManager.getinstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME_KEY,MD5.getMD5(mkey).toUpperCase());
        values.put(COL_NAME_VALUE,mvalue);
        if (mremark != null && !"".equals(mremark)) {
            values.put(COL_NAME_REMARK,mremark);
        }

        long l = db.insert(TABLE_CACHE,null,values);
        DatabaseManager.getinstance().closeDatabase();
        return l != -1;

    }

    /**
     * 删除数据
     *
     * @param mkey
     * @return 删除的条目数量
     */
    public static int delete(String mkey){
        SQLiteDatabase db = DatabaseManager.getinstance().openDatabase();
        String whereClause = " " + COL_NAME_KEY + " = ? ";
        String[] whereArgs = new String[]{MD5.getMD5(mkey).toUpperCase()};
        int i = db.delete(TABLE_CACHE,whereClause,whereArgs);
        DatabaseManager.getinstance().closeDatabase();
        return i;
    }

    /**
     * 修改数据
     *
     * @param mkey
     * @param mvalue
     * @param mremark
     * @return 修改的数量
     */
    public static int update(String mkey,String mvalue,String mremark){
        SQLiteDatabase db = DatabaseManager.getinstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME_KEY,MD5.getMD5(mkey).toUpperCase());
        values.put(COL_NAME_VALUE,mvalue);
        if (mremark != null && !"".equals(mremark)) {
            values.put(COL_NAME_REMARK,mremark);
        }

        String whereClause = " " + COL_NAME_KEY + " = ? ";
        String[] whereArgs = new String[]{MD5.getMD5(mkey).toUpperCase()};
        int i = db.update(TABLE_CACHE,values,whereClause,whereArgs);
        DatabaseManager.getinstance().closeDatabase();
        return i;
    }

    /**
     * 查询数据
     *
     * @param mkey
     * @return
     */
    public static String select(String mkey){
        SQLiteDatabase db = DatabaseManager.getinstance().openDatabase();
        String sql = "select " + COL_NAME_VALUE + " from " + TABLE_CACHE + " where " + COL_NAME_KEY + " = ? ";
        String[] selectionArgs = new String[]{MD5.getMD5(mkey).toUpperCase()};
        Cursor c = db.rawQuery(sql,selectionArgs);
        try {
            String jsonStr = null;
            if (c != null && c.moveToFirst()) {
                jsonStr = c.getString(c.getColumnIndex(COL_NAME_VALUE));
            }
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            c.close();
            DatabaseManager.getinstance().closeDatabase();
        }
    }

    /**
     * 插入或者修改
     *
     * @param mkey
     * @param mvalue
     * @param mremark
     */
    public static void insertOrUpdate(String mkey,String mvalue,String mremark){
        if (!TextUtils.isEmpty(select(mkey))) {
            update(mkey,mvalue,mremark);
        } else {
            insert(mkey,mvalue,mremark);
        }

    }

}
