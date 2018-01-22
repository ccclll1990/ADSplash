package net.virous.adsplash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import net.virous.adsplash.config.BaseConfig;
import net.virous.adsplash.db.MyDb;
import net.virous.adsplash.modle.AdInfo;
import net.virous.adsplash.utils.SDUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;

public class MainActivity extends Activity implements BaseConfig {

    private static final String TAG = MainActivity.class.getSimpleName();
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    @SuppressLint("StaticFieldLeak")
    private void init(){

        list.add(picJson);
        list.add(gifJson);
        list.add(videoJson);

        // 请求接口下载文件

        GetJsonDataTask getJsonDataTask = new GetJsonDataTask();
        getJsonDataTask.execute();

    }


    AdInfo adInfo;

    class GetJsonDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids){

            try {
                // 模拟请求 接口
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 随机返回一个种类的json
            Random random = new Random(1024);

            int r = Math.abs(random.nextInt()) % 3;
            Log.e(TAG,"random " + r);

            return list.get(r);
        }

        @Override
        protected void onPostExecute(String json){
            super.onPostExecute(json);

            Log.e(TAG,"请求的json" + json);

            // 解析数据
            JSONObject jsonObject = JSON.parseObject(json);
            adInfo = new AdInfo(jsonObject);

            if (!adInfo.isShow()) {
                Log.e(TAG,"终于没广告了");
                return;
            }

            String j = JSONObject.toJSONString(adInfo);

            MyDb.insertOrUpdate(ADFILE,j,"广告文件");

            String filePath = SDUtils.getAvPath();
            String fileName = new File(adInfo.getNormalShowUrl()).getName();

            File file = new File(filePath + fileName);
            if (file != null && file.exists()) {
                file.delete();
                Log.e(TAG," 已经存在了  ");
            } else {

                file.mkdir();

                Log.e(TAG,"准备开始下载 ");
            }

            OkHttpUtils.get().url(adInfo.getNormalShowUrl()).build().execute(new FileCallBack(filePath,fileName) {

                @Override
                public void inProgress(float progress,long total,int id){

                    Log.e(TAG,"onLoading   progress " + progress * 100 + "%");

                }

                @Override
                public void onError(Call call,Exception e,int id){
                    Log.e(TAG,"下载失败 " + e.getMessage() + " \\n  call:  " + call.toString());
                    e.printStackTrace();

                }

                @Override
                public void onResponse(File response,int id){
                    Log.e(TAG,"下载成功 ");

                    // 保存下载地址
                    MyDb.insertOrUpdate(adInfo.getNormalShowUrl(),response.getAbsolutePath(),"");

                    // 保存下载完成记录
                    MyDb.insert(ISADDOWNLOAD,"1","下载广告");
                }
            });
        }
    }
}
