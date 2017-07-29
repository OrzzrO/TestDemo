package com.me.geekpracticedemo.base.contract.main;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.VersionBean;

/**
 * Created by user on 2017/7/28.
 */

public interface SettingContract {

    interface View extends BaseView{

        void showUpdateDialog(VersionBean versionBean);
    }

    interface Presenter extends BasePresenter<SettingContract.View>{

        void checkVersion(String currentVersion);

        void setNightModeState(boolean b);

        void setNoImageState(boolean b);

        void setAutoCacheState(boolean b);

        boolean getNightModeState();

        boolean getNoImageState();

        boolean getAutoCacheState();
    }

}
