package com.me.geekpracticedemo.model.http;

import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.bean.WelcomeBean;
import com.me.geekpracticedemo.model.http.response.MyHttpResponse;

import io.reactivex.Flowable;

/**
 * Created by user on 2017/7/19.
 */

public interface HttpHelper {

    Flowable<WelcomeBean> fetchWelcomeInfo(String res);

    Flowable<CommentBean> fetchShortCommentInfo(int id);

    Flowable<CommentBean> fetchLongCommentInfo(int id);

    Flowable<DailyBeforeListBean> fetchDailyBeforeListInfo(String date);

    Flowable<DailyListBean> fetchDailyListInfo();

    Flowable<MyHttpResponse<VersionBean>> fetchVersionInfo();
}
