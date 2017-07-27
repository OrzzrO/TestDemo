package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.ThemeContract;
import com.me.geekpracticedemo.model.bean.ThemeListBean;
import com.me.geekpracticedemo.presenter.zhihu.ThemePresenter;
import com.me.geekpracticedemo.ui.zhihu.activity.ThemeActivity;
import com.me.geekpracticedemo.ui.zhihu.adapter.ThemeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2017/7/24.
 */

public class ThemeFragment extends RootFragment<ThemePresenter> implements ThemeContract.View {
    @BindView(R.id.view_main)
    RecyclerView mRvThemeList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    List<ThemeListBean.OthersBean> mList = new ArrayList<>();
    private ThemeAdapter mAdapter;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.view_common_list;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mAdapter = new ThemeAdapter(mContext, mList);
        mRvThemeList.setLayoutManager(new GridLayoutManager(mContext,2));
        mRvThemeList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Intent intent = new Intent();
                intent.setClass(mContext, ThemeActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_THEME_ID, id);
                mContext.startActivity(intent);
            }
        });
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeData();
            }
        });
        mPresenter.getThemeData();
        stateLoading();
    }

    @Override
    public void showContent(ThemeListBean themeListBean) {
            if (mSwipeRefresh.isRefreshing()){
                mSwipeRefresh.setRefreshing(false);
            }
        Log.w("hongTest", "showContent:  themeListBean = " + themeListBean );
            stateMain();
            mList.clear();
            mList.addAll(themeListBean.getOthers());
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stateError() {
        super.stateError();
        if (mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }

    }
}
