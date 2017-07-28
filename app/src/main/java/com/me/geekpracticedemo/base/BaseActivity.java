package com.me.geekpracticedemo.base;

import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

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
    public void useNightMode(boolean isNight) {
        Log.w("hongTest", "post事件,调用这里 - useNightMode:  invoke--" );
        if (isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();

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

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }



    /**
     * 每个类都会要调用inject方法,所以抽出来给基类
     */
    protected abstract void initInject();
}
