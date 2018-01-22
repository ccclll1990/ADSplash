package net.virous.adsplash.config;

public interface DbConfig {
    String NAME = "adsplash.db";
    int VERSION = 1;
    String TABLE_CACHE = "datacache"; //

    String COL_NAME_ID = "id";
    String COL_NAME_KEY = "url";  //缓存的key
    String COL_NAME_VALUE = "json"; // value
    String COL_NAME_REMARK = "remark"; // remark
}
