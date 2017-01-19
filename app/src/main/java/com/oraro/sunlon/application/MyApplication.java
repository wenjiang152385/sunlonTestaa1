package com.oraro.sunlon.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Created by wy on 2016/7/15.
 */
public class MyApplication extends Application {
    /**
     * 上下文对象
     */
    private static Context context;

    /**
     * 应用程序对象
     */
    private static MyApplication app;

    @Override
    public void onCreate() {
       super.onCreate();
        Log.e("wy","Application oncreate!!!!!");
        context=getApplicationContext();
        app=this;

        Picasso.Builder builder = new Picasso.Builder(this);
        LruCache picassoCache = new LruCache(1024*1024*15);

        builder.memoryCache(picassoCache);

    }
    /**
     * 获得上下文
     * @return 上下文对象
     */
    public static Context getContext(){
        return context;
    }
    /**
     * 获得app对象
     * @return app对象
     */
    public static Application getApp(){
        return app;
    }
}
