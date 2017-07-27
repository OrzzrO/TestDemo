package com.me.geekpracticedemo.model.http.api;

import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.model.bean.DetailExtraBean;
import com.me.geekpracticedemo.model.bean.HotListBean;
import com.me.geekpracticedemo.model.bean.SectionChildListBean;
import com.me.geekpracticedemo.model.bean.SectionListBean;
import com.me.geekpracticedemo.model.bean.ThemeChildListBean;
import com.me.geekpracticedemo.model.bean.ThemeListBean;
import com.me.geekpracticedemo.model.bean.WelcomeBean;
import com.me.geekpracticedemo.model.bean.ZhihuDetailBean;

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

    /**
     * 往期日报
     */
    @GET("news/before/{date}")
    Flowable<DailyBeforeListBean> getDailyBeforeList(@Path("date") String date);

    /**
     * 最新日报
     */
    @GET("news/latest")
    Flowable<DailyListBean> getDailyList();

    /**
     * 主题日报
     */
    @GET("themes")
    Flowable<ThemeListBean> getThemeList();

    /**
     * 主题日报详情
     */
    @GET("theme/{id}")
    Flowable<ThemeChildListBean> getThemeChildList(@Path("id") int id);

    /**
     * 专栏日报
     */
    @GET("sections")
    Flowable<SectionListBean> getSectionList();

    /**
     * 专栏日报详情
     */
    @GET("section/{id}")
    Flowable<SectionChildListBean> getSectionChildList(@Path("id") int id);

    /**
     * 热门日报
     */
    @GET("news/hot")
    Flowable<HotListBean> getHotList();

    /**
     * 日报详情
     */
    @GET("news/{id}")
    Flowable<ZhihuDetailBean> getDetailInfo(@Path("id") int id);

    /**
     * 日报的额外信息
     */
    @GET("story-extra/{id}")
    Flowable<DetailExtraBean> getDetailExtraInfo(@Path("id") int id);
}
