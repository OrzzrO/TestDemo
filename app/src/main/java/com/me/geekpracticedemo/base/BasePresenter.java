package com.me.geekpracticedemo.base;

/**
 * Created by user on 2017/7/19.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);


    void detachView();
}
