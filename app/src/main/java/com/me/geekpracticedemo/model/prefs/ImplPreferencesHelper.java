package com.me.geekpracticedemo.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.app.Constants;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/19.
 */

public class ImplPreferencesHelper implements PreferenceHelper{

    private static final String SHARED_PREFERENCE_NAME = "my_sp";

    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_ZHIHU;
    private static final boolean DEFAULT_VERSION_POINT = false;
    private SharedPreferences mPreferences;


    @Inject
    public ImplPreferencesHelper() {
        mPreferences = App.getInstance().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getNoImageState() {
      return mPreferences.getBoolean(Constants.SP_NO_IMAGE,DEFAULT_NO_IMAGE);

    }

    @Override
    public void setCurrentItem(int item) {
        mPreferences.edit().putInt(Constants.SP_CURRENT_ITEM,item).apply();
    }

    @Override
    public int getCurrentItem() {
        return mPreferences.getInt(Constants.SP_CURRENT_ITEM,DEFAULT_CURRENT_ITEM);
    }

    @Override
    public void setVersionPoint(boolean isFirst) {
        mPreferences.edit().putBoolean(Constants.SP_VERSION_POINT, isFirst).apply();
    }

    @Override
    public boolean getVersionPoint() {
        return mPreferences.getBoolean(Constants.SP_VERSION_POINT, DEFAULT_VERSION_POINT);
    }
}
