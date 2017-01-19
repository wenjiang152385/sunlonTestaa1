package com.oraro.sunlon.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.oraro.sunlon.Interface.MyCircleCallback;
import com.oraro.sunlon.constant.MarkMap;
import com.oraro.sunlon.db.Dao;
import com.oraro.sunlon.fragment.CompanyFragment;
import com.oraro.sunlon.fragment.MapFragment;
import com.oraro.sunlon.fragment.MonitorFragment;
import com.oraro.sunlon.fragment.ProductFragment;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.GuideViewUtil;
import com.oraro.sunlon.util.PackageUtils;
import com.oraro.sunlon.view.FullScreenVideoView;
import com.oraro.sunlon.view.OverCircleView;


public class DisplayActivity extends Activity {


    /**
     * 首次进入Activity播放视频
     */
    private FullScreenVideoView videoView;


    /**
     * videoview刚启动时是黑屏，使用该白色view覆盖在VideoView上。有画面在移除掉
     */
    private FrameLayout frame;

    /**
     * 打开fragment的集合
     */
    private Class[] mClasses = new Class[]{CompanyFragment.class, MonitorFragment.class,
            MapFragment.class, ProductFragment.class};



    //
    private long exitTimeMillis = System.currentTimeMillis();


    private GuideViewUtil guideViewUtil;
    private  BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            finish();
        }
    };
    private MyCountDownTimer myCountDownTimer;
    private Button button;
    private FrameLayout fl_video;
    private OverCircleView betterCircleView;
    private FrameLayout ovalsLayout;
    private TextView tv1;
    private String uri;
    private String [][] person={{"18951996707","0"},{"18846389005","0"},{"18846389019","0"},
            {"13351626161","0"},{"13163408801","0"},{"18846389001","0"},{"18952005110","0"},
            {"18951810217","1"},{"15663715511","0"},{"18545002990","0"}, {"18846389006","0"},
            {"11111111111","1"},{"18248785628","0"},{"15904521573","1"}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        myCountDownTimer = new MyCountDownTimer(6000, 1000);
        myCountDownTimer.start();

        IntentFilter filter = new IntentFilter();
        Log.e("jw","filter="+filter);
        //黑屏
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mBatInfoReceiver, filter);
        Dao dao = Dao.getInstance(this);
        for (int i = 0; i <person.length ; i++) {
            dao.add(person[i][0],person[i][1]);
        }
        MarkMap.getInstance();//初始化mark
        WindowManager wm = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
//        Log.e("wjq", "Width = " + outMetrics.widthPixels + "height = " + outMetrics.heightPixels);
        ovalsLayout = (FrameLayout) findViewById(R.id.cutOvals);
        ovalsLayout.setVisibility(View.GONE);
        ViewGroup.LayoutParams lp = ovalsLayout.getLayoutParams();
        button = (Button) findViewById(R.id.bt_timer);
        lp.width = outMetrics.widthPixels / 5;
        lp.height = outMetrics.heightPixels;
        betterCircleView = (OverCircleView) findViewById(R.id.circleview);
       // ll = (LinearLayout) findViewById(R.id.container);
        betterCircleView.setOnCircleItemChangeListener(new MyCircleCallback() {
            @Override
            public void getIndex(int i) {
              //  Log.e("sa","i=="+i);
                openFragment(i);
            }
        });
        fl_video = (FrameLayout) findViewById(R.id.fl_video);
        videoView = (FullScreenVideoView) findViewById(R.id.videoView1);
        frame = (FrameLayout) findViewById(R.id.frame);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("版本号:V" + PackageUtils.getVersionName(this));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.pause();
                fl_video.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
               // videoView.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
               ovalsLayout.setVisibility(View.VISIBLE);
                betterCircleView.autoRun();
                myCountDownTimer.cancel();

            }
        });

        //组装视频的地址
        uri = "android.resource://" + getPackageName() + "/"
                + R.raw.magic;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();

        //加载第一个fragment
        getFragmentManager().beginTransaction().replace(R.id.container, new CompanyFragment()).commit();

        //viddeoView开始播放的监听
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                frame.setVisibility(View.GONE);
            }
        });

//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                videoView.pause();
//                fl.setVisibility(View.VISIBLE);
//            }
//        });
    }

        class  MyCountDownTimer extends CountDownTimer{

            /**
             * @param millisInFuture    The number of millis in the future from the call
             *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
             *                          is called.
             * @param countDownInterval The interval along the way to receive
             *                          {@link #onTick(long)} callbacks.
             */
            public MyCountDownTimer(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                button.setText("跳过" + millisUntilFinished / 1000);
                //("跳过"+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                videoView.pause();
                fl_video.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                // videoView.setVisibility(View.GONE);
                tv1.setVisibility(View.GONE);
                ovalsLayout.setVisibility(View.VISIBLE);
                betterCircleView.autoRun();
                myCountDownTimer.cancel();


            }
        }

    private void openFragment(int position) {

        //实例化fragment。 所有fragment都是用的Android.app.fragment,没有用V4的fragment，导包时要注意
        Fragment fragment = Fragment.instantiate(DisplayActivity.this, mClasses[position].getName());
        Log.e("wjq", "mClasses[position].getName() = " + mClasses[position].getName());
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        videoView.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatInfoReceiver);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - exitTimeMillis == 0 || currentTime - exitTimeMillis > 1500) {
                exitTimeMillis = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
