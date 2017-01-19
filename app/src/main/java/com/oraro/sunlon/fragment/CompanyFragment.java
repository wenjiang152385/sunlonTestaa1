package com.oraro.sunlon.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.oraro.sunlon.Interface.ViewPagerTouchCallback;
import com.oraro.sunlon.application.MyApplication;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.util.UISharedPreferenceUtil;
import com.oraro.sunlon.view.CycleViewPager;
import com.squareup.picasso.Cache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompanyFragment extends Fragment {

    // 图片资源ID
    private final int[] imageIds = { R.raw.jiagongye01, R.raw.jiagongye02,R.raw.jiagongye05,R.raw.jiagongye04,R.raw.jiagongye03};
    private final int[] imageIds1 = {R.raw.yangzhiye01, R.raw.yangzhiye02, R.raw.yangzhiye03
            , R.raw.yangzhiye04, R.raw.yangzhiye05};
    private final int[] imageIds2 = {R.raw.zhongzhiye01, R.raw.zhongzhiye02, R.raw.zhongzhiye03
            , R.raw.zhongzhiye04, R.raw.zhongzhiye05};
    private final int[] imageIds3 = {R.raw.fengguang05, R.raw.fengguang03, R.raw.fengguang07,
            R.raw.fengguang06, R.raw.fengguang02,R.raw.fengguang01};
    private int[] animsIn = {R.anim.anim_translate, R.anim.anim_translate1,
            R.anim.anim_translate, R.anim.anim_translate1};
    private int[] animsOut = {R.anim.anim_translate1_out, R.anim.anim_translate_out,
            R.anim.anim_translate1_out, R.anim.anim_translate_out};
    private CycleViewPager cycleViewPager;
    private MyAdapter adpter;
    private ViewGroup[] liners;

    private ImageView iv_company1;
    private ImageView iv_company2;
    private int mCurrentPosition;
    private boolean mIsShow = false;
    private int[] layoutArray = { R.layout.testlayout1,R.layout.testlayout3, R.layout.testlayout2, R.layout.testlayout4, R.layout.testlayout5,
            R.layout.testlayout6, R.layout.testlayout7};
    private List<ViewGroup> mViewGroup = new ArrayList<ViewGroup>();
    private Runnable mCurrentRunnable;
    private Handler handler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (cycleViewPager.getCurrentItem() < adpter.getCount() - 1) {
                cycleViewPager.setCurrentItem(cycleViewPager.getCurrentItem() + 1);
            } else {
                cycleViewPager.setCurrentItem(0, false);
            }
            handler.postDelayed(mCurrentRunnable, 1000 * 4);
        }

    };
    private RelativeLayout rl_viewpager;
    private LinearLayout layoutchooser;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private LinearLayout relativeFrame;
    private UISharedPreferenceUtil mUiSharedPreferenceUtil;

    public void setthread() {
        Thread thread = new Thread();
        if (!thread.isAlive()) {
            handler.removeCallbacks(mCurrentRunnable);
            if (thread != null) {
                handler.postDelayed(mCurrentRunnable, 1000 * 4);
            }
        }
    }


    public void setBaseAnimation(final int i, final int type) {
        Animation animation = null;
        Animation animation1 = null;
        Animation animation2 = null;
        Animation animation3 = null;
        if (type == 1) {
            animation = AnimationUtils.loadAnimation(getActivity(), animsIn[0]);
            animation1 = AnimationUtils.loadAnimation(getActivity(), animsIn[1]);
            animation2 = AnimationUtils.loadAnimation(getActivity(), animsIn[2]);
            animation3 = AnimationUtils.loadAnimation(getActivity(), animsIn[3]);
        } else if (type == 0) {
            animation = AnimationUtils.loadAnimation(getActivity(), animsOut[0]);
            animation1 = AnimationUtils.loadAnimation(getActivity(), animsOut[1]);
            animation2 = AnimationUtils.loadAnimation(getActivity(), animsOut[2]);
            animation3 = AnimationUtils.loadAnimation(getActivity(), animsOut[3]);
        }
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        img3.setVisibility(View.VISIBLE);
        img4.setVisibility(View.VISIBLE);
        layoutchooser.setVisibility(View.VISIBLE);
        rl_viewpager.setVisibility(View.GONE);


        img1.startAnimation(animation);
        img2.startAnimation(animation1);
        img3.startAnimation(animation2);
        img4.startAnimation(animation3);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (type == 1) {
                    layoutchooser.setVisibility(View.GONE);
                    rl_viewpager.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        adpter = new MyAdapter(imageIds, 0);
                    } else if (i == 1) {
                        adpter = new MyAdapter(imageIds1, 1);
                    } else if (i == 2) {
                        adpter = new MyAdapter(imageIds2, 2);
                    } else if (i == 3) {
                        adpter = new MyAdapter(imageIds3, 3);
                    }
                    cycleViewPager.setAdapter(adpter);
                    mCurrentRunnable = mRunnable;
                    handler.postDelayed(mCurrentRunnable, 1000 * 4);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.iv1:
//                    String url = "http://www.bjshnc.com"; // web address

//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    startActivity(intent);
//                    break;
//                case R.id.iv3:
//                    Intent intent1 = new Intent();
//                    intent1.setAction(Intent.ACTION_CALL);
//                    intent1.setData(Uri.parse("tel:0452-5589044"));
//                    //开启系统拨号器
//                    startActivity(intent1);
//                    break;
                case R.id.iv_company1:
                    //hiddenView();
                    mCurrentPosition = cycleViewPager.getCurrentItem() - 1;
                    setthread();
                    if (mCurrentPosition < 1) {
                        mCurrentPosition = mImgIds.length;
                    }
                    cycleViewPager.setCurrentItem(mCurrentPosition, false);

                    break;
                case R.id.iv_company2:
                    //hiddenView();
                    mCurrentPosition = cycleViewPager.getCurrentItem() + 1;
                    setthread();
                    if (mCurrentPosition > mImgIds.length) {
                        mCurrentPosition = 1;
                    }
                    cycleViewPager.setCurrentItem(mCurrentPosition, false);
                    break;
                case R.id.img1:
                    setBaseAnimation(0, 1);
                    break;
                case R.id.img2:
                    setBaseAnimation(1, 1);

                    break;
                case R.id.img3:
                    setBaseAnimation(2, 1);
                    break;
                case R.id.img4:
                    setBaseAnimation(3, 1);
                    break;

                default:
                    break;
            }
        }
    };

    private  void getDisplay(final View view) {
        final ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {

                int height = view.getMeasuredHeight();
                int width = view.getMeasuredWidth();
                Log.e("wjq","height = " + height);
                Log.e("wjq","width = " + width);
                mUiSharedPreferenceUtil.saveIntSharedPreferences("WIDTH", width);
                mUiSharedPreferenceUtil.saveIntSharedPreferences("HEIGHT", height);

                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Log.e("wy", "***********->fragment Oncreate");
        final View view = inflater.inflate(R.layout.fragment_company, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout_company);
        cycleViewPager = (CycleViewPager) view.findViewById(R.id.viewpager_company);
        iv_company1 = (ImageView) view.findViewById(R.id.iv_company1);
        iv_company2 = (ImageView) view.findViewById(R.id.iv_company2);
        iv_company1.setOnClickListener(mListener);
        iv_company2.setOnClickListener(mListener);
        rl_viewpager = (RelativeLayout) view.findViewById(R.id.rl_viewpager);
        layoutchooser = (LinearLayout) view.findViewById(R.id.chooseLinear);
        img1 = (ImageView) view.findViewById(R.id.img1);
        img2 = (ImageView) view.findViewById(R.id.img2);
        img3 = (ImageView) view.findViewById(R.id.img3);
        img4 = (ImageView) view.findViewById(R.id.img4);
        img1.setOnClickListener(mListener);
        img2.setOnClickListener(mListener);
        img3.setOnClickListener(mListener);
        img4.setOnClickListener(mListener);

//         mCurrentRunnable=mRunnable;
//        handler.postDelayed(mCurrentRunnable,1000*3);
//        cycleViewPager.setAdapter(adpter);

        cycleViewPager.setCallback(new ViewPagerTouchCallback() {
            @Override
            public void postTouchState(boolean state) {
                handler.removeCallbacks(mCurrentRunnable);
            }

            @Override
            public void postTouchDirection(int i) {

                setBaseAnimation(i, 0);
            }
        });

        cycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("jww", "onPageSelected: =" + position);
                if (null != adpter) {
                    if (position == 0) {
                        position = 6;
                    }
                    if (position >= 1 && position <= mImgIds.length) {
                        if (mIndex == 3) {
                            View view = liners[position];
                            if (null != view) {
                                view.setVisibility(View.VISIBLE);
                                view.startAnimation(getAnimation(R.anim.anim_show));
                            }
                        }
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // hiddenView();
                relativeFrame.setVisibility(View.GONE);
            }

        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mCurrentRunnable);
        adpter = null;
    }
//       private void hiddenView() {
//       mIsShow = false;
//       for (int i = 0; i < mViewGroup.size(); i++) {
//         mViewGroup.get(i).setVisibility(View.GONE);
//
//     }
//    }
    private void createChildView(LayoutInflater inflater, ViewGroup viewGroup, int position) {
        if (mIndex == 3) {
            ViewGroup Layout = (ViewGroup) inflater
                    .inflate(layoutArray[position], null, false);
            viewGroup.addView(Layout);
        }

    }
    private Animation getAnimation(int resources) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), resources);
        return animation;
    }
    private Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //  获取资源图片
        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is, null, opt);
    }
    private int[] mImgIds;
    private int mIndex;
    private boolean isfrist=true;
    public class MyAdapter extends PagerAdapter {
        public LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        private Bitmap mBitmap;
        public MyAdapter(int[] imgIds, int index) {
            mImgIds = imgIds;
            mIndex = index;
            liners = new ViewGroup[imgIds.length + 1];
        }
        @Override
        public int getCount() {
            return mImgIds.length;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            }
            return false;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = null;
            FrameLayout frameLayout = (FrameLayout) layoutInflater
                    .inflate(R.layout.viewpager_company, null, false)
                    .findViewById(R.id.frame_company);
            imageView = (ImageView) frameLayout.findViewById(R.id.image_company);
            relativeFrame = (LinearLayout) frameLayout.findViewById(R.id.relative_frame);
            relativeFrame.setVisibility(View.GONE);
            liners[position+1] = relativeFrame;
            mViewGroup.add(relativeFrame);
            createChildView(layoutInflater, relativeFrame, position);
            if (position==0&&isfrist){
                relativeFrame.setVisibility(View.VISIBLE);
                  relativeFrame.startAnimation(getAnimation(R.anim.anim_show));
                  isfrist = false;
            }
            mBitmap = readBitmap(getActivity(), mImgIds[position]);
            imageView.setImageBitmap(mBitmap);
            container.addView(frameLayout);
            return frameLayout;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LinearLayout ll = (LinearLayout) ((View) object).findViewById(R.id.relative_frame);
            if (mViewGroup.contains(ll)) {
                mViewGroup.remove(ll);
                System.gc();
            }
            ll = null;
            container.removeView((View) object);
            ((ImageView) ((View) object).findViewById(R.id.image_company)).setImageURI(null);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewGroup.clear();
    }
}

