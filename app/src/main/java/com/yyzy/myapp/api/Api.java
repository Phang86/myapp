package com.yyzy.myapp.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api {
    public static final String API_VIDEO_URLT = "http://192.168.1.101:8080/myapp/video/list";
    public static final String API_NEWS_URL = "http://192.168.1.101:8080/myapp/news/list";
    public static final String API_MY_URL = "http://192.168.1.101:8080/myapp/me/list";
    //public static final String API_URL = "http://192.168.43.105:8080/myapp/video/list";

    public void getRequest(final CallBack callback, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //showToast(res);
                //将拿到的json数据转换成实体类
                String res = response.body().string();
                //Log.e("TAG", "onResponse: "+res);
                callback.onSuccess(res);
            }
        });
    }


}
