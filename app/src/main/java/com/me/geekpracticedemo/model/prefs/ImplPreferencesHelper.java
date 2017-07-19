package com.me.geekpracticedemo.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.me.geekpracticedemo.app.App;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/19.
 */

public class ImplPreferencesHelper implements PreferenceHelper{

    private static final String SHARED_PREFERENCE_NAME = "my_sp";
    private SharedPreferences mPreferences;


    @Inject
    public ImplPreferencesHelper() {
        mPreferences = App.getInstance().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }
}
