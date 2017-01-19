package com.oraro.sunlon.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oraro.sunlon.Interface.MarkShowCallBack;
import com.oraro.sunlon.Interface.MyViewCallBack;
import com.oraro.sunlon.Interface.editCallback;
import com.oraro.sunlon.activity.MonitorActivity;
import com.oraro.sunlon.constant.MarkMap;
import com.oraro.sunlon.db.Dao;
import com.oraro.sunlon.sunlontesta.Person;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.BaseParams;
import com.oraro.sunlon.util.UISharedPreferenceUtil;
import com.oraro.sunlon.view.LoginDialog;
import com.oraro.sunlon.view.MyView;
import com.oraro.sunlon.vo.Mark;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 地图模式的碎片
 *
 * @author 王子榕
 */
public class MapFragment extends Fragment {
    /**
     * 碎片最终的布局容器
     */
    private ViewGroup viewGroup;
    private MyView myView;
    private Dao dao;
    private int permisson = -1;
    private Mark mMark;
    private UISharedPreferenceUtil mUiSharedPreferenceUtil;
    private final static String KEY = "isLogin";
    private final static String PERS = "pres";
    private LoginDialog dialog;
    private editCallback callback;

    private Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //  获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
//        Matrix matrix = new Matrix();
//        matrix.postScale(0.6f,0.6f); //长和宽放大缩小的比例
//        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return bitmap;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUiSharedPreferenceUtil.saveIntSharedPreferences(KEY, 0);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_map, container, false);
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter("www.fuck.com"));
        Log.e("jw", "MapFragment            -------------------->  onCreate");
//        ImageView map = (ImageView) view.findViewById(R.id.mapooses);
//        map.setImageBitmap(readBitmap(getActivity(),R.raw.mapoos));
        mUiSharedPreferenceUtil = UISharedPreferenceUtil.getInstall(getActivity());
        checkDate();
        Log.e("dd", "value = " + mUiSharedPreferenceUtil.getIntSharedPreferences(KEY));
        if (mUiSharedPreferenceUtil.getIntSharedPreferences(KEY) == -1) {
            mUiSharedPreferenceUtil.saveIntSharedPreferences(KEY, 0);
        }
        dao = Dao.getInstance(getActivity());
        myView = (MyView) view.findViewById(R.id.myview);
        LinearLayout mapLayout = (LinearLayout) view.findViewById(R.id.map);
        if (view instanceof ViewGroup) {
            viewGroup = (ViewGroup) view;
        }
        //获取布局的宽高
        getLayoutParams(mapLayout);
        //获取屏幕宽高
        myView.getDisplayMetrics(getDisplay());
        //获取view的大小
        getLayoutParams(view);
         //整个界面的点击
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.hiddenALLImageViews();
            }
        });

         //回调myview的mark点
        myView.setCallBack(new MyViewCallBack() {
            @Override
            public void callBack(Mark mark) {
                Log.e("ee","mark=="+mark);
                mMark = mark;
                if (mMark.getId().startsWith("X")) {
                    Toast.makeText(getActivity(), "设备正在架设中..", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("jw", "value11 = " + mUiSharedPreferenceUtil.getIntSharedPreferences(KEY));
                if (mUiSharedPreferenceUtil.getIntSharedPreferences(KEY) == 0) {

                    showDialog();
                } else if (mUiSharedPreferenceUtil.getIntSharedPreferences(KEY) == 1) {
                    startIntent();
                }
            }

            @Override
            public void callShowMark() {
                   Toast.makeText(getActivity(),"网络异常,请检查网络连接",Toast.LENGTH_SHORT).show();
                loadMark();
            }
        });

        /**加载mark信息并展示s*/
         loadMark();
        // Log.e("wy","------------xxx-------->"+viewGroup);
        return viewGroup == null ? view : viewGroup;

    }
    private  void loadMark(){
        MarkMap.getInstance().loadMark(new MarkShowCallBack() {
            /**
             * 显示全部在线的mark点
             * @param map mark点的集合
             */
            @Override
            public void showMark(Map<String, Mark> map) {
                myView.addMarks(map);
            }

            /**
             * 网络数据获取失败
             */
            @Override
            public void netError() {
                Log.e("wy", "------------yyy-------->net error");
            }
        });
    }
    private DisplayMetrics getDisplay() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }


    private void getLayoutParams(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = view.getHeight();
                int width = view.getWidth();
                BaseParams params = new BaseParams();
                params.setHeight(height);
                params.setWidth(width);
            }
        });
    }


    private void showDialog() {
//        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        View v = View.inflate(getActivity(), R.layout.userlogin, null);
//
//        final EditText editText = (EditText) v.findViewById(R.id.et_name);
//        dialog.setView(v);
//        alertDialog = dialog.create();
//        alertDialog.show();


        dialog = new LoginDialog(getActivity());
        dialog.setTitle("身份验证");

        dialog.show();
        dialog.setCallBack( new editCallback() {
            @Override
            public void setTextListener(String text) {
                Log.e("11","text=="+text);
                checkPhoneNumber(text);

            }
        });

//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                String phoneNumber = editText.getText().toString();
//                checkPhoneNumber(phoneNumber);
//
//            }
//        });
    }

    public void checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 11) {
            List<Person> data = dao.querysByName(phoneNumber);
            if (data.size() == 0) {
                Toast.makeText(getActivity(), "手机号码不正确", Toast.LENGTH_LONG).show();
                return;
            } else {
                for (int i = 0; i < data.size(); i++) {
                    permisson = Integer.parseInt(data.get(i).getPassword());
                    mUiSharedPreferenceUtil.saveIntSharedPreferences(PERS, permisson);
                }
                Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                mUiSharedPreferenceUtil.saveIntSharedPreferences(KEY, 1);
                Calendar c = Calendar.getInstance();
                int day = c.get(c.DAY_OF_YEAR);
                mUiSharedPreferenceUtil.saveIntSharedPreferences("TIME", day);
                dialog.dismiss();
                startIntent();
                return;
            }
        }
    }

    private String checkDate() {
        Calendar c = Calendar.getInstance();//默认是当前日期
        int nowday = c.get(c.DAY_OF_YEAR);
        int oldday = mUiSharedPreferenceUtil.getIntSharedPreferences("TIME");
        Log.e("js", "now = " + nowday);
        Log.e("js", "old = " + oldday);
        if (oldday==-1){
            oldday=nowday;
        }
        if (Math.abs(nowday - oldday) >= 90) {
            mUiSharedPreferenceUtil.saveIntSharedPreferences(KEY, 0);
            Log.e("js", "已经超过90天了");
        }

        return "";
    }

    private void startIntent() {
        Intent intent = new Intent(getActivity(), MonitorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ip", mMark.getIp());
        bundle.putCharSequence("id", mMark.getId());
        bundle.putCharSequence("title", mMark.getTitle());
        bundle.putInt("level", mMark.getLevel());
        bundle.putInt("permisson", mUiSharedPreferenceUtil.getIntSharedPreferences(PERS));
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}
