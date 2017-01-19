package com.oraro.sunlon.vo;

/**
 * 视频Mark点信息
 *
 * @author 王子榕
 */
public class Mark {
    /**
     * 该视频mark点的id
     */
    private String id;
    /**
     * 该视频mark点的ip
     */
    private String ip;
    /**
     * 该视频mark点的是否在线可连接
     */
    private boolean isOnline;
    /**
     * 控件在fragment距离左侧的距离
     */
    private double xValue;
    /**
     * 控件在fragment距离顶部的距离
     */
    private double yValue;
    /**
     * 摄像头颜色分类
     */
    private int type;
    /**
     * 摄像头等级
     */
    private int level;

    /**
     * 摄像头标题
     */
    private String title;

    /**
     * 构造方法，默认视频设备不在线
     */
    public Mark() {
        this.isOnline = false                 ;
    }

    public double getyValue() {
        return yValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public double getxValue() {
        return xValue;
    }

    public void setxValue(double xValue) {
        this.xValue = xValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {

        String str = "Mark{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", isOnline=" + isOnline +
                ", xValue=" + xValue +
                ", yValue=" + yValue +
                ", type=" + type +
                ", level=" + level +
                ", title=" + title +
                '}';


        return str;
    }
}
