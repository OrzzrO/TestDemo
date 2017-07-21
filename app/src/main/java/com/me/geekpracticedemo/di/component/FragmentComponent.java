package com.me.geekpracticedemo.di.component;

import android.app.Activity;

import com.me.geekpracticedemo.di.module.FragmentModule;
import com.me.geekpracticedemo.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by user on 2017/7/21.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


}
