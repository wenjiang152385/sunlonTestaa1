//package com.oraro.sunlon.view;
//
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.loopj.android.http.TextHttpResponseHandler;
//import com.oraro.sunlon.activity.MonitorActivity;
//
//import com.oraro.sunlon.Interface.MarkCloseAndTopCallBack;
//import com.oraro.sunlon.db.Dao;
//import com.oraro.sunlon.sunlontesta.Person;
//import com.oraro.sunlon.sunlontesta.R;
//import com.oraro.sunlon.util.UISharedPreferenceUtil;
//import com.oraro.sunlon.vo.Mark;
//
//import org.apache.http.Header;
//
//import java.util.List;
//import java.util.Locale;
//
///**
// * 包含markView点，和一个预览窗口的view
// * @author 王子榕
// */
//public class MarkWindowView extends RelativeLayout implements View.OnClickListener{
//    /**视频当前一帧缩略图*/
//    private ImageView imageView;
//    /**Log Tag*/
//    private final static String tag = "MainActivity-->";
//    /**视频端ip地址*/
//    private String ip;
//    /**视频端设备id号*/
//    private String id;
//
//
//    private Activity mActivity;
//    private String name1;
//    private String  psw;
//
//    private Context mContext;
//    private String sp1name;
//    private String sp1password;
//
//    public MarkWindowView(Context context) {
//        super(context);
//        mContext = context;
//    }
//
//    public MarkWindowView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//    }
//
//    public Activity getActivity(Activity activity) {
//        mActivity = activity;
//        return activity;
//    }
//    private String name2;
//    private  String password2;
//    final Dao dao = Dao.getInstance(getContext());
//    private  UISharedPreferenceUtil mUiSharedPreferenceUtil;
//    private final static  String KEY = "isLogin";
//    private MarkCloseAndTopCallBack mCallback;
//    /**
//     * 构造器
//     * @param context 上下文
//     * @param mark 对应的mark对象
//     */
//    public  MarkWindowView(final Context context, Mark mark,MarkCloseAndTopCallBack callback) {
//        this(context);
//        mCallback = callback;
////        //主要以键值对的形式取出
////        isFirst=sp.getBoolean("first", true);
//        mUiSharedPreferenceUtil = UISharedPreferenceUtil.getInstall(context);
//        Log.e("wjq","value = " + mUiSharedPreferenceUtil.getIntSharedPreferences(KEY));
//        if (mUiSharedPreferenceUtil.getIntSharedPreferences(KEY) == -1){
//            mUiSharedPreferenceUtil.saveIntSharedPreferences(KEY, 0);
//        }
////        Log.e("wjq","1111111111111111111111111|||||||" + sp.getIntSharedPreferences("isLogin") + "");
////        if (sp.getIntSharedPreferences("isLogin") == -1) {
////            //未登陆
////            sp.saveIntSharedPreferences("isLogin",0);
////        }
//
//
//        /**设置设备id*/
//        this.id = mark.getId();
//        /**设置设备ip*/
//        this.ip = mark.getIp();
//        /**设置布局*/
//        LayoutInflater.from(context).inflate(R.layout.view_mark_window, this, true);
//        imageView = (ImageView) findViewById(R.id.img);
//        /**设置mark点的点击时间*/
//        findViewById(R.id.marka).setOnClickListener(this);
//         /**设置缩略图的点击事件*/
//        imageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mUiSharedPreferenceUtil.getIntSharedPreferences(KEY) == 0){
//                   showDialog();
//                }
//                else if (mUiSharedPreferenceUtil.getIntSharedPreferences(KEY) == 1){
//                     getIntent();
//                }
//            }
//
//        });
//    }
//
//    private void showDialog() {
//        final List<Person> person = dao.query();
//        final AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
//        View v = View.inflate(getContext(), R.layout.userlogin, null);
//        ImageView iv_cha = (ImageView) v.findViewById(R.id.iv_cha);
//        final EditText et_name = (EditText) v.findViewById(R.id.et_name);
//       // final EditText et_password = (EditText) v.findViewById(R.id.et_password);
//        Button bt = (Button) v.findViewById(R.id.bt_denglu);
//        dialog.setView(v);
//        final AlertDialog alertDialog = dialog.create();
//        bt.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                name1 = et_name.getText().toString().trim();
//                //psw = et_password.getText().toString().trim();
//                if (TextUtils.isEmpty(name1)) {
//                    Toast.makeText(getActivity(mActivity), "手机号码不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    for (int i = 0; i < person.size(); i++) {
//                        Person person1 = person.get(i);
//                        name2 = person1.getName();
//                        //password2 = person1.getPassword();
//                        if (name1.equals(name2)) {
////                            SharedPreferences sp=mActivity.getSharedPreferences("config", Context.MODE_PRIVATE);
////                            name11 = sp.edit().putString("name", name1).commit();
////                            pwd11=  sp.edit().putString("password", psw).commit();
//                            Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
//                            mUiSharedPreferenceUtil.saveIntSharedPreferences(KEY, 1);
//                            getIntent();
//                            alertDialog.dismiss();
//                            return;
//                        } else {
//                            Toast.makeText(mActivity, "登录失败,请重新登录", Toast.LENGTH_SHORT).show();
//                            et_name.setText("");
//                            return;
//                        }
//                    }
//                }
//            }
//        });
//
//        iv_cha.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//        Window window = alertDialog.getWindow();
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.alpha = 0.6f;
//        window.setAttributes(attributes);
//        alertDialog.show();
//    }
//    private void getIntent() {
//        Intent intent = new Intent(getContext(), MonitorActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putCharSequence("ip", ip);
//        bundle.putCharSequence("id", id);
//        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getContext().getApplicationContext().startActivity(intent);
//    }
//
//    /**
//     * 重写mark点，点击事件
//     * @param view 被点击的view
//     */
//    @Override
//    public void onClick(View view) {
//        if(imageView == null) return;
//        if(imageView.getVisibility() == View.VISIBLE){
//            imageView.setVisibility(View.INVISIBLE);
//        }
//        else{
//            if(mCallback != null) mCallback.closeOtherMarkWindow();
//            imageView.setVisibility(View.VISIBLE);
//            this.invalidate();
//            this.bringToFront();
//            imageView.invalidate();
//            imageView.bringToFront();
//
//        }
//    }
//
//    private TextHttpResponseHandler responseHandler =  new TextHttpResponseHandler(){
//        @Override
//        public void onStart() {
//            Log.e(tag, "onStart====");
//        }
//
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, String response) {
//            Log.e(tag, "MainActivityonSuccess====");
//            StringBuilder builder = new StringBuilder();
//            for (Header h : headers) {
//                String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
//                builder.append(_h);
//                builder.append("\n");
//            }
//            Log.e(tag, "statusCode:" + statusCode + " headers:" + builder.toString() + " response:" + response);
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
//            Log.e(tag, "MainActivityonFailure====");
//
//        }
//
//        @Override
//        public void onRetry(int retryNo) {
//
//        }
//    };
//
//    public ImageView getImageView() {
//        return imageView;
//    }
//}
