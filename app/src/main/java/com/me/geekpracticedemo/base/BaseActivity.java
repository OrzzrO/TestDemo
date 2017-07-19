package com.me.geekpracticedemo.base;

/**
 * Created by user on 2017/7/19.
 * mvp的基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {

    protected T mPresenter;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }

    }

    /**
     * 每个类都会要调用inject方法,所以抽出来给基类
     */
    protected abstract void initInject();
}
