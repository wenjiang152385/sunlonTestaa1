package com.oraro.sunlon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.HttpUtil;
import com.oraro.sunlon.util.UISharedPreferenceUtil;
import com.oraro.sunlon.view.UISwitchButton;
import com.oraro.sunlon.view.VideoControlView;

import org.apache.http.Header;


import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * 监控播放Activity
 */
public class MonitorActivity extends Activity {
    private final static String tag = "MainActivity-->";
    private final static int ONLOADING = 0;
    private final static int LOADED= 1;

    private LinearLayout loadingVideoLayout;
    private ProgressBar loadBar;
    private TextView  loadText;
    private RequestParams params = new RequestParams();
    private String ip;
    private String id;
    private String title;
    private int level;
    private int permisson;
    private VideoView mVideoView;
    private UISwitchButton switchBtn;
    private VideoControlView controlView;
    private LinearLayout videoDefinitionLayout;
    private ImageButton backButton;
    private UISharedPreferenceUtil mUiSharedPreferenceUtil;
    /**
     * 维持心跳请求（4S一次）
     */
    private Thread thread  = new Thread(new Runnable() {
        @Override
        public void run() {
            boolean flag = true;
            while(flag) {
                heartMonitor(id);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    flag = false;
                }
            }
        }
    });
    /**
     *视频加载进度条及文字控制
     *
     */
    private Thread loadingThread  = new Thread(new Runnable() {
        @Override
        public void run() {
            boolean flag = true;
            while(flag) {
                myMessageHandler.sendEmptyMessage(ONLOADING);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    flag = false;
                }
            }
        }
    });

    /**
     *视频加载进度条及文字控制Handler
     *
     */
    private Handler myMessageHandler = new Handler(){

        private int textStep = 0;
        private int barStep = 0;
        public void handleMessage(Message msg){

            switch(msg.what){

                case ONLOADING:
                    if(loadBar != null && loadText != null && loadingVideoLayout != null){
                        if(barStep>99) barStep = 0;
                        loadBar.setProgress(barStep+20);
                        if(textStep == 0){
                            loadText.setText(R.string.loadingVideob);
                            textStep++;
                        }else if(textStep == 1){
                            loadText.setText(R.string.loadingVideoc);
                            textStep++;
                        }else{
                            loadText.setText(R.string.loadingVideoa);
                            textStep = 0;
                        }


                    }

                    break;

                case LOADED:
                    loadingThread.interrupt();
                    loadingVideoLayout.setVisibility(View.GONE);
                    break;

            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_monitor);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ip = bundle.getString("ip");
        id = bundle.getString("id");
        level = bundle.getInt("level");
        permisson = bundle.getInt("permisson");
        title = bundle.getString("title");
        initLoading(level);
        openMonitor(ip, id);

    }

    /**
     * 初始化界面
     */
    private void initLoading(int level){
        mVideoView =  (VideoView) findViewById(R.id.vitamio_videoView);
        loadingVideoLayout = (LinearLayout) findViewById(R.id.videoLoadingLayout);
        loadBar = (ProgressBar) findViewById(R.id.videoLoadingProgerss);
        loadText = (TextView) findViewById(R.id.videoLoadingText);
        loadBar.setMax(100);
        loadingThread.start();

        controlView = (VideoControlView) findViewById(R.id.video_control_view);
        videoDefinitionLayout = (LinearLayout) findViewById(R.id.videoDefinitionLayout);
        backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent("www.fuck.com");
                sendBroadcast(intent);
                finish();
            }
        });
        showVideoControl();
        //默认进入高清模式
        HttpUtil.get("http://" + ip + "/svr/fls.php?act=qxd&bid=" + id + "&qxd=2", params, definitionHandler);
        setOnClick(findViewById(R.id.img_hd), 0);

        setOnClick(findViewById(R.id.img_md), 1);




    }

    private void showVideoControl() {
        String titles;
        titles = title.substring(title.length() - 2,title.length());
        Log.e("jw","title = " + title);
        controlView.setVisibility(View.GONE);
        if (level == 1) {
            controlView.setVisibility(View.GONE);
        }else if (level == 0){
            if (permisson == 0) {
                controlView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setOnClick(View view,final int i) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0) {
                    //http://<mip>/svr/fls.php?act=qxd&bid=<bid>&qxd=<qxd>
                    HttpUtil.get("http://" + ip + "/svr/fls.php?act=qxd&bid=" + id + "&qxd=2", params, definitionHandler);
                    Toast.makeText(MonitorActivity.this, "正在切换至高清模式,请稍后...", Toast.LENGTH_SHORT)
                            .show();
                } else if (i == 1) {
                    HttpUtil.get("http://" + ip + "/svr/fls.php?act=qxd&bid=" + id + "&qxd=1", params, definitionHandler);
                    Toast.makeText(MonitorActivity.this, "正在切换至流畅模式,请稍后...", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("wy","stop");
        thread.interrupt();
        loadingThread.interrupt();
    }



    /**
     * 打开监控
     * @param ip ip地址
     * @param id 设备id号
     */
    private void openMonitor(String ip,String id){

        HttpUtil.get("http://" + ip + ":1936/svr.php?act=cts&bid=" + id + "&pm1=1&pm2=1", params, openHandler);
        Log.e("wy","openMonitor url :"+"http://" + ip + ":1936/svr.php?act=cts&bid=" + id + "&pm1=1&pm2=1");
    }

    /**
     * 申请心跳
     * @param id 设备id号
     */
    private void heartMonitor(String id){
        HttpUtil.get("http://mowa-cloud.com/svr/fls.php?act=hbt&bid=" + id , params, heartHandler);
    }

    /**
     * 申请播放
     * @param ip ip地址
     * @param id 设备id号
     */
    private void playMonitor(String ip,String id){
        mVideoView.setVideoPath("rtmp://" + ip + ":1935/live/" + id);
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
        //  mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        //mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f);
                mediaPlayer.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
                Log.e("wy", "setOnPreparedListener!!!");
            }
        });

        controlView.setOnClick(ip, id);
        mVideoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            private int step = 0;

            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (step == 0 && percent > 0) {
                    myMessageHandler.sendEmptyMessage(LOADED);
                    mp.start();
                    step++;
                }
            }
        });

    }


    /**
     * 处理打开监控返回
     */
    private TextHttpResponseHandler openHandler =  new TextHttpResponseHandler(){
        @Override
        public void onStart() {
            Log.e(tag, "onStart====");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
            Log.e(tag, "open --ok");
            thread.start();

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
            Log.e(tag, "open --fail:" + errorResponse);

        }

        @Override
        public void onRetry(int retryNo) {

        }
    };
    /**
     * 处理心跳返回
     */
    private TextHttpResponseHandler heartHandler =  new TextHttpResponseHandler(){
        private int step = 0;
        @Override
        public void onStart() {
            Log.e(tag, "onStart====");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
            Log.e(tag, "heart --ok");
            if(step == 0){
                Log.e(tag, "heart --ok1");
                step++;
                playMonitor(ip, id);
            }
            else{
                Log.e(tag, "heart --ok2");
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
            Log.e(tag, "heart --fail");
        }

        @Override
        public void onRetry(int retryNo) {

        }
    };

    /**
     * 处理视频质量
     */
    private TextHttpResponseHandler definitionHandler =  new TextHttpResponseHandler(){
        private int step = 0;
        @Override
        public void onStart() {
            Log.e(tag, "onStart====");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String response) {
            Log.e(tag, "definitio --ok");


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
            Log.e(tag, "definitio --fail");
        }

        @Override
        public void onRetry(int retryNo) {

        }
    };

}
