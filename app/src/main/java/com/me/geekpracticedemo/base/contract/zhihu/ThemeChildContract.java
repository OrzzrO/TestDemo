package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.ThemeChildListBean;

/**
 * Created by user on 2017/7/27.
 */

public interface ThemeChildContract {

    interface View extends BaseView{

        void showContent(ThemeChildListBean themeChildListBean);

    }

    interface Presenter extends BasePresenter<View>{

        void getThemeChildData(int id);

        void insertReadToDB(int id);
    }
}
