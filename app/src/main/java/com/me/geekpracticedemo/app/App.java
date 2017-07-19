package com.me.geekpracticedemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.me.geekpracticedemo.di.component.AppComponent;
import com.me.geekpracticedemo.di.component.DaggerAppComponent;
import com.me.geekpracticedemo.di.module.AppModule;
import com.me.geekpracticedemo.di.module.HttpModule;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;

/**
 * Created by user on 2017/7/19.
 */

public class App extends Application {


    private static App instance;
    private static AppComponent appComponent;
    private Set<Activity> allActivities;

    public static synchronized App getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Realm.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void addActivity(Activity act){
        if (allActivities == null){
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act){
        if (allActivities != null){
            allActivities.remove(act);
        }
    }


    public void exitApp(){
        if (allActivities != null){
            synchronized (allActivities){
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

    public static AppComponent getAppComponent(){
            if (appComponent == null){
                    appComponent = DaggerAppComponent.builder()
                        .appModule(new AppModule(instance))
                        .httpModule(new HttpModule())
                        .build();
            }
            return appComponent;
    }

}
