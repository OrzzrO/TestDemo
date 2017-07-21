package com.me.geekpracticedemo.model.http;

import com.me.geekpracticedemo.model.bean.WelcomeBean;

import io.reactivex.Flowable;

/**
 * Created by user on 2017/7/19.
 */

public interface HttpHelper {

    Flowable<WelcomeBean> fetchWelcomeInfo(String res);

}
