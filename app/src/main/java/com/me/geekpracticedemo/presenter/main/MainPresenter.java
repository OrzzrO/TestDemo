package com.me.geekpracticedemo.presenter.main;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.main.MainContract;
import com.me.geekpracticedemo.model.DataManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/21.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void checkVersion(String currentVersion) {
    }

    @Override
    public void checkPermission(RxPermissions rxPermissions) {
    }

    @Override
    public void setNightModeState(boolean b) {
    }

    @Override
    public void setCurrentItem(int index) {
    }

    @Override
    public int getCurrentItem() {
        return 0;
    }

    @Override
    public void setVersionPoint(boolean b) {
    }

    @Override
    public boolean getVersionPoint() {
        return false;
    }
}
