package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.HotContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.HotListBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by user on 2017/7/24.
 */

public class HotPresenter extends RxPresenter<HotContract.View> implements HotContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public HotPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getHotData() {
        addSubscribe(mDataManager.fetchHotListInfo()
        .compose(RxUtil.<HotListBean>rxSchedulerHelper())
        .map(new Function<HotListBean, HotListBean>() {
            @Override
            public HotListBean apply(HotListBean hotListBean) throws Exception {
                List<HotListBean.RecentBean> list = hotListBean.getRecent();
                for (HotListBean.RecentBean item : list) {
                    item.setReadState(mDataManager.queryNewsId(item.getNews_id()));
                }

                return hotListBean;
            }
        })
        .subscribeWith(new CommonSubscriber<HotListBean>(mView) {
            @Override
            public void onNext(HotListBean hotListBean) {
                mView.showContent(hotListBean);
            }
        }));
    }

    @Override
    public void insertReadToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
