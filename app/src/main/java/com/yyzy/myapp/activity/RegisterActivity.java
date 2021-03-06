package com.yyzy.myapp.activity;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.yyzy.myapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {


    private Button mbtnRegister;
    private EditText edRegisterUser, edRegisterPwd;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        edRegisterUser = findViewById(R.id.edRegister_user);
        edRegisterPwd = findViewById(R.id.edRegister_pwd);
        mbtnRegister = findViewById(R.id.btnRegister_register);
    }

    @Override
    protected void initData() {
        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edRegisterUser.getText().toString().trim();
                String pwd = edRegisterPwd.getText().toString().trim();
                Register(user, pwd);
            }
        });
        mbtnRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.shape_register_activity_btn);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.shape_login_activity_btn2);
                }
                return false;
            }
        });
    }



    private void Register(String user, String pwd) {
        if (TextUtils.isEmpty(user)) {
            showToast("??????????????????????????????");
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            showToast("??????????????????????????????");
            return;
        }else if (!checkPassword(pwd)){
            showToast("???????????????????????????????????????????????????????????????8?????????16???");
            return;
        }else if (!checkUsername(user)){
            showToast("????????????????????????????????????????????????6?????????12???");
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formbody = new FormBody.Builder();
        formbody.add("user", user);
        formbody.add("pwd", pwd);
        RequestBody requestBody = formbody.build();
        Request request = new Request.Builder()
                .url(BASE_URLT + "/user/register")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("TAG", "onFailureError: " + e.getMessage());
                Looper.prepare();
                showToast("???????????????????????????????????????");
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String resultStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultStr.equals("success")) {
                            showToast("?????????????????????");
                            edRegisterUser.setText("");
                            edRegisterPwd.setText("");
                        } else if (resultStr.equals("error")) {
                            showToast("?????????????????????");
                            return;
                        } else {
                            showToast("???????????????????????????????????????");
                            return;
                        }
                    }
                });
            }
        });
    }
}