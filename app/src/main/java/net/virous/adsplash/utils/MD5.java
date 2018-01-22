package net.virous.adsplash.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private MD5(){
    }

    /**
     * md5加密
     *
     * @param val
     * @return
     */
    public static String getMD5(String val){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e(null,e.getMessage(),e);
        }
        md5.update(val.getBytes());
        byte[] m = md5.digest();// 加密
        return getString(m);
    }

    private static String getString(byte[] b){
        StringBuilder buf = new StringBuilder("");
        int i = 0;
        for (byte aB : b) {
            i = aB;
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString().toUpperCase();
    }
}
