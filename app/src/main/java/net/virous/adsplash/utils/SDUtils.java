package net.virous.adsplash.utils;

import android.os.Environment;

import net.virous.adsplash.config.BaseConfig;

/**
 * @version V1.0
 * @Des
 * @FileName: net.virous.adsplash.utils.SDUtils.java
 * @author: cl1
 * @date: 2018-01-22 11:06
 */
public class SDUtils {

    /**
     * 获取可用的存储空间
     *
     * @return
     */
    public static String getAvPath(){
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// sdcard
            String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            path = sdDir + "/adSplash/";
        } else {
            String romDir = Environment.getDataDirectory().getAbsolutePath();
            path = romDir + "/data/" + BaseConfig.PACKAGE_NAME + "/";
        }

        return path;
    }

}
