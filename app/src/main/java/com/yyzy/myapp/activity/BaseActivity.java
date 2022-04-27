package com.yyzy.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class BaseActivity extends AppCompatActivity {
    public static final String BASE_URLT = "http://192.168.1.102:8080/myapp";
    public static final String BASE_URL = "http://192.168.88.202:8080/myapp";
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        context = this;
        initView();
        initData();
    }

    //初始化布局文件
    protected abstract int initLayout();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    public void intentJump(Class cla) {
        startActivity(new Intent(context,cla));
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }

    // 验证用户名是否匹配指定格式的方法
    public boolean checkUsername(String user) {
        //用户名只能大小写字母，长度不低于6不大于12。
        String regexp = "^[a-zA-Z]{6,12}$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(user);
        return matcher.matches();
    }


    // 验证密码是否匹配指定格式的方法
    public boolean checkPassword(String pwd) {
        //密码只能用大小写字母、数字组合，长度不低于8不大于16。
        String regexp = "^[0-9a-zA-Z]{8,16}$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }
}
