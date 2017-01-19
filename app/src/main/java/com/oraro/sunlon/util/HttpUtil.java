package com.oraro.sunlon.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 网络请求封装（依赖于Asyn_Http_Client）
 * @author 王子榕
 */
public class HttpUtil {
//    private static final String BASE_URL = "http://192.168.17.99/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void setTimeout(){
        client.setTimeout(60000);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void download(String url, RequestParams params, FileAsyncHttpResponseHandler fileAsyncHttpResponseHandler){
        client.get(getAbsoluteUrl(url), params, fileAsyncHttpResponseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return relativeUrl;
    }

}
