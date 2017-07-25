package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.ZhihuDetailContract;
import com.me.geekpracticedemo.model.DataManager;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/25.
 */

public class ZhihuDetailPresenter extends RxPresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public ZhihuDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getDetailData(int id) {
    }

    @Override
    public void getExtraData(int id) {
    }

    @Override
    public void insertLikeData() {
    }

    @Override
    public void deleteLikeData() {
    }

    @Override
    public void queryLikeData() {
    }

    @Override
    public void queryLikeData(int id) {
    }

    @Override
    public boolean getNoImageState() {
        return false;
    }

    @Override
    public boolean getAutoCacheState() {
        return false;
    }
}
