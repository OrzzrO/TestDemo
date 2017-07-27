package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.SectionChildContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.SectionChildListBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by user on 2017/7/27.
 */

public class SectionChildPresenter
    extends RxPresenter<SectionChildContract.View>
    implements SectionChildContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public SectionChildPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void getThemeChildData(int id) {
        addSubscribe(mDataManager
                         .fetchSectionChildListInfo()
                         .compose(RxUtil.<SectionChildListBean>rxSchedulerHelper())
                         .map(new Function<SectionChildListBean, SectionChildListBean>() {
                             @Override
                             public SectionChildListBean apply(SectionChildListBean sectionChildListBean)
                                 throws Exception {
                                 List<SectionChildListBean.StoriesBean> list = sectionChildListBean.getStories();
                                 for (SectionChildListBean.StoriesBean item : list) {
                                     item.setReadState(mDataManager.queryNewsId(item.getId()));
                                 }
                                 return sectionChildListBean;
                             }
                         })
                         .subscribeWith(new CommonSubscriber<SectionChildListBean>(mView) {
                             @Override
                             public void onNext(SectionChildListBean sectionChildListBean) {
                                 mView.showContent(sectionChildListBean);

                             }
                         }));


    }

    @Override
    public void insertReadToDB(int id) {
        mDataManager.insertNewsId(id);
    }
}
