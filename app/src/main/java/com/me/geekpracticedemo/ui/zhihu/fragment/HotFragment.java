package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.HotContract;
import com.me.geekpracticedemo.model.bean.HotListBean;
import com.me.geekpracticedemo.presenter.zhihu.HotPresenter;
import com.me.geekpracticedemo.ui.zhihu.activity.ZhihuDetailActivity;
import com.me.geekpracticedemo.ui.zhihu.adapter.HotAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by user on 2017/7/24.
 */

public class HotFragment
    extends RootFragment<HotPresenter>
    implements HotContract.View {
    @BindView(R.id.view_main)
    RecyclerView mRvHotList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    List<HotListBean.RecentBean> mList;
    HotAdapter mAdapter;


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
        mList = new ArrayList<>();
        stateLoading();
        mAdapter = new HotAdapter(mContext,mList);
        mRvHotList.setVisibility(View.INVISIBLE);
        mRvHotList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvHotList.setAdapter(mAdapter);
        mPresenter.getHotData();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getHotData();
            }
        });
        mAdapter.setOnItemClickListener(new HotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View shareView) {
                mPresenter.insertReadToDB(mList.get(position).getNews_id());
                mAdapter.setReadState(position,true);
                mAdapter.notifyItemChanged(position);
                Intent intent = new Intent();
                intent.setClass(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_DETAIL_ID, mList.get(position).getNews_id());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, shareView, "shareView");
                mContext.startActivity(intent,options.toBundle());

            }
        });

    }

    @Override
    public void showContent(HotListBean hotListBean) {
        if (mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }
        stateMain();
        mRvHotList.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(hotListBean.getRecent());
        mAdapter.notifyDataSetChanged();

    }


}
