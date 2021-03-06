package com.yyzy.myapp.fragment;

import android.content.pm.ActivityInfo;
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
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yyzy.myapp.R;
import com.yyzy.myapp.adapter.RecommendAdapter;
import com.yyzy.myapp.api.Api;
import com.yyzy.myapp.api.CallBack;
import com.yyzy.myapp.entity.ViedeoDataEntity;
import com.yyzy.myapp.listener.OnItemChildClickListener;
import com.yyzy.myapp.util.Tag;
import com.yyzy.myapp.util.Utils;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.player.VideoView;

public class MovieFragment extends BaseFragment implements OnItemChildClickListener {
    private RecyclerView recycler;
    private SmartRefreshLayout smartRefresh;
    private String title;
    private List<ViedeoDataEntity> datas = new ArrayList<>();
    private RecommendAdapter viedeoAdapter;
    private LinearLayoutManager linearManager;

    protected VideoView mVideoView;
    protected StandardVideoController mController;
    protected ErrorView mErrorView;
    protected CompleteView mCompleteView;
    protected TitleView mTitleView;


    /**
     * ?????????????????????
     */
    protected int mCurPos = -1;
    /**
     * ???????????????????????????????????????????????????????????????
     */
    protected int mLastPos = mCurPos;

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

    public MovieFragment() {
    }

    public static MovieFragment newInstance(String title) {
        MovieFragment fragment = new MovieFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void initView() {
        initVideoView();
        recycler = mRootView.findViewById(R.id.recycler);
        smartRefresh = mRootView.findViewById(R.id.smartRefresh);
    }

    protected void initVideoView() {
        mVideoView = new VideoView(getActivity());
        mVideoView.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //??????VideoViewManager?????????????????????
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new StandardVideoController(getActivity());
        mErrorView = new ErrorView(getActivity());
        mController.addControlComponent(mErrorView);
        mCompleteView = new CompleteView(getActivity());
        mController.addControlComponent(mCompleteView);
        mTitleView = new TitleView(getActivity());
        mController.addControlComponent(mTitleView);
        mController.addControlComponent(new VodControlView(getActivity()));
        mController.addControlComponent(new GestureView(getActivity()));
        mController.setEnableOrientation(true);
        mVideoView.setVideoController(mController);
    }

    @Override
    protected void initData() {
        linearManager = new LinearLayoutManager(getActivity());
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearManager);
        viedeoAdapter = new RecommendAdapter(getActivity());
        viedeoAdapter.setOnItemChildClickListener(this);
        recycler.setAdapter(viedeoAdapter);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getListDatas(true);
                showToast("???????????????");
            }
        });
        smartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getListDatas(false);
            }
        });
        getListDatas(true);
        recycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
    }

    private void getListDatas(boolean isRefresh) {
        Api a = new Api();
        a.getRequest(new CallBack() {
            @Override
            public void onSuccess(String res) {
                if (isRefresh) {
                    smartRefresh.finishRefresh(true);
                } else {
                    smartRefresh.finishLoadMore(true);
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
                } else {
                    if (isRefresh) {
                        showToast("???????????????");
                    } else {
                        showToast("??????????????????");
                    }
                }

            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        }, Api.API_MOVIE_URL);
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * ??????onPause????????????super????????????????????????
     * ????????????????????????????????????onPause?????????
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * ??????onResume????????????super????????????????????????
     * ????????????????????????????????????onResume?????????
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //???????????????????????????
        startPlay(mLastPos);
    }

    /**
     * PrepareView?????????
     */
    @Override
    public void onItemChildClick(int position) {
        startPlay(position);
    }

    /**
     * ????????????
     *
     * @param position ????????????
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        ViedeoDataEntity entity = datas.get(position);
        //????????????
//        String proxyUrl = ProxyVideoCacheManager.getProxy(getActivity()).getProxyUrl(entity.getPlayUrl());
//        mVideoView.setUrl(proxyUrl);
        mVideoView.setUrl(entity.getPlayUrl());
        mTitleView.setTitle(entity.getTitle());
        View itemView = linearManager.findViewByPosition(position);
        if (itemView == null) return;
        RecommendAdapter.ViewHolder viewHolder = (RecommendAdapter.ViewHolder) itemView.getTag();
        //?????????????????????PrepareView??????????????????????????????isDissociate???????????????true, ???????????????isDissociate?????????
        mController.addControlComponent(viewHolder.mPrepareView, true);
        Utils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        //???????????????VideoView?????????VideoViewManager????????????????????????????????????
        getVideoViewManager().add(mVideoView, Tag.LIST);
        mVideoView.start();
        mCurPos = position;


    }

    private void releaseVideoView() {
        //Log.e("TAG", "startPlay: " + getActivity().getExternalCacheDir());
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if (getActivity().getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }
}