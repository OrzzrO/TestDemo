package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.SectionContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.SectionListBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/24.
 */

public class SectionPresenter extends RxPresenter<SectionContract.View> implements SectionContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public SectionPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getSectionData() {
        addSubscribe(mDataManager.fetchSectionListInfo()
        .compose(RxUtil.<SectionListBean>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<SectionListBean>(mView) {
            @Override
            public void onNext(SectionListBean sectionListBean) {

                mView.showContent(sectionListBean);
            }
        }));

    }
}
