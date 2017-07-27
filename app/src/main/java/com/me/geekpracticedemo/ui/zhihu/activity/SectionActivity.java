package com.me.geekpracticedemo.ui.zhihu.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RootActivity;
import com.me.geekpracticedemo.base.contract.zhihu.SectionChildContract;
import com.me.geekpracticedemo.model.bean.SectionChildListBean;
import com.me.geekpracticedemo.presenter.zhihu.SectionChildPresenter;
import com.me.geekpracticedemo.ui.zhihu.adapter.SectionChildAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2017/7/27.
 */

public class SectionActivity extends RootActivity<SectionChildPresenter> implements SectionChildContract.View {


    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.view_main)
    RecyclerView mRvSetionList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private int mId;
    private String mTitle;
    private List<SectionChildListBean.StoriesBean> mList;
    private SectionChildAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_section;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        mTitle = intent.getStringExtra("title");
        setToolBar(mToolBar,mTitle);
        mList = new ArrayList<>();
        mAdapter = new SectionChildAdapter(mContext, mList);
        mRvSetionList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSetionList.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeChildData(mId);
            }
        });
        mAdapter.setOnItemClickListener(new SectionChildAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View shareView) {
                    mPresenter.insertReadToDB(mList.get(position).getId());
                mAdapter.setReadState(position,true);
                mAdapter.notifyItemChanged(position);
                Intent intent = new Intent();
                intent.setClass(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_DETAIL_ID, mList.get(position).getId());
                if (shareView != null) {
                    mContext.startActivity(intent, ActivityOptions
                        .makeSceneTransitionAnimation(mContext, shareView, "shareView").toBundle());
                } else {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mContext).toBundle());
                }

            }
        });
        mPresenter.getThemeChildData(mId);
        stateLoading();

    }

    @Override
    public void showContent(SectionChildListBean sectionChildListBean) {
        if (mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }

        stateMain();
        mList.clear();
        mList.addAll(sectionChildListBean.getStories());
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
