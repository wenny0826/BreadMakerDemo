package com.wenny.breadmakerdemo;

import java.io.Serializable;

/**
 * Created by ${wenny} on 2017/6/10.
 */

public class FootPrintEntity implements Comparable<FootPrintEntity>,Serializable {

    private String id;
    private String imgTime;
    private String imgUrl;
    private String address;
    private String describe;//描述
    private String days;//第几天
    private boolean isShowDays = true;
    private long times;
    private int type;//1:文字 2:图片和文字

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isShowDays() {
        return isShowDays;
    }

    public void setShowDays(boolean showDays) {
        isShowDays = showDays;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
        if(times != 0){
            imgTime = TimeUtil.date2String(times, TimeUtil.DATE_FORMAT_2);
        }
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgTime() {
        return imgTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(FootPrintEntity footPrintEntity) {
        if((footPrintEntity.getTimes() - this.times) > 0){
            return -1;
        } else if((footPrintEntity.getTimes() - this.times)<0){
            return 0;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "FootPrintEntity{" +
                "id='" + id + '\'' +
                ", imgTime='" + imgTime + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", address='" + address + '\'' +
                ", days='" + days + '\'' +
                ", isShowDays=" + isShowDays +
                ", times=" + times +
                '}';
    }
}
