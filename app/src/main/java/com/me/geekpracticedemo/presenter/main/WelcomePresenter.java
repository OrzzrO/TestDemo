package com.me.geekpracticedemo.presenter.main;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.main.WelcomeContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.WelcomeBean;
import com.me.geekpracticedemo.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by user on 2017/7/20.
 */

public class WelcomePresenter extends RxPresenter<WelcomeContract.View> implements WelcomeContract.Presenter{


    private static final String RES = "1080*1776";
    private static final long COUNT_DOWN_TIME = 2200;
    private DataManager mDataManager;


    @Inject
    public WelcomePresenter(DataManager dataManager){
        this.mDataManager = dataManager;
    }

    @Override
    public void getWelcomeData() {
            addSubscribe(mDataManager.fetchWelcomeInfo(RES)
            .compose(RxUtil.<WelcomeBean>rxSchedulerHelper())
            .subscribe(new Consumer<WelcomeBean>() {
                @Override
                public void accept(WelcomeBean welcomeBean) throws Exception {
                    mView.showContent(welcomeBean);
                    startCountDown();

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.jumpToMain();
                }
            })

     );
    }

    /**
     * 延时进入,timer操作符.用interval替代.
     */
    private void startCountDown() {
        addSubscribe(Flowable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                         //   mView.jumpToMain();
                    }
                }));

    }
}
