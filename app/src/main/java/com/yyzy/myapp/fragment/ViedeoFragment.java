package com.yyzy.myapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
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
import com.yyzy.myapp.adapter.RecommendAdapter;
import com.yyzy.myapp.adapter.ViedeoAdapter;
import com.yyzy.myapp.api.Api;
import com.yyzy.myapp.api.CallBack;
import com.yyzy.myapp.entity.ViedeoDataEntity;

import java.util.ArrayList;
import java.util.List;


public class ViedeoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private List<ViedeoDataEntity> datas = new ArrayList<>();
    private ViedeoAdapter viedeoAdapter;
    private LinearLayoutManager linearManager;
    private String title;


    public ViedeoFragment() {

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    viedeoAdapter.setDatas(datas);
                    viedeoAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public static ViedeoFragment newInstance(String title) {
        ViedeoFragment fragment = new ViedeoFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_viedeo;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        smartRefreshLayout = mRootView.findViewById(R.id.smartRefreshLayout);
    }

    @Override
    protected void initData() {
        linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearManager);
        viedeoAdapter = new ViedeoAdapter(getActivity());
        recyclerView.setAdapter(viedeoAdapter);
        getListDatas(true);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //smartRefreshLayout.finishRefresh(2000);
                getListDatas(true);
                showToast("刷新成功！");
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //smartRefreshLayout.finishLoadMore(2000,false,true);
                getListDatas(false);
            }
        });
    }

    private void getListDatas(boolean isRefresh) {
        new Api().getRequest(new CallBack() {
            @Override
            public void onSuccess(String res) {
                //showToast(res);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRefresh) {
                            smartRefreshLayout.finishRefresh(true);
                        } else {
                            smartRefreshLayout.finishLoadMore(true);
                        }
                        List<ViedeoDataEntity> dataEntity = new Gson().fromJson(res, new TypeToken<List<ViedeoDataEntity>>() {
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
        }, Api.API_VIDEO_URLT);
    }
}