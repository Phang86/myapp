package com.yyzy.myapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyzy.myapp.MainActivity;
import com.yyzy.myapp.R;
import com.yyzy.myapp.activity.HomeActivity;
import com.yyzy.myapp.activity.LoginActivity;
import com.yyzy.myapp.adapter.MyAdapter;
import com.yyzy.myapp.adapter.ViedeoAdapter;
import com.yyzy.myapp.api.Api;
import com.yyzy.myapp.api.CallBack;
import com.yyzy.myapp.entity.CollectEntity;
import com.yyzy.myapp.entity.MyEntity;
import com.yyzy.myapp.entity.User;
import com.yyzy.myapp.entity.ViedeoDataEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import skin.support.SkinCompatManager;

public class MeFragment extends BaseFragment {

    private Button btn;
    private ImageView titleImg;
    private TextView titleName,titleAuthor,readCount,likeCount,commentCount,enjoyCount;
    private List<MyEntity> datas = new ArrayList<>();
    private LinearLayout skin;
    private String data;

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        btn = mRootView.findViewById(R.id.exitLogin);
        titleImg = mRootView.findViewById(R.id.titleImg);
        titleName = mRootView.findViewById(R.id.titleName);
        titleAuthor = mRootView.findViewById(R.id.titleAuthor);
        readCount = mRootView.findViewById(R.id.tv_read);
        likeCount = mRootView.findViewById(R.id.tv_like);
        commentCount = mRootView.findViewById(R.id.tv_comment);
        enjoyCount = mRootView.findViewById(R.id.tv_enjoy);
        skin = mRootView.findViewById(R.id.skin);

        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skin = findByKey("skin");
                if (skin.equals("night")){
                    // 恢复应用默认皮肤
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    insertVal("skin","defualt");
                }else {
                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    insertVal("skin","night");
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(MainActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK,
                        "您已成功退出！","退出登录已取消！");


            }
        });
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                    v.setBackgroundResource(R.drawable.shape_btn_exitlogin);
//                }else
//                if (event.getAction() == MotionEvent.ACTION_UP){
//                    v.setBackgroundResource(R.drawable.shape_btn_exitlogin2);
//                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        data = findByKey("name");
        titleName.setText(data);
        getNews();
//        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
//        String data	 = sp.getString("name","");    // 获取用户名

    }

    private void getNews() {
        new Api().getRequest(new CallBack() {
            @Override
            public void onSuccess(String res) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<MyEntity> dataEntity = new Gson().fromJson(res, new TypeToken<List<MyEntity>>() {
                        }.getType());
                        datas = dataEntity;
                        Glide.with(mRootView)
                                .load(datas.get(0).getTitleImg())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(titleImg);
                        titleAuthor.setText(datas.get(0).getTitleAuthor()+"\t"+data+"\t"+"，来到此平台！");
                        readCount.setText(String.valueOf(datas.get(0).getReadCount()));
                        likeCount.setText(String.valueOf(datas.get(0).getLikeCount()));
                        commentCount.setText(String.valueOf(datas.get(0).getCommentCount()));
                        enjoyCount.setText(String.valueOf(datas.get(0).getEnjoyCount()));
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        }, Api.API_MY_URL);
    }
}