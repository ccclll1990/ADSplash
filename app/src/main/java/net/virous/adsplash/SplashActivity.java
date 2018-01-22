package net.virous.adsplash;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import net.virous.adsplash.config.BaseConfig;
import net.virous.adsplash.db.MyDb;
import net.virous.adsplash.modle.AdInfo;


public class SplashActivity extends Activity implements BaseConfig {


    Handler mHandler = new Handler();
    SplashHandler splashhandler;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        splashhandler = new SplashHandler();
        mHandler.postDelayed(splashhandler,2000);
        init();

    }

    private void init(){

        // 已经保存的json
        String json = MyDb.select(ADFILE);

        if (TextUtils.isEmpty(json)) {
            // 没有json return
            return;
        }

        // 是否已经下载完成
        String isDownload = MyDb.select(ISADDOWNLOAD);

        if (TextUtils.isEmpty(isDownload)) {
            // 未下载完成 return
            return;
        }

        // 解析
        AdInfo adInfo = JSON.parseObject(json,AdInfo.class);

        // 已经下载完成,并且需要显示祝福
        if (isDownload.equals("1") && adInfo != null && adInfo.isShow()) {
            mHandler.removeCallbacks(splashhandler);

            startActivity(new Intent(SplashActivity.this,ADActivity.class));
            SplashActivity.this.finish();
        }

    }

    class SplashHandler implements Runnable {

        public void run(){
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            SplashActivity.this.finish();

        }
    }
}
