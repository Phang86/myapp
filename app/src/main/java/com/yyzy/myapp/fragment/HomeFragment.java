
package com.yyzy.myapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyzy.myapp.R;
import com.yyzy.myapp.adapter.HomeAdapter;
import com.yyzy.myapp.api.Api;
import com.yyzy.myapp.api.CallBack;
import com.yyzy.myapp.entity.CategoryEntity;
import com.yyzy.myapp.entity.ViedeoDataEntity;
import com.yyzy.myapp.view.FixedViewPager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private String titles[] = {"关注","推荐"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private HomeAdapter homeAdapter;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void initData() {
        mFragments.add(new ViedeoFragment().newInstance(titles[0]));
        mFragments.add(new RecommendFragment().newInstance(titles[1]));
        viewPager.setOffscreenPageLimit(mFragments.size());
        homeAdapter = new HomeAdapter(getFragmentManager(), titles, mFragments);
        viewPager.setAdapter(homeAdapter);
        slidingTabLayout.setViewPager(viewPager);

    }
}