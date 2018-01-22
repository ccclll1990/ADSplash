package net.virous.adsplash.modle;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import net.virous.adsplash.interfaces.BaseModelCallBack;

/**
 * @version V1.0
 * @Des
 * @FileName: com.yisheng.yonghu.model.BaseModel.java
 * @author: cl1
 * @date: 2017-04-19 12:10
 */
public abstract class BaseModel implements BaseModelCallBack {

    /**
     * 解析Json -> String
     *
     * @param json
     * @param jsonKey
     * @return
     */
    public static String json2String(JSONObject json,String jsonKey){
        return json2String(json,jsonKey,"");
    }

    /**
     * @param json
     * @param jsonKey
     * @param defaultValue
     * @return
     */
    public static String json2String(JSONObject json,String jsonKey,String defaultValue){
        if (json == null) {
            return "";
        }

        return TextUtils.isEmpty(json.getString(jsonKey)) ? defaultValue : json.getString(jsonKey);
    }

    /**
     * 解析Json -> boolean
     *
     * @param json
     * @param jsonKey
     * @return
     */
    public static boolean json2Boolean(JSONObject json,String jsonKey){
        return json2Boolean(json,jsonKey,1);
    }

    /**
     * 解析Json -> boolean
     *
     * @param json
     * @param jsonKey
     * @param trueKey
     * @return
     */
    public static boolean json2Boolean(JSONObject json,String jsonKey,int trueKey){
        if (json == null) {
            return false;
        }
        return !TextUtils.isEmpty(json.getString(jsonKey)) && Integer.parseInt(json.getString(jsonKey)) == trueKey;
    }

    /**
     * 解析Json -> int
     *
     * @param json
     * @param jsonKey
     * @return
     */
    public static int json2Int(JSONObject json,String jsonKey){
        return json2Int(json,jsonKey,0);
    }

    /**
     * 解析Json -> int
     *
     * @param json
     * @param jsonKey
     * @param defaultValue
     * @return
     */
    public static int json2Int(JSONObject json,String jsonKey,int defaultValue){
        if (json == null) {
            return defaultValue;
        }
        return TextUtils.isEmpty(json.getString(jsonKey)) ? defaultValue : Integer.parseInt(json.getString(jsonKey));
    }

    /**
     * 解析Json -> float
     *
     * @param json
     * @param jsonKey
     * @return
     */
    public static float json2Float(JSONObject json,String jsonKey){
        return json2Float(json,jsonKey,0f);
    }

    /**
     * 解析Json -> float
     *
     * @param json
     * @param jsonKey
     * @param defaultValue
     * @return
     */
    public static float json2Float(JSONObject json,String jsonKey,float defaultValue){
        if (json == null) {
            return defaultValue;
        }
        return TextUtils.isEmpty(json.getString(jsonKey)) ? defaultValue : Float.parseFloat(json.getString(jsonKey));
    }

    /**
     * 解析Json -> double
     *
     * @param json
     * @param jsonKey
     * @return
     */
    public static double json2Double(JSONObject json,String jsonKey){
        return json2Double(json,jsonKey,0);
    }

    /**
     * 解析Json -> double
     *
     * @param json
     * @param jsonKey
     * @param defaultValue
     * @return
     */
    public static double json2Double(JSONObject json,String jsonKey,double defaultValue){
        if (json == null) {
            return defaultValue;
        }
        return TextUtils.isEmpty(json.getString(jsonKey)) ? defaultValue : Double.parseDouble(json.getString(jsonKey));
    }

}
