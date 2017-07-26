package com.me.geekpracticedemo.model;

import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.bean.WelcomeBean;
import com.me.geekpracticedemo.model.db.DBHelper;
import com.me.geekpracticedemo.model.http.HttpHelper;
import com.me.geekpracticedemo.model.http.response.MyHttpResponse;
import com.me.geekpracticedemo.model.prefs.PreferenceHelper;

import io.reactivex.Flowable;

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

    @Override
    public Flowable<WelcomeBean> fetchWelcomeInfo(String res) {
        return mHttpHelper.fetchWelcomeInfo(res);
    }

    @Override
    public Flowable<CommentBean> fetchShortCommentInfo(int id) {
        return mHttpHelper.fetchShortCommentInfo(id);
    }

    @Override
    public Flowable<CommentBean> fetchLongCommentInfo(int id) {
        return mHttpHelper.fetchLongCommentInfo(id);
    }

    @Override
    public Flowable<DailyBeforeListBean> fetchDailyBeforeListInfo(String date) {
        return mHttpHelper.fetchDailyBeforeListInfo(date);
    }

    @Override
    public Flowable<DailyListBean> fetchDailyListInfo() {
        return mHttpHelper.fetchDailyListInfo();
    }

    @Override
    public Flowable<MyHttpResponse<VersionBean>> fetchVersionInfo() {
        return mHttpHelper.fetchVersionInfo();
    }

    @Override
    public boolean getNoImageState() {
        return mPreferenceHelper.getNoImageState();
    }

    @Override
    public void setCurrentItem(int index) {
        mPreferenceHelper.setCurrentItem(index);
    }

    @Override
    public int getCurrentItem() {
        return mPreferenceHelper.getCurrentItem();
    }

    @Override
    public void setVersionPoint(boolean b) {
        mPreferenceHelper.setVersionPoint(b);
    }

    @Override
    public boolean getVersionPoint() {
        return mPreferenceHelper.getVersionPoint();
    }

    @Override
    public void insertNewsId(int id) {
        mDBHelper.insertNewsId(id);
    }

    @Override
    public boolean queryNewsId(int id) {
        return mDBHelper.queryNewsId(id);
    }
}
