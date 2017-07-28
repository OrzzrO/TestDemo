package com.me.geekpracticedemo.presenter.main;

import android.Manifest;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.main.MainContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.event.NightModeEvent;
import com.me.geekpracticedemo.model.http.response.MyHttpResponse;
import com.me.geekpracticedemo.util.RxBus;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

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
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(NightModeEvent.class)
        .compose(RxUtil.<NightModeEvent>rxSchedulerHelper())
        .map(new Function<NightModeEvent, Boolean>() {
            @Override
            public Boolean apply(NightModeEvent nightModeEvent) throws Exception {
                return  nightModeEvent.getNightMode();
            }
        })
        .subscribeWith(new CommonSubscriber<Boolean>(mView,"切换模式失败") {
            @Override
            public void onNext(Boolean aBoolean) {
                    mView.useNightMode(aBoolean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                registerEvent();
            }
        }));

    }

    @Override
    public void checkVersion(final String currentVersion) {
        addSubscribe(mDataManager.fetchVersionInfo()
                     .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                     .compose(RxUtil.<VersionBean>handleMyResult())
                     .filter(new Predicate<VersionBean>() {
                         @Override
                         public boolean test(VersionBean versionBean) throws Exception {
                             return Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""));
                         }
                     })
                     .map(new Function<VersionBean, String>() {
                         @Override
                         public String apply(VersionBean bean) throws Exception {
                             StringBuilder content = new StringBuilder("版本号: v");
                             content.append(bean.getCode());
                             content.append("\r\n");
                             content.append("版本大小: ");
                             content.append(bean.getSize());
                             content.append("\r\n");
                             content.append("更新内容:\r\n");
                             content.append(bean.getDes().replace("\\r\\n","\r\n"));

                             return content.toString();
                         }
                     })
                     .subscribeWith(new CommonSubscriber<String>(mView) {
                         @Override
                         public void onNext(String s) {
                             mView.showUpdateDialog(s);
                         }
                     })
        );


    }

    @Override
    public void checkPermission(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted){
                    mView.startDownloadService();
                }else{
                    mView.showErrorMsg("下载文件需要权限~");
                }

            }
        }));
    }

    @Override
    public void setNightModeState(boolean b) {
        mDataManager.setNightModeState(b);
    }

    @Override
    public void setCurrentItem(int index) {
        mDataManager.setCurrentItem(index);
    }

    @Override
    public int getCurrentItem() {
        return mDataManager.getCurrentItem();
    }

    @Override
    public void setVersionPoint(boolean b) {
        mDataManager.setVersionPoint(b);
    }

    @Override
    public boolean getVersionPoint() {
        return mDataManager.getVersionPoint();
    }
}
