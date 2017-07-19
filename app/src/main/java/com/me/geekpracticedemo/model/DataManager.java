package com.me.geekpracticedemo.model;

import com.me.geekpracticedemo.model.db.DBHelper;
import com.me.geekpracticedemo.model.http.HttpHelper;
import com.me.geekpracticedemo.model.prefs.PreferenceHelper;

/**
 * Created by user on 2017/7/19.
 */

public class DataManager implements HttpHelper,DBHelper,PreferenceHelper {

    HttpHelper mHttpHelper;
    DBHelper mDBHelper;
    PreferenceHelper mPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DBHelper DBHelper, PreferenceHelper preferenceHelper) {
        mHttpHelper = httpHelper;
        mDBHelper = DBHelper;
        mPreferenceHelper = preferenceHelper;
    }
}
