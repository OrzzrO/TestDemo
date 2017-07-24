package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;

/**
 * Created by user on 2017/7/24.
 */

public interface DailyContract {

    interface View extends BaseView{

        void showContent(DailyListBean info);

        void showMoreContent(String date, DailyBeforeListBean info);

        void doInterval(int currentCount);

    }

    interface Presenter extends BasePresenter<View>{

        void getDailyData();

        void getBeforeData(String date);

        void startInterval();

        void stopInterval();

        void insertReadToDB(int id);

    }
}
