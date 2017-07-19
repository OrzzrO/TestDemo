package com.me.geekpracticedemo.di.component;

import android.app.Activity;

import com.me.geekpracticedemo.di.module.ActivityModule;
import com.me.geekpracticedemo.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by user on 2017/7/19.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

}
