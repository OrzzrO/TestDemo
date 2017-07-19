package com.me.geekpracticedemo.di.component;

import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.di.module.AppModule;
import com.me.geekpracticedemo.di.module.HttpModule;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.db.RealmHelper;
import com.me.geekpracticedemo.model.http.RetrofitHelper;
import com.me.geekpracticedemo.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 2017/7/19.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext(); //提供App的Context

    DataManager getDataManager();  //数据中心

    RetrofitHelper retrofithelper(); //提供http的帮助类

    RealmHelper realmHelper(); //提供数据库的帮助类

    ImplPreferencesHelper preferenceHelper(); //提供sp的帮助类



}
