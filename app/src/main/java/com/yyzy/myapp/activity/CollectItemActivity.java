package com.yyzy.myapp.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyzy.myapp.R;
import com.yyzy.myapp.api.Api;
import com.yyzy.myapp.api.CallBack;
import com.yyzy.myapp.entity.CollectEntity;
import com.yyzy.myapp.entity.MyEntity;

import java.util.ArrayList;
import java.util.List;

public class CollectItemActivity extends BaseActivity {

    private Button btn;
    private TextView one,two,titleName,titleAuthor,textThree,tvName;
    private ImageView imgTitle,bgImg;
    private List<CollectEntity> datas = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_collect_item;
    }

    @Override
    protected void initView() {
        btn = findViewById(R.id.callback);
        one = findViewById(R.id.textone);
        two = findViewById(R.id.texttwo);
        titleName = findViewById(R.id.title_Name);
        titleAuthor = findViewById(R.id.title_Author);
        bgImg = findViewById(R.id.big_img);
        imgTitle = findViewById(R.id.title_img);
        textThree = findViewById(R.id.textthree);
        tvName = findViewById(R.id.tvName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        String author = bundle.getString("author");
        String img = bundle.getString("imgtitle");
        String sum = bundle.getString("one");
        String date = bundle.getString("date");
        Glide.with(this)
                .load(img)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imgTitle);
        one.setText(sum);
        two.setText(sum);
        Glide.with(this)
                .load(img)
                .into(bgImg);
        titleName.setText(author);
        titleAuthor.setText(date);
        textThree.setText("\t\t\t\t"+sum+sum+sum+sum);
        tvName.setText(author);
    }
}
