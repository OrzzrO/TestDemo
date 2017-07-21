package com.me.geekpracticedemo.base;

/**
 * Created by user on 2017/7/21.
 */

public class RootFragment<T extends BasePresenter> extends BaseFragment<T> {
    @Override
    protected void initInject() {
    }

    @Override
    protected void initEventAndData() {
    }

    @Override
    protected int getlayoutId() {
        return 0;
    }
}
