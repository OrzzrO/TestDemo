package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.SimpleFragment;
import com.me.geekpracticedemo.ui.zhihu.adapter.ZhihuMainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2017/7/21.
 */

public class ZhiHuMainFragment extends SimpleFragment {
    @BindView(R.id.tab_zhihu_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_zhihu_main)
    ViewPager mViewPager;

    String[] tabTitle = new String[]{"日报","主题","专栏","热门"};
    List<Fragment> mFragments = new ArrayList<>();
    private ZhihuMainAdapter mAdapter;

    @Override
    protected void initEventAndData() {
        mFragments.add(new DailyFragment());
        mFragments.add(new ThemeFragment());
        mFragments.add(new SectionFragment());
        mFragments.add(new HotFragment());
        mAdapter = new ZhihuMainAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);

        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[3]));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
        mTabLayout.getTabAt(2).setText(tabTitle[2]);
        mTabLayout.getTabAt(3).setText(tabTitle[3]);


    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_zhihu_main;
    }


}
