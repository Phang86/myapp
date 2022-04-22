package com.yyzy.myapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yyzy.myapp.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends BaseActivity {

    private EditText edUser, edPwd;
    private Button btnLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        edUser = findViewById(R.id.edLogin_user);
        edPwd = findViewById(R.id.edLogin_pwd);
        btnLogin = findViewById(R.id.btnLogin_login);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUser.getText().toString().trim();
                String pwd = edPwd.getText().toString().trim();
                login(user, pwd);
            }
        });
        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    v.setBackgroundResource(R.drawable.shape_login_activity_btn2);
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    v.setBackgroundResource(R.drawable.shape_login_activity_btn);
                }
                return false;
            }
        });
    }

    private void login(String user, String pwd) {
        if (TextUtils.isEmpty(user)) {
            showToast("账号不能为空哦！");
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            showToast("密码不能为空哦！");
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
//        Map map = new HashMap();
//        map.put("username", user);
//        map.put("password", pwd);
//        JSONObject jsonObject = new JSONObject(map);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonStr);
        FormBody.Builder formbody = new FormBody.Builder();
        formbody.add("user", user);
        formbody.add("pwd", pwd);
        RequestBody requestBody = formbody.build();
        Request request = new Request.Builder()
                .url(BASE_URLT + "/user/login")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG", "onFailure: " + e.getMessage().toString());
                Looper.prepare();
                showToast("登录失败！服务器连接超时！");
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String resultStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultStr.equals("success")) {
                            intentJump(HomeActivity.class);
                            showToast("您已登录成功！");
                        } else if (resultStr.equals("error")) {
                            showToast("登录失败！账号或密码不正确！");
                            return;
                        } else {
                            showToast("登录失败！服务器连接超时！");
                            return;
                        }
                    }
                });
            }
        });
    }
}