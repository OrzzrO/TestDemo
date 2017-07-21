package com.me.geekpracticedemo.base;

/**
 * Created by user on 2017/7/19.
 * view的基类
 */

public interface BaseView {

    void showErrorMsg(String msg);

    void useNightMode(boolean isNight);

    //state
    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
