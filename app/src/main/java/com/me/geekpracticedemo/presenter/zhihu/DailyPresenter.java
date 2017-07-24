package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.DailyContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.util.RxBus;
import com.me.geekpracticedemo.weight.CommonSubscriber;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2017/7/24.
 * 期为明天时，取latest接口的数据
 * 其他日期，取before接口的数据
 */

public class DailyPresenter extends RxPresenter<DailyContract.View> implements DailyContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public DailyPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DailyContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CalendarDay.class)
        .subscribeOn(Schedulers.io())
        .map(new Function<CalendarDay, String>() {
            @Override
            public String apply(CalendarDay calendarDay)
                throws Exception {
                return null;
            }
        })
        .filter(new Predicate<String>() {
            @Override
            public boolean test(String s)
                throws Exception {
                return false;
            }
        })
        .observeOn(Schedulers.io())
        .flatMap(new Function<String, Flowable<DailyBeforeListBean>>() {
            @Override
            public Flowable<DailyBeforeListBean> apply(String s)
                throws Exception {
                return null;
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Function<DailyBeforeListBean, DailyBeforeListBean>() {
            @Override
            public DailyBeforeListBean apply(DailyBeforeListBean dailyBeforeListBean)
                throws Exception {
                return null;
            }
        })
        .subscribeWith(new CommonSubscriber<DailyBeforeListBean>(mView) {
            @Override
            public void onNext(DailyBeforeListBean dailyBeforeListBean) {
            }
        }));

    }

    @Override
    public void getDailyData() {
    }

    @Override
    public void getBeforeData(String date) {
    }

    @Override
    public void startInterval() {
    }

    @Override
    public void stopInterval() {
    }

    @Override
    public void insertReadToDB(int id) {
    }
}
