package com.me.geekpracticedemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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


    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;


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

        //初始化屏幕的宽高
        getScreenSize();

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


    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
}
