package com.me.geekpracticedemo.base.contract.main;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by user on 2017/7/21.
 * 进入主页面,业务逻辑为: 检测权限,检测新版本,如果有新版本,展示新版本的对话框,
 * 启动下载服务,
 */

public interface MainContract {

    interface View extends BaseView{

        void showUpdateDialog(String versionContent);

        void startDownloadService();
    }

    interface Presenter extends BasePresenter<View>{

        void checkVersion(String currentVersion);

        void checkPermission(RxPermissions rxPermissions);

        void setNightModeState(boolean b);

        void setCurrentItem(int index);

        int getCurrentItem();

        void setVersionPoint(boolean b);

        boolean getVersionPoint();
    }
}
