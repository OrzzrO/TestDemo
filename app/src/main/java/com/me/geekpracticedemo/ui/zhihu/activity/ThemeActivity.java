package com.me.geekpracticedemo.ui.zhihu.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RootActivity;
import com.me.geekpracticedemo.base.contract.zhihu.ThemeChildContract;
import com.me.geekpracticedemo.model.bean.ThemeChildListBean;
import com.me.geekpracticedemo.presenter.zhihu.ThemeChildPresenter;
import com.me.geekpracticedemo.ui.zhihu.adapter.ThemeChildAdapter;
import com.me.geekpracticedemo.util.ImageLoader;
import com.me.geekpracticedemo.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by user on 2017/7/27.
 */

public class ThemeActivity
    extends RootActivity<ThemeChildPresenter>
    implements ThemeChildContract.View {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.iv_theme_child_blur)
    ImageView mIvThemeChildBlur;
    @BindView(R.id.iv_theme_child_origin)
    ImageView mIvThemeChildOrigin;
    @BindView(R.id.tv_theme_child_des)
    TextView mTvThemeChildDes;
    @BindView(R.id.theme_child_appbar)
    AppBarLayout mAppBar;
    @BindView(R.id.view_main)
    RecyclerView mRvThemeChildList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    List<ThemeChildListBean.StoriesBean> mList;
    private ThemeChildAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_theme;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Intent intent = getIntent();
        final int id = intent.getExtras().getInt(Constants.IT_ZHIHU_THEME_ID);
        mList = new ArrayList<>();
        mAdapter = new ThemeChildAdapter(mContext, mList);
        mRvThemeChildList.setLayoutManager(new LinearLayoutManager(this));
        mRvThemeChildList.setAdapter(mAdapter);
        stateLoading();
        mPresenter.getThemeChildData(id);
        mAdapter.setOnItemClickListener(new ThemeChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View shareView) {
                mPresenter.insertReadToDB(mList.get(position).getId());
                mAdapter.setReadState(position,true);
                mAdapter.notifyItemChanged(position);
                Intent intent = new Intent();
                intent.setClass(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_DETAIL_ID, mList.get(position).getId());
                if (shareView != null){
                    mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mContext,shareView,"shareView").toBundle());
                }else{
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mContext).toBundle());
                }
            }
        });
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0){
                    mSwipeRefresh.setEnabled(true);
                }else{
                    mSwipeRefresh.setEnabled(false);
                    float rate = (float) (SystemUtil.dp2px(mContext, 256) + verticalOffset * 2) / SystemUtil.dp2px(mContext, 256);
                        if (rate > 0){
                            mIvThemeChildOrigin.setAlpha(rate);
                        }
                }

            }
        });
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getThemeChildData(id);
            }
        });
    }

    @Override
    public void showContent(ThemeChildListBean themeChildListBean) {
            if (mSwipeRefresh.isRefreshing()){
                mSwipeRefresh.setRefreshing(false);
            }
            stateMain();
            setToolBar(mToolBar,themeChildListBean.getName());
            mList.clear();
            mList.addAll(themeChildListBean.getStories());
            mAdapter.notifyDataSetChanged();
        ImageLoader.load(mContext,themeChildListBean.getBackground(),mIvThemeChildOrigin);
        Glide.with(mContext).load(themeChildListBean.getBackground()).bitmapTransform(new BlurTransformation(mContext)).into(mIvThemeChildBlur);
        mTvThemeChildDes.setText(themeChildListBean.getDescription());
    }

    @Override
    public void stateError() {
        super.stateError();
        if (mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }

    }
}
