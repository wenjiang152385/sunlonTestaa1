package com.oraro.sunlon.fragment;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oraro.sunlon.Interface.ViewPagerTouchCallback;
import com.oraro.sunlon.sunlontesta.R;
import com.oraro.sunlon.view.CycleViewPager;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class ProductFragment extends Fragment {
    // 图片资源ID
    private final int[] imageIds = {R.raw.product1, R.raw.product2, R.raw.product5,
            R.raw.product3, R.raw.product4};
    private ViewPager viewPager;
    //private List<ImageView> imageList;
    private MyAdapter adapter;
    private CycleViewPager cycleViewPager;
    private ImageView iv_product1;
    private ImageView iv_product2;
    private int currentPosition;
    private Runnable mCurrentRunnable;
    private  Handler handler=new Handler();
    private  Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            if (cycleViewPager.getCurrentItem() < adapter.getCount() - 1) {
                cycleViewPager.setCurrentItem(cycleViewPager.getCurrentItem() + 1);
            }else {
                cycleViewPager.setCurrentItem(0,false);
            }
            handler.postDelayed(mCurrentRunnable,1000*3);
        }

    };
    public void setthread(){
        Thread thread=new Thread();
        if (!thread.isAlive()){
            handler.removeCallbacks(mCurrentRunnable);
            if (thread!=null){
                handler.postDelayed(mCurrentRunnable,1000*3);
            }
        }
    }
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_product1:
                    currentPosition = cycleViewPager.getCurrentItem() - 1;
                    Log.e("ddd","currentPosition=="+currentPosition);
                   setthread();
                    Log.e("11","imagelenth1=="+imageIds.length);
                    if (currentPosition < 1) {
                        currentPosition = imageIds.length;
                    }
                    cycleViewPager.setCurrentItem(currentPosition, false);
                    break;
                case R.id.iv_product2:
                    currentPosition = cycleViewPager.getCurrentItem() + 1;
                    Log.e("ddd","currentPosition=="+currentPosition);
                    setthread();
                    Log.e("11", "imagelenth2==" + imageIds.length);
                    if (currentPosition > imageIds.length) {
                        currentPosition = 1;
                    }
                    cycleViewPager.setCurrentItem(currentPosition, false);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        cycleViewPager = (CycleViewPager) view.findViewById(R.id.viewPager_product);
        iv_product1 = (ImageView) view.findViewById(R.id.iv_product1);
        iv_product2 = (ImageView) view.findViewById(R.id.iv_product2);
        iv_product1.setOnClickListener(mListener);
        iv_product2.setOnClickListener(mListener);
        mCurrentRunnable = mRunnable;
        handler.postDelayed(mCurrentRunnable,1000 * 3);
        adapter = new MyAdapter();
        cycleViewPager.setCallback(new ViewPagerTouchCallback() {
            @Override
            public void postTouchState(boolean state) {
        handler.removeCallbacks(mCurrentRunnable);
    }

    @Override
    public void postTouchDirection(int i) {

    }
});
        cycleViewPager.setAdapter(adapter);
        return view;


        }

@Override
public void onDestroy() {
        super.onDestroy();
    handler.removeCallbacks(mCurrentRunnable);
        adapter=null;
    }

    private Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //  获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    private class MyAdapter extends PagerAdapter {
        private Bitmap mBitmap;

        @Override
        public int getCount() {
            return imageIds.length;
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
            ImageView imageView = (ImageView) LayoutInflater
                    .from(container.getContext()).inflate(R.layout.viewpager_product, null, false)
                    .findViewById(R.id.image_product);
            mBitmap = readBitmap(getActivity(), imageIds[position]);
            imageView.setImageBitmap(mBitmap);
            container.addView(imageView);
            return imageView;
        }

        @Override

        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("destroyItem : position:" + position);
//			super.destroyItem(container, position, object);
            container.removeView((View) object);


        }

    }


}
