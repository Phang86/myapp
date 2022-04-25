package com.yyzy.myapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.yyzy.myapp.activity.LoginActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.doikki.videoplayer.player.VideoViewManager;

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    private Looper Thread;
    public Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(initLayout(), container, false);
            initView();
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    //初始化布局文件
    protected abstract int initLayout();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    public void intentJump(Class cla, int flag) {
        Intent intent = new Intent(getActivity(), cla);
        intent.setFlags(flag);
        startActivity(intent);
    }


    public void intentSkip(Class cla, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cla);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showToast(String msg) {
        if (Thread != null){
            Looper.prepare();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }else {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }

    }

    public void showAlert(Class cla, int flags, String confirm, String cancel){
        new AlertDialog.Builder(getActivity())
                .setMessage("是否确定退出?")//提示消息
                //.setIcon(R.mipmap.ic_launcher)//设置图标
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    //点击确定按钮执行的事件
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intentJump(cla, flags);
                        showToast(confirm);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    //点击取消按钮执行的事件
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast(cancel);
                    }
                })
                .create() //创建对话框
                .show(); //显示对话框
    }


    /**
     * 子类可通过此方法直接拿到VideoViewManager
     */
    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }

    protected String findByKey(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key, "");
    }

    protected void insertVal(String key, String val) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }
}
