package com.yyzy.myapp.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yyzy.myapp.R;
import com.yyzy.myapp.adapter.MyAdapter;
import com.yyzy.myapp.entity.TabEntity;
import com.yyzy.myapp.fragment.CollectFragment;
import com.yyzy.myapp.fragment.HomeFragment;
import com.yyzy.myapp.fragment.MeFragment;
import com.yyzy.myapp.view.FixedViewPager;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private String mTitles[] = {"首页", "资讯", "我的"};
    private int mIconUnSelects[] = {R.mipmap.home_unselect,
            R.mipmap.collect_unselect, R.mipmap.my_unselect};
    private int mIconSelecteds[] = {R.mipmap.home_selected,
            R.mipmap.collect_selected, R.mipmap.my_selected};
    private FixedViewPager viewPager;
    private CommonTabLayout commonTabLayout;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private ArrayList<CustomTabEntity> tabEntityTitle = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }


    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData() {
        listFragment.add(new HomeFragment().newInstance());
        listFragment.add(new CollectFragment().newInstance());
        listFragment.add(new MeFragment().newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            tabEntityTitle.add(new TabEntity(mTitles[i], mIconSelecteds[i], mIconUnSelects[i]));
        }
        commonTabLayout.setTabData(tabEntityTitle);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //实现Fragment与tab联动切换页面  左右滑动Fragment，tab栏也切换
                commonTabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setOffscreenPageLimit(listFragment.size());
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), mTitles, listFragment));
    }
}