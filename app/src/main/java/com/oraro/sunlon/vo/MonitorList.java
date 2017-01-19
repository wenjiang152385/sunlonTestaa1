package com.oraro.sunlon.vo;

import java.util.List;

/**
 * 网络在线摄像头列表实体
 * @author 王子榕
 */
public class MonitorList{
    /**摄像头集合*/
    private List<Monitor> itm;

    public List<Monitor> getItm() {
        return itm;
    }

    public void setItm(List<Monitor> itm) {
        this.itm = itm;
    }
}
