package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.SectionContract;
import com.me.geekpracticedemo.model.bean.SectionListBean;
import com.me.geekpracticedemo.presenter.zhihu.SectionPresenter;
import com.me.geekpracticedemo.ui.zhihu.adapter.SectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by user on 2017/7/24.
 */

public class SectionFragment
    extends RootFragment<SectionPresenter>
    implements SectionContract.View {
    @BindView(R.id.view_main)
    RecyclerView mRvSectionList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;

    List<SectionListBean.DataBean> mList;
    private SectionAdapter mAdapter;

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
        mAdapter = new SectionAdapter(mContext, mList);
        mRvSectionList.setLayoutManager(new GridLayoutManager(mContext,2));
        mRvSectionList.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getSectionData();
            }
        });
        mPresenter.getSectionData();
        stateLoading();

    }

    @Override
    public void showContent(SectionListBean sectionListBean) {
        if (mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }

        stateMain();
        mList.clear();
        mList.addAll(sectionListBean.getData());
        mAdapter.notifyDataSetChanged();
    }

}
