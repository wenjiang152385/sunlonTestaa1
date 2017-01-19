package com.oraro.sunlon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.oraro.sunlon.application.MyApplication;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.HttpUtil;
import com.oraro.sunlon.vo.Mark;

import org.apache.http.Header;

/**
 * 摄像头移动控制控件
 */
public class VideoControlView  extends RelativeLayout implements View.OnClickListener{
    /**视频端ip地址*/
    private String ip;
    /**视频端设备id号*/
    private String videoId;

    private  ImageView up;
    private  ImageView down;
    private  ImageView left;
    private  ImageView right;
    private  ImageView toBig;
    private  ImageView toSmall;
    private final static String tag = "MainActivity-->";
    private RequestParams params = new RequestParams();

    /**
     * 处理心跳返回
     */
    private TextHttpResponseHandler controlHandler =  new TextHttpResponseHandler(){
        private int step = 0;
        @Override
        public void onStart() {
            Log.e(tag, "onStart====");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
            Log.e(tag,"onSuccessresponse = " + response + "statusCode = " + statusCode);
            Toast.makeText(MyApplication.getContext(),"操作成功，请稍候...",Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        stopMove();
                    }
                }
            }).start();

        }


        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
            Log.e(tag,"onFailureresponse = " + errorResponse + "statusCode = " + statusCode);
            Toast.makeText(MyApplication.getContext(),"操作失败，请检查网络状态...",Toast.LENGTH_SHORT).show();
            stopMove();//务必让摄像机停止转动，以防电机毁坏
        }

        @Override
        public void onRetry(int retryNo) {

        }
    };
    /**
     * 停止移动的监听
     */
    private TextHttpResponseHandler stopHandler =  new TextHttpResponseHandler(){
        private int step = 0;
        @Override
        public void onStart() {
            Log.e(tag, "onStart====");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
            Log.e(tag, "stop moving success!");

        }


        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
            Log.e(tag, "stop moving Fail!");
            stopMove();//务必让摄像机停止转动，以防电机毁坏
        }

        @Override
        public void onRetry(int retryNo) {

        }
    };

    public VideoControlView(Context context) {
        super(context);
    }

    public VideoControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_video_control, this, true);
        RelativeLayout controlBg = (RelativeLayout) findViewById(R.id.controlbg);
        controlBg.setAlpha(0.5f);
        up = (ImageView) findViewById(R.id.control_up);
        down = (ImageView) findViewById(R.id.control_down);
        left = (ImageView) findViewById(R.id.control_left);
        right = (ImageView) findViewById(R.id.control_right);
        toBig = (ImageView) findViewById(R.id.control_big);
        toSmall = (ImageView) findViewById(R.id.control_small);
    }


    public void setOnClick(String ip,String id){
        this.videoId = id;
        this.ip = ip;
        if(ip!=null && videoId!=null && up!=null &&down!=null && left!=null && right!=null && toBig!=null && toSmall!=null && !ip.equals("") && !videoId.equals("")){
            up.setOnClickListener(this);
            down.setOnClickListener(this);
            left.setOnClickListener(this);
            right.setOnClickListener(this);
            toBig.setOnClickListener(this);
            toSmall.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        Log.e("jw","id = " + videoId);
        Log.e("jw","ip = "+ip);
        switch (view.getId()){
            case R.id.control_up:
                // http://101.231.52.58:1936/svr.php?act=cts&bid=1234560306&pm1=2&pm2=405
                HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=104", params, controlHandler);
                break;
            case R.id.control_down:
                HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=204", params, controlHandler);
                break;
            case R.id.control_left:
                HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=304", params, controlHandler);
                break;
            case R.id.control_right:
                HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=404", params, controlHandler);
                break;
            case R.id.control_big:
                HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=504", params, controlHandler);
                break;
            case R.id.control_small:
                HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=604", params, controlHandler);
                break;

        }


    }

    /**
     * 停止转动
     */
    public void stopMove(){
        HttpUtil.get("http://"+ip+":1936/svr.php?act=cts&bid=" + videoId+"&pm1=2&pm2=0", params, stopHandler);
    }
}
