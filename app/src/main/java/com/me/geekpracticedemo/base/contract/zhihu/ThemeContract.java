package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.ThemeListBean;

/**
 * Created by user on 2017/7/27.
 */

public interface ThemeContract {

    interface View extends BaseView{

        void  showContent(ThemeListBean themeListBean);
    }

    interface Presenter extends BasePresenter<View>{

        void getThemeData();
    }
}
