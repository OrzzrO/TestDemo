package com.me.geekpracticedemo.base;

import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.di.component.ActivityComponent;
import com.me.geekpracticedemo.di.component.DaggerActivityComponent;
import com.me.geekpracticedemo.di.module.ActivityModule;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/19.
 * mvp的基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {

    @Inject
    protected T mPresenter;


    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
            .appComponent(App.getAppComponent())
            .activityModule(getActivityModule())
            .build();
    }


    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
        }
        super.onDestroy();


    }

    /**
     * 每个类都会要调用inject方法,所以抽出来给基类
     */
    protected abstract void initInject();
}
