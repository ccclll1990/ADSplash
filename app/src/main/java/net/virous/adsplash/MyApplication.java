package net.virous.adsplash;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import net.virous.adsplash.config.BaseConfig;
import net.virous.adsplash.db.DatabaseManager;
import net.virous.adsplash.db.DbHelper;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @version V1.0
 * @Des
 * @FileName: net.virous.adsplash.MyApplication.java
 * @author: cl1
 * @date: 2018-01-22 15:52
 */
public class MyApplication extends Application implements BaseConfig {

    Context context;

    public static DisplayImageOptions adImgOption;

    @Override
    public void onCreate(){
        super.onCreate();

        context = this;

        initHttp();

        // 初始化数据库
        DatabaseManager.initialize(new DbHelper(context));

        initImageLoader();
    }

    private void initHttp(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new MyLoggerInterceptor("TAGG",false)) //
                //.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager) //设置可访问所有的https网站
                .connectTimeout(60000L,TimeUnit.MILLISECONDS)
                .readTimeout(60000L,TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader(){

        File imgCacheDir = StorageUtils.getOwnCacheDirectory(context,BASEFLODER + "/imgCache");

        int memoryCacheSize = (int)(Runtime.getRuntime().maxMemory() / 8);

        MemoryCache memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)//
                .memoryCacheExtraOptions(480,800) // max-width,max-height，即保存的每个缓存文件的最大长宽
                .diskCacheExtraOptions(720,1280,null)//
                .threadPoolSize(5)// 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//
                .denyCacheImageMultipleSizesInMemory()//
//                .memoryCache(new FIFOLimitedMemoryCache(50)) // You-can-pass-your-own
                // memory cache implementation/你可以通过自己的内存缓存实现
                // .memoryCacheSizePercentage(30)
                .memoryCache(memoryCache)//
                .tasksProcessingOrder(QueueProcessingType.LIFO)//
//                .diskCacheFileCount(500) // 缓存的文件数量
                .diskCache(new UnlimitedDiskCache(imgCacheDir)) //  自定义缓存路径
                .diskCacheSize(200 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())//
                .imageDownloader(new BaseImageDownloader(context,5 * 1000,30 * 1000)) // connectTimeout(5s),readTimeout(30s)超时时间
                // .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);// 全局初始化此配置


        adImgOption = new DisplayImageOptions.Builder()//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .resetViewBeforeLoading(true)//
                .imageScaleType(ImageScaleType.NONE)//
                .bitmapConfig(Bitmap.Config.ARGB_8888)//
                .build();//


    }

}
