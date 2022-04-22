package com.yyzy.myapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yyzy.myapp.R;
import com.yyzy.myapp.activity.CollectItemActivity;
import com.yyzy.myapp.adapter.CollectAdapter;
import com.yyzy.myapp.adapter.ViedeoAdapter;
import com.yyzy.myapp.api.Api;
import com.yyzy.myapp.api.CallBack;
import com.yyzy.myapp.entity.CategoryEntity;
import com.yyzy.myapp.entity.CollectEntity;
import com.yyzy.myapp.entity.ViedeoDataEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CollectFragment extends BaseFragment {

    private RecyclerView recycler;
    private SmartRefreshLayout smartRefresh;
    private List<CollectEntity> datas = new ArrayList<>();
    private CollectAdapter collectAdapter;
    private LinearLayoutManager linearManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    collectAdapter.setData(datas);
                    collectAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public CollectFragment() {

    }

    public static CollectFragment newInstance() {
        CollectFragment fragment = new CollectFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initView() {
        recycler = mRootView.findViewById(R.id.recycler);
        smartRefresh = mRootView.findViewById(R.id.smartRefresh);
    }

    @Override
    protected void initData() {
        linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearManager);
        collectAdapter = new CollectAdapter(getActivity());
        collectAdapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
                CollectEntity datas = (CollectEntity) obj;
                Bundle bundle = new Bundle();
                bundle.putString("author", datas.getAuthorName());
                bundle.putString("imgtitle", datas.getHeaderUrl());
                bundle.putString("one", datas.getNewsTitle());
                bundle.putString("date", datas.getReleaseDate());
                intentSkip(CollectItemActivity.class, bundle);
            }
        });
        recycler.setAdapter(collectAdapter);
        getNews(true);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //smartRefreshLayout.finishRefresh(2000);
                getNews(true);
                showToast("刷新成功！");
            }
        });
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //smartRefreshLayout.finishLoadMore(2000,false,true);
                getNews(false);
            }
        });
    }

    private void getNews(boolean isRefresh) {
        new Api().getRequest(new CallBack() {
            @Override
            public void onSuccess(String res) {
                //showToast(res);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            smartRefresh.finishRefresh(true);
                        } else {
                            smartRefresh.finishLoadMore(true);
                        }
                        List<CollectEntity> dataEntity = new Gson().fromJson(res, new TypeToken<List<CollectEntity>>() {
                        }.getType());
                        if (dataEntity != null && dataEntity.size() > 0) {
                            if (isRefresh) {
                                datas = dataEntity;
                            } else {
                                datas.addAll(dataEntity);
                            }
                            mHandler.sendEmptyMessage(0);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        }, Api.API_NEWS_URL);
    }
}