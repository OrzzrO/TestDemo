package com.me.geekpracticedemo.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.di.component.DaggerFragmentComponent;
import com.me.geekpracticedemo.di.component.FragmentComponent;
import com.me.geekpracticedemo.di.module.FragmentModule;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/21.
 */

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView  {

    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
            .appComponent(App.getAppComponent())
            .fragmentModule(getFragmentModule())
            .build();
    }

    @NonNull
    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null){
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    protected abstract void initInject();
}
