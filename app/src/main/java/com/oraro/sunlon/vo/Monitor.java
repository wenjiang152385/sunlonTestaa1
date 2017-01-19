package com.oraro.sunlon.vo;

/**
 * 网络请求设备的实体对象
 * @author 王子榕
 */
public class Monitor {
    //":[{"bid":"1234560305","sip":"101.231.52.58","tit":"岗亭内科达半球","ste":"1"},
    /**设备id*/
    private String bid;
    /**设备ip地址*/
    private String sip;
    /**设备信息*/
    private String tit;
    /**设备是否可用*/
    private Integer ste;
    /**设备信息型号*/
    private Integer tpe;

    public Integer getTpe() {
        return tpe;
    }

    public void setTpe(Integer tpe) {
        this.tpe = tpe;
    }



    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public Integer getSte() {
        return ste;
    }

    public void setSte(Integer ste) {
        this.ste = ste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monitor monitor = (Monitor) o;
        return bid.equals(monitor.bid);

    }

    @Override
    public int hashCode() {
        return bid.hashCode();
    }
}
