package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.me.geekpracticedemo.ui.zhihu.fragment.CommentFragment;

import java.util.List;

/**
 * Created by user on 2017/7/26.
 */

public class CommentMainAdapter extends FragmentPagerAdapter {

    List<CommentFragment> mFragments ;

    public CommentMainAdapter(FragmentManager fm, List<CommentFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }



}
