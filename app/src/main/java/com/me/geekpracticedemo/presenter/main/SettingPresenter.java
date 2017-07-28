package com.me.geekpracticedemo.presenter.main;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.main.SettingContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.http.response.MyHttpResponse;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/28.
 */

public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter {


    private DataManager mDataManager;

    @Inject
    public SettingPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void checkVersion(final String currentVersion) {
        addSubscribe(mDataManager.fetchVersionInfo()
        .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
        .compose(RxUtil.<VersionBean>handleMyResult())
        .subscribeWith(new CommonSubscriber<VersionBean>(mView, "获取版本信息失败") {
            @Override
            public void onNext(VersionBean versionBean) {
                if (Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""))) {
                    mView.showUpdateDialog(versionBean);
                } else {
                    mView.showErrorMsg("已经是最新版本~");
                }

            }
        }));


    }

    @Override
    public void setNightModeState(boolean b) {
        mDataManager.setNightModeState(b);
    }

    @Override
    public void setNoImageState(boolean b) {
        mDataManager.setNoImageState(b);
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mDataManager.setAutoCacheState(state);
    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }
}
