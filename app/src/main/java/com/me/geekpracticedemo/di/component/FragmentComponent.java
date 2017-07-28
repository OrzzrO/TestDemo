package com.me.geekpracticedemo.di.component;

import android.app.Activity;

import com.me.geekpracticedemo.di.module.FragmentModule;
import com.me.geekpracticedemo.di.scope.FragmentScope;
import com.me.geekpracticedemo.ui.main.fragment.SettingFragment;
import com.me.geekpracticedemo.ui.zhihu.fragment.CommentFragment;
import com.me.geekpracticedemo.ui.zhihu.fragment.DailyFragment;
import com.me.geekpracticedemo.ui.zhihu.fragment.HotFragment;
import com.me.geekpracticedemo.ui.zhihu.fragment.SectionFragment;
import com.me.geekpracticedemo.ui.zhihu.fragment.ThemeFragment;

import dagger.Component;

/**
 * Created by user on 2017/7/21.
 */

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();


    void inject(CommentFragment commentFragment);

    void inject(DailyFragment dailyFragment);

    void inject(ThemeFragment themeFragment);

    void inject(SectionFragment sectionFragment);

    void inject(HotFragment hotFragment);

    void inject(SettingFragment settingFragment);
}
