package com.me.geekpracticedemo.base.contract;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.WelcomeBean;

/**
 * Created by user on 2017/7/20.
 *
 * 业务逻辑为.点击图标,启动App,判断是否是第一次登陆,
 * 如果是,首先展示的是欢迎界面
 * 如果不是,直接进入主界面.
 */

public interface WelcomeContract {

    interface View extends BaseView {


        void showContent(WelcomeBean welcomeBean);

        void jumpToMain();
    }

    interface Presenter extends BasePresenter<View>{

        void getWelcomeData();

    }
}
