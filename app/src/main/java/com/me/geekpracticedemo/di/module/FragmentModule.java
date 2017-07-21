package com.me.geekpracticedemo.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.me.geekpracticedemo.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017/7/21.
 */

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }


    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return mFragment.getActivity();
    }
}
