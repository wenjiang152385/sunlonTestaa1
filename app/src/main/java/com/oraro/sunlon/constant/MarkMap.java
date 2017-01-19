package com.oraro.sunlon.constant;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.oraro.sunlon.Interface.MarkShowCallBack;
import com.oraro.sunlon.application.MyApplication;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.HttpUtil;
import com.oraro.sunlon.util.MarkXmlUitl;
import com.oraro.sunlon.vo.Mark;
import com.oraro.sunlon.vo.Monitor;
import com.oraro.sunlon.vo.MonitorList;

import org.apache.http.Header;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wy on 2016/7/15.
 */

/**
 * 用户希望展现的摄像头队列
 */
public class MarkMap {
    private static MarkMap markMapVo;
    private final static String tag = "MainActivity-->";
    private Map<String, Mark> markMap = null;
    private boolean isUpLoad = true;
    private String username = "demoapp";
    private String password = "123456";
    private TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
        private int step = 0;

        @Override
        public void onStart() {
            Log.e(tag, "onStart====");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
//            Log.e(tag, "MarkMap netRsp:"+statusCode);
//            Log.e(tag, "MarkMap netRsp:"+response);//
            Gson gson = new Gson();
            MonitorList monitorList = gson.fromJson(response, MonitorList.class);
            if (monitorList != null && markMap != null) {
                Log.e("wjq", "markMap = " + markMap.size());
                List<Monitor> list = monitorList.getItm();

                for (Monitor mo : list) {
                    if (markMap.containsKey(mo.getBid())) {
                        Mark mark = markMap.get(mo.getBid());
                        mark.setIp(mo.getSip());
                        mark.setTitle(mo.getTit());
                        mark.setLevel(mo.getTpe());

                        mark.setIsOnline(true);
                    }
                }
                isUpLoad = true;
            }

            step = 0;

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
            Log.e(tag, "MainActivityonFailure====");
            if (step < 3) {
                HttpUtil.get("http://mowa-cloud.com/svr/app.php?act=lst&usr=" + username + "&pwd=" + password, new RequestParams(), responseHandler);
                step++;
            }

        }

        @Override
        public void onRetry(int retryNo) {

        }
    };

    //初始化块
    {
        Log.e("wy", "************" + MyApplication.getContext());
        Log.e("wy", "************" + MyApplication.getContext().getResources());
        InputStream is = MyApplication.getContext().getResources().openRawResource(R.raw.mark_res);

        MarkXmlUitl util = new MarkXmlUitl();
        markMap = util.readXML(is);
        HttpUtil.get("http://mowa-cloud.com/svr/app.php?act=lst&usr=" + username + "&pwd=" + password, new RequestParams(), responseHandler);
    }

    /**
     * 私有化构造函数，确保通过自定义构造生成
     */
    private MarkMap() {

    }

    /**
     * 自定义构造函数，获取MarkMap对象
     *
     * @return MarkMap对象
     */
    public static MarkMap getInstance() {
       // if (markMapVo == null) {
            markMapVo = new MarkMap();
        //}
        return markMapVo;
    }

    /**
     * 检测当前mark信息是否可用（网络判断在线状态后，视为可用）
     *
     * @return 是否可用
     */
    private boolean checkCanUse() {
        return isUpLoad;
    }

    /**
     * 加载mark点
     *
     * @param callBack 回调函数
     */
    public void loadMark(MarkShowCallBack callBack) {

        if (checkCanUse()) {

            callBack.showMark(markMap);
            Log.e("fff", "markmap==" + markMap);
        } else {
            HttpUtil.get("http://mowa-cloud.com/svr/app.php?act=lst&usr=" + username + "&pwd=" + password, new RequestParams(), responseHandler);
            callBack.netError();
        }
    }
}
