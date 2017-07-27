package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.ThemeContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.ThemeListBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/27.
 */

public class ThemePresenter extends RxPresenter<ThemeContract.View> implements ThemeContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public ThemePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getThemeData() {
        mDataManager.fetchDailyThemeListInfo()
            .compose(RxUtil.<ThemeListBean>rxSchedulerHelper())
            .subscribe(new CommonSubscriber<ThemeListBean>(mView) {
                @Override
                public void onNext(ThemeListBean themeListBean) {
                    mView.showContent(themeListBean);
                }
            });
    }


}
