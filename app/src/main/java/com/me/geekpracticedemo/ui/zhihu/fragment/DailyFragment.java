package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.DailyContract;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.presenter.zhihu.DailyPresenter;
import com.me.geekpracticedemo.ui.zhihu.activity.CalendarActivity;
import com.me.geekpracticedemo.ui.zhihu.activity.ZhihuDetailActivity;
import com.me.geekpracticedemo.ui.zhihu.adapter.DailyAdapter;
import com.me.geekpracticedemo.util.CircularAnimUtil;
import com.me.geekpracticedemo.util.DateUtil;
import com.me.geekpracticedemo.util.RxBus;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by user on 2017/7/24.
 */

public class DailyFragment
    extends RootFragment<DailyPresenter>
    implements DailyContract.View {
    @BindView(R.id.view_main)
    RecyclerView mRvDailyList;
    @BindView(R.id.fab_calender)
    FloatingActionButton mFabCalender;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;


    private String mCurrentDate;
    private DailyAdapter mAdapter;
    List<DailyListBean.StoriesBean> mList = new ArrayList<>();
    private boolean isDataReady = false;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        mCurrentDate = DateUtil.getCurrentDate();
        mAdapter = new DailyAdapter(mContext, mList);
        mAdapter.setOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View shareView) {
                mPresenter.insertReadToDB(mList.get(position).getId());
                mAdapter.setReadState(position,true);
                if (mAdapter.getIsBefore()){
                    mAdapter.notifyItemChanged(position + 1);
                }else{
                    mAdapter.notifyItemChanged(position + 2);
                }
                Intent intent = new Intent();
                intent.setClass(mContext, ZhihuDetailActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_DETAIL_ID,mList.get(position).getId());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity,shareView,"shareView");
                mContext.startActivity(intent,options.toBundle());
            }
        });
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCurrentDate.equals(DateUtil.getTomorrowDate())) {
                    mPresenter.getDailyData();
                } else {
                    int year = Integer.valueOf(mCurrentDate.substring(0, 4));
                    int month = Integer.valueOf(mCurrentDate.substring(4, 6));
                    int day = Integer.valueOf(mCurrentDate.substring(6, 8));
                    CalendarDay date = CalendarDay.from(year, month - 1, day);
                    RxBus.getDefault().post(date);
                }
            }
        });
        mRvDailyList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvDailyList.setAdapter(mAdapter);
        stateLoading();
        mPresenter.getDailyData();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isDataReady) {
            mPresenter.startInterval();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stopInterval();
    }

    @Override
    public void showContent(DailyListBean info) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        stateMain();
        mList = info.getStories();
        mCurrentDate = String.valueOf(Integer.valueOf(info.getDate()) + 1);

        mAdapter.addDailyData(info);
        isDataReady = true;
        mPresenter.startInterval();
    }

    /**
     * 过往数据
     * @param date
     * @param info
     */
    @Override
    public void showMoreContent(String date, DailyBeforeListBean info) {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
        stateMain();
        isDataReady = false;
        mPresenter.stopInterval();
        mList = info.getStories();
        mCurrentDate = String.valueOf(Integer.valueOf(info.getDate()));
        mAdapter.addDailyBeforeData(info);
    }

    @Override
    public void doInterval(int currentCount) {
        mAdapter.changeTopPager(currentCount);

    }

    @Override
    public void stateError() {
        super.stateError();
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }


    @OnClick(R.id.fab_calender)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(mContext,CalendarActivity.class);
        CircularAnimUtil.startActivity(mActivity,intent,mFabCalender,R.color.fab_bg);
    }
}
