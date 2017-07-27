package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.ZhihuDetailContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.DetailExtraBean;
import com.me.geekpracticedemo.model.bean.RealmLikeBean;
import com.me.geekpracticedemo.model.bean.ZhihuDetailBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/25.
 */

public class ZhihuDetailPresenter extends RxPresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter {

    private DataManager mDataManager;
    private ZhihuDetailBean mData;

    @Inject
    public ZhihuDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getDetailData(int id) {
        addSubscribe(mDataManager.fetchDetailInfo(id)
        .compose(RxUtil.<ZhihuDetailBean>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<ZhihuDetailBean>(mView) {
            @Override
            public void onNext(ZhihuDetailBean zhihuDetailBean) {

                mData = zhihuDetailBean;
                mView.showContent(zhihuDetailBean);

            }
        }));


    }

    @Override
    public void getExtraData(int id) {

        addSubscribe(mDataManager.fetchDetailExtraInfo(id)
        .compose(RxUtil.<DetailExtraBean>rxSchedulerHelper())
        .subscribeWith(new CommonSubscriber<DetailExtraBean>(mView,"加载额外信息失败~~") {
            @Override
            public void onNext(DetailExtraBean detailExtraBean) {
                mView.showExtralInfo(detailExtraBean);
            }
        }));


    }

    @Override
    public void insertLikeData() {
        if (mData != null){
            RealmLikeBean bean = new RealmLikeBean();
            bean.setId(String.valueOf(mData.getId()));
            bean.setImage(mData.getImage());
            bean.setTitle(mData.getTitle());
            bean.setType(Constants.TYPE_ZHIHU);
            bean.setTime(System.currentTimeMillis());
            mDataManager.insertLikeBean(bean);
        }else{
            mView.showErrorMsg("操作失败");
        }

    }

    @Override
    public void deleteLikeData() {
        if (mData != null) {
            mDataManager.deleteLikeBean(String.valueOf(mData.getId()));
        } else {
            mView.showErrorMsg("操作失败");
        }
    }


    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mDataManager.queryLikeId(String.valueOf(id)));
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }
}
