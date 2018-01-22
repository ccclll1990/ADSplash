package net.virous.adsplash.config;

/**
 * @version V1.0
 * @Des
 * @FileName: net.virous.adsplash.config.BaseConfig.java
 * @author: cl1
 * @date: 2018-01-19 18:10
 */
public interface BaseConfig {

    String ISADDOWNLOAD = "addownloadfinish";
    String ADFILE = "newYearGreeting";
    String PACKAGE_NAME = "net.virous.adsplash";


    String picJson = "{\n" +
            "        \"is_show\": \"1\", \n" +
            "        \"duration\": \"10\", \n" +
            "        \"show_type\": \"0\", \n" +
            "        \"is_use_normal\": \"1\", \n" +
            "        \"normal_show_url\": \"http://www.yishengdaojia.com:8084/common/p1.jpg\", \n" +
            "        \"target_type\": \"1\", \n" +
            "        \"target_obj\": \"http://www.baidu.com\"\n" +
            "}";

    String gifJson = "{\n" +
            "        \"is_show\": \"1\", \n" +
            "        \"duration\": \"10\", \n" +
            "        \"show_type\": \"1\", \n" +
            "        \"is_use_normal\": \"1\", \n" +
            "        \"normal_show_url\": \"http://www.yishengdaojia.com:8084/common/gif2.gif\", \n" +
            "        \"target_type\": \"1\", \n" +
            "        \"target_obj\": \"http://www.baidu.com\"\n" +
            "}";

    String videoJson = "{\n" +
            "        \"is_show\": \"1\", \n" +
            "        \"duration\": \"10\", \n" +
            "        \"show_type\": \"2\", \n" +
            "        \"is_use_normal\": \"1\", \n" +
            "        \"normal_show_url\": \"http://www.yishengdaojia.com:8084/common/v3.mp4\", \n" +
            "        \"target_type\": \"1\", \n" +
            "        \"target_obj\": \"http://www.baidu.com\"\n" +
            "}";
}
