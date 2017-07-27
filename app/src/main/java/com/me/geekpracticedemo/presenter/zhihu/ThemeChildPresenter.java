package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.ThemeChildContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.ThemeChildListBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by user on 2017/7/27.
 */

public class ThemeChildPresenter extends RxPresenter<ThemeChildContract.View> implements ThemeChildContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public ThemeChildPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getThemeChildData(int id) {
            addSubscribe(mDataManager.fetchThemeChildListInfo(id)
            .compose(RxUtil.<ThemeChildListBean>rxSchedulerHelper())
            .map(new Function<ThemeChildListBean, ThemeChildListBean>() {
                @Override
                public ThemeChildListBean apply(ThemeChildListBean themeChildListBean) throws Exception {
                    List<ThemeChildListBean.StoriesBean> list = themeChildListBean.getStories();
                    for (ThemeChildListBean.StoriesBean item : list) {
                        item.setReadState(mDataManager.queryNewsId(item.getId()));
                    }
                    return themeChildListBean;
                }
            })
            .subscribeWith(new CommonSubscriber<ThemeChildListBean>(mView) {
                @Override
                public void onNext(ThemeChildListBean themeChildListBean) {
                        mView.showContent(themeChildListBean);
                }
            })
            );
    }

    @Override
    public void insertReadToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
