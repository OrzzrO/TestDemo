package com.me.geekpracticedemo.model.http.api;

import com.me.geekpracticedemo.model.bean.WelcomeBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by user on 2017/7/19.
 */

public interface ZhihuApis {

    String HOST = "http://news-at.zhihu.com/api/4/";


    /**
     * 启动界面图片
     * @param res
     * @return
     */
    @GET("start-image/{res}")
    Flowable<WelcomeBean> getWelcomeInfo(@Path("res") String res);

}
