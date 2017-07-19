package com.me.geekpracticedemo.di.module;

import android.app.Activity;

import com.me.geekpracticedemo.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017/7/19.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity(){
        return mActivity;
    }
}
