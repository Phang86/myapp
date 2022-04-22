package com.yyzy.myapp;


import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yyzy.myapp.activity.LoginActivity;
import com.yyzy.myapp.activity.RegisterActivity;
import com.yyzy.myapp.activity.BaseActivity;

public class MainActivity extends BaseActivity{

    private Button btnLogin,btnRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentJump(LoginActivity.class);
            }
        });
        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.shape_login);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundResource(R.drawable.shape_login2);
                }
                return false;
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentJump(RegisterActivity.class);
            }
        });
        btnRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.shape_register);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundResource(R.drawable.shape_register2);
                }
                return false;
            }
        });
    }

}