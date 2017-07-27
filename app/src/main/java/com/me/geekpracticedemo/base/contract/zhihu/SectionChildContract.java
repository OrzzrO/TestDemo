package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.SectionChildListBean;

/**
 * Created by user on 2017/7/27.
 */

public interface SectionChildContract {

    interface View extends BaseView{

        void showContent(SectionChildListBean sectionChildListBean);
    }

    interface Presenter extends BasePresenter<View>{

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }

}
