package com.me.geekpracticedemo.model.http.api;

import com.me.geekpracticedemo.model.bean.CommentBean;
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


    /**
     * 日报的短评论
     * @param id
     * @return
     */
    @GET("story/{id}/short-comments")
    Flowable<CommentBean> getShortCommentInfo(@Path("id")int id);

    /**
     * 日报的短评论
     * @param id
     * @return
     */
    @GET("story/{id}/long-comments")
    Flowable<CommentBean> getLongCommentInfo(@Path("id")int id);
}
