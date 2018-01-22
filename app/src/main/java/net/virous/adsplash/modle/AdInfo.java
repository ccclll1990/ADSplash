package net.virous.adsplash.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSONObject;

/**
 * @version V1.0
 * @Des
 * @FileName: net.virous.adsplash.modle.AdInfo.java
 * @author: cl1
 * @date: 2018-01-16 12:10
 */
public class AdInfo extends BaseModel implements Parcelable {

    public static final int DEFATLT_DURATION = 3;

    public static final int SHOW_TYPE0 = 0; //0.图片 1.gif  2.视频
    public static final int SHOW_TYPE1 = 1;
    public static final int SHOW_TYPE2 = 2;

    public static final int TARGET_TYPE1 = 1; // 1.h5 2.原生
    public static final int TARGET_TYPE2 = 2;

    boolean isShow = false; //0.不展示  1.展示
    boolean isUseNormal = false; //是否使用通用的文件   0 不适用  1 使用
    int duration = DEFATLT_DURATION; //持续时间
    int showType = SHOW_TYPE0; //0.图片 1.gif  2.视频
    int targetType = TARGET_TYPE1; // 1.h5 2.原生
    String bgImg = ""; //背景图
    String normalShowUrl = ""; //通用展示的文件url
    String targetObj = ""; //url 或其他；  如果是原生  这个值还需商榷

    @Override
    public void fillThis(JSONObject json){

        if (json == null) {
            return;
        }
        if (json.containsKey("is_show")) {
            this.isShow = json2Boolean(json,"is_show",1);
        }
        if (json.containsKey("is_use_normal")) {
            this.isUseNormal = json2Boolean(json,"is_use_normal",1);
        }
        if (json.containsKey("duration")) {
            this.duration = json2Int(json,"duration",DEFATLT_DURATION);
        }
        if (json.containsKey("show_type")) {
            this.showType = json2Int(json,"show_type",SHOW_TYPE0);
        }
        if (json.containsKey("target_type")) {
            this.targetType = json2Int(json,"target_type",TARGET_TYPE1);
        }
        if (json.containsKey("show_bg_img")) {
            this.bgImg = json2String(json,"show_bg_img");
        }
        if (json.containsKey("normal_show_url")) {
            this.normalShowUrl = json2String(json,"normal_show_url");
        }
        if (json.containsKey("target_obj")) {
            this.targetObj = json2String(json,"target_obj");
        }
    }

    public AdInfo(JSONObject json){
        fillThis(json);
    }

    @Override
    public boolean isValid(){
        return isShow;
    }

    public AdInfo(){
    }

    @Override
    public String toString(){
        return "AdInfo{" +
                "isShow=" + isShow +
                ", isUseNormal=" + isUseNormal +
                ", duration=" + duration +
                ", showType=" + showType +
                ", targetType=" + targetType +
                ", bgImg='" + bgImg + '\'' +
                ", normalShowUrl='" + normalShowUrl + '\'' +
                ", targetObj='" + targetObj + '\'' +
                '}';
    }


    public boolean isShow(){
        return isShow;
    }

    public void setShow(boolean show){
        isShow = show;
    }

    public boolean isUseNormal(){
        return isUseNormal;
    }

    public void setUseNormal(boolean useNormal){
        isUseNormal = useNormal;
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public int getShowType(){
        return showType;
    }

    public void setShowType(int showType){
        this.showType = showType;
    }

    public int getTargetType(){
        return targetType;
    }

    public void setTargetType(int targetType){
        this.targetType = targetType;
    }

    public String getBgImg(){
        return bgImg;
    }

    public void setBgImg(String bgImg){
        this.bgImg = bgImg;
    }

    public String getNormalShowUrl(){
        return normalShowUrl;
    }

    public void setNormalShowUrl(String normalShowUrl){
        this.normalShowUrl = normalShowUrl;
    }

    public String getTargetObj(){
        return targetObj;
    }

    public void setTargetObj(String targetObj){
        this.targetObj = targetObj;
    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest,int flags){
        dest.writeByte(this.isShow ? (byte)1 : (byte)0);
        dest.writeByte(this.isUseNormal ? (byte)1 : (byte)0);
        dest.writeInt(this.duration);
        dest.writeInt(this.showType);
        dest.writeInt(this.targetType);
        dest.writeString(this.bgImg);
        dest.writeString(this.normalShowUrl);
        dest.writeString(this.targetObj);
    }

    protected AdInfo(Parcel in){
        this.isShow = in.readByte() != 0;
        this.isUseNormal = in.readByte() != 0;
        this.duration = in.readInt();
        this.showType = in.readInt();
        this.targetType = in.readInt();
        this.bgImg = in.readString();
        this.normalShowUrl = in.readString();
        this.targetObj = in.readString();
    }

    public static final Creator<AdInfo> CREATOR = new Creator<AdInfo>() {
        @Override
        public AdInfo createFromParcel(Parcel source){
            return new AdInfo(source);
        }

        @Override
        public AdInfo[] newArray(int size){
            return new AdInfo[size];
        }
    };
}
