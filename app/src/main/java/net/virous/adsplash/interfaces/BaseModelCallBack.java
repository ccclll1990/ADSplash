package net.virous.adsplash.interfaces;

import com.alibaba.fastjson.JSONObject;

public interface BaseModelCallBack {

    void fillThis(JSONObject json);

    boolean isValid();

}
