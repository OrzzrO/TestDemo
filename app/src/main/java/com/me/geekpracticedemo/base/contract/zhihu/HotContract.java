package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.HotListBean;

/**
 * Created by user on 2017/7/24.
 */

public interface HotContract {

    interface View extends BaseView{

        void showContent(HotListBean hotListBean);
    }

    interface Presenter extends BasePresenter<View>{

        void getHotData();

        void insertReadToDB(int id);
    }
}
