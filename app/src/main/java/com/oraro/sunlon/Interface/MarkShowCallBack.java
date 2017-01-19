package com.oraro.sunlon.Interface;

import com.oraro.sunlon.vo.Mark;

import java.util.Map;

/**
 * Mark显示的回掉接口
 */
public interface MarkShowCallBack {
    /**
     * 显示所有mark点
     * @param map mark点的集合
     */
    public void showMark(Map<String,Mark> map);

    /**
     * 网络异常
     */
    public void netError();
}
