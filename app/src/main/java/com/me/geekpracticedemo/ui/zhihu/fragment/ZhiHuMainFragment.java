package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.SimpleFragment;

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

    @Override
    protected void initEventAndData() {
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_zhihu_main;
    }


}
