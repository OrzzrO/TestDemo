package com.me.geekpracticedemo.ui.zhihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.SimpleActivity;
import com.me.geekpracticedemo.ui.zhihu.adapter.CommentMainAdapter;
import com.me.geekpracticedemo.ui.zhihu.fragment.CommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2017/7/26.
 */

public class CommentActivity extends SimpleActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_comment)
    TabLayout mTabLayout;
    @BindView(R.id.vp_comment)
    ViewPager mViewPager;

    List<CommentFragment> mFragments = new ArrayList<>();
    private CommentMainAdapter mAdapter;


    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        int allNum = intent.getExtras().getInt(Constants.IT_ZHIHU_COMMENT_ALL_NUM);
        int shortNum = intent.getExtras().getInt(Constants.IT_ZHIHU_COMMENT_SHORT_NUM);
        int longNum = intent.getExtras().getInt(Constants.IT_ZHIHU_COMMENT_LONG_NUM);
        int id = intent.getExtras().getInt(Constants.IT_ZHIHU_COMMENT_ID);
        setToolBar(mToolbar,String.format("%d条评论",allNum));

        CommentFragment shortCommentFragment = new CommentFragment();
        Bundle shortBundle = new Bundle();
        shortBundle.putInt("id",id);
        shortBundle.putInt("kind",0);
        shortCommentFragment.setArguments(shortBundle);

        CommentFragment longCommentFragment = new CommentFragment();
        Bundle longBundle = new Bundle();
        longBundle.putInt("id",id);
        longBundle.putInt("kind",1);
        longCommentFragment.setArguments(longBundle);

        mFragments.add(shortCommentFragment);
        mFragments.add(longCommentFragment);
        mAdapter = new CommentMainAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText(String.format("短评论(%d)",shortNum)));
        mTabLayout.addTab(mTabLayout.newTab().setText(String.format("长评论(%d)",longNum)));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setText(String.format("短评论(%d)",shortNum));
        mTabLayout.getTabAt(1).setText(String.format("长评论(%d)",longNum));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }


}
