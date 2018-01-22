package net.virous.adsplash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import net.virous.adsplash.db.MyDb;
import net.virous.adsplash.modle.AdInfo;
import net.virous.adsplash.view.CustomVideoView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ADActivity extends Activity {
    private static final String TAG = ADActivity.class.getSimpleName();
    AdInfo mAdInfo;
    @BindView(R.id.ad_img_iv)
    ImageView mAdImgIv;
    @BindView(R.id.ad_video_cvv)
    CustomVideoView mAdVideoCvv;
    @BindView(R.id.ad_gif_giv)
    GifImageView mAdGifGiv;
    @BindView(R.id.ad_content_rl)
    RelativeLayout mAdContentRl;
    @BindView(R.id.ad_skip_tv)
    TextView mAdSkipTv;
    @BindView(R.id.ad_skip_rl)
    RelativeLayout mAdSkipRl;
    @BindView(R.id.ad_main_rl)
    RelativeLayout mAdMainRl;

    private GradientDrawable splashADButtonBg = new GradientDrawable();
    int duration = 10;

    private Handler handler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run(){
            if (0 == duration) {
                goMain();
                return;
            } else {
                setDuration(duration);
            }
            handler.postDelayed(timerRunnable,1000);
        }
    };
    Context mContext;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);

        mContext = this;
        mAdInfo = getIntent().getParcelableExtra("AdInfo");
        init();
    }

    private void init(){
        splashADButtonBg.setShape(GradientDrawable.OVAL);
        splashADButtonBg.setColor(Color.parseColor("#66333333"));
        mAdSkipRl.setBackgroundDrawable(splashADButtonBg);
        mAdSkipTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);

        duration = mAdInfo.getDuration();

        if (mAdInfo.getShowType() == AdInfo.SHOW_TYPE0) {

            mAdImgIv.setVisibility(View.VISIBLE);

            mAdVideoCvv.setVisibility(View.GONE);
            mAdGifGiv.setVisibility(View.GONE);

            //  从缓存里图片
            String IMgPath = MyDb.select(mAdInfo.getNormalShowUrl());

            if (TextUtils.isEmpty(IMgPath)) {
                Log.e(TAG," 缓存里 没有 IMgPath " + IMgPath);
                goMain();
                return;
            }

            File file = new File(IMgPath);

            Log.e(TAG,"从缓存里取图片 file  " + file.getAbsolutePath());
            if (file != null && file.exists()) {
                //图片存在, 加载图片
                ImageLoader.getInstance().displayImage("file://" + file.getAbsolutePath(),mAdImgIv,MyApplication.adImgOption);
                handler.postDelayed(timerRunnable,1000);
            }


        } else if (mAdInfo.getShowType() == AdInfo.SHOW_TYPE1) {

            mAdImgIv.setVisibility(View.GONE);
            mAdVideoCvv.setVisibility(View.GONE);

            mAdGifGiv.setVisibility(View.VISIBLE);

            String gifPath = MyDb.select(mAdInfo.getNormalShowUrl());

            if (TextUtils.isEmpty(gifPath)) {
                Log.e(TAG," 缓存里 没有 gifPath " + gifPath);
                goMain();
                return;
            }

            File file = new File(gifPath);

            if (file != null && file.exists()) {

                try {
                    GifDrawable gifFromUri = new GifDrawable(gifPath);
                    mAdGifGiv.setImageDrawable(gifFromUri);
                    handler.postDelayed(timerRunnable,1000);
                } catch (IOException e) {
                    e.printStackTrace();
                    goMain();
                }

            } else {
                goMain();
            }


        } else if (mAdInfo.getShowType() == AdInfo.SHOW_TYPE2) {

            mAdImgIv.setVisibility(View.GONE);
            mAdGifGiv.setVisibility(View.GONE);
            mAdVideoCvv.setVisibility(View.VISIBLE);

            String videoPath = MyDb.select(mAdInfo.getNormalShowUrl());

            if (TextUtils.isEmpty(videoPath)) {
                Log.e(TAG," 缓存里 没有 videoPath " + videoPath);
                goMain();
                return;
            }
            File file = new File(videoPath);

            if (file != null && file.exists()) {

                //设置播放加载路径
                mAdVideoCvv.setVideoPath(videoPath);
                mAdVideoCvv.requestFocus();
                mAdVideoCvv.start();

                //循环播放
                mAdVideoCvv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer){
                        mAdVideoCvv.start();
                    }
                });
                handler.postDelayed(timerRunnable,1000);
            } else {
                goMain();
            }


        } else {
            goMain();
        }
    }

    private void setDuration(Integer d){
        d--;
        this.duration = d;
        mAdSkipTv.setText(String.format("跳过\n%d s",duration));
    }


    @OnClick({R.id.ad_content_rl,R.id.ad_skip_rl})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.ad_content_rl:
                Log.e(TAG,"点击 content ,   getTargetType " + mAdInfo.getTargetType());

                if (mAdInfo.getTargetType() == AdInfo.TARGET_TYPE1) {
                    handler.removeCallbacks(timerRunnable);
                    //    TODO 打开网页
                    Toast.makeText(ADActivity.this,"打开网页",Toast.LENGTH_LONG);

                } else if (mAdInfo.getTargetType() == AdInfo.TARGET_TYPE2) {
                    handler.removeCallbacks(timerRunnable);
                    // TODO 打开原生app
                } else {
                    goMain();
                }

                break;

            case R.id.ad_skip_rl:
                Log.e(TAG,"点击 kip_tv ");
                goMain();
                break;
        }
    }


    private void goMain(){

        handler.removeCallbacks(timerRunnable);
        startActivity(new Intent(this,MainActivity.class));

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();

    }
}
