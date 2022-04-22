package com.yyzy.myapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends FragmentPagerAdapter {
    private String mTitles[];
    private ArrayList<Fragment> listFragment;

    public MyAdapter(@NonNull FragmentManager fm, String mTitles[], ArrayList<Fragment> listFragment) {
        super(fm);
        this.mTitles = mTitles;
        this.listFragment = listFragment;

    }
    @NonNull
    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
