package com.oraro.sunlon.sunlontesta;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.oraro.sunlon.fragment.MapFragment;


/**
 * 被遗弃的Activity，第一内测版本测试时的入口
 * @author 王子榕
 */
public class MainActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Log.i(tag, "step000====");
        setContentView(R.layout.activity_main);
    //    MarkView markView = (MarkView) findViewById(R.id.marka);
//        markView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                                Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putCharSequence("ip", "101.231.52.58");
//                bundle.putCharSequence("id", "1234560305");
//                intent.putExtras(bundle);
//
//
//                HttpUtil.get("http://dvr.dragra.com/svr/app.php?act=log&usr=demoapp&pwd=123456", new RequestParams(), responseHandler);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                HttpUtil.get("http://dvr.dragra.com/svr/app.php?act=lst&usr=demoapp&pwd=123456", new RequestParams(), responseHandler);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//
//
//              startActivity(intent);
//            }
//        });
//        Button btn = (Button)findViewById(R.id.btn_get_video);
//
//        Log.e(tag, "step111====");
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putCharSequence("ip", "101.231.52.58");
//                bundle.putCharSequence("id", "1234560305");
//                intent.putExtras(bundle);
//


        MapFragment map = new MapFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment,map);


    }
}
