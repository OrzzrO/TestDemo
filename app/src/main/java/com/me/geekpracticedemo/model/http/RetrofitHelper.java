package com.me.geekpracticedemo.model.http;

import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.bean.WelcomeBean;
import com.me.geekpracticedemo.model.http.api.GankApis;
import com.me.geekpracticedemo.model.http.api.GoldApis;
import com.me.geekpracticedemo.model.http.api.MyApis;
import com.me.geekpracticedemo.model.http.api.VtexApis;
import com.me.geekpracticedemo.model.http.api.WeChatApis;
import com.me.geekpracticedemo.model.http.api.ZhihuApis;
import com.me.geekpracticedemo.model.http.response.MyHttpResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by user on 2017/7/19.
 */

public class RetrofitHelper implements HttpHelper {

    private ZhihuApis mZhihuApiService;
    private GankApis mGankApiService;
    private WeChatApis mWechatApiService;
    private MyApis mMyApiService;
    private GoldApis mGoldApiService;
    private VtexApis mVtexApiService;

    @Inject
    public RetrofitHelper(ZhihuApis zhihuApiService, GankApis gankApiService, WeChatApis wechatApiService,
                          MyApis myApiService, GoldApis goldApiService, VtexApis vtexApiService) {
        this.mZhihuApiService = zhihuApiService;
        this.mGankApiService = gankApiService;
        this.mWechatApiService = wechatApiService;
        this.mMyApiService = myApiService;
        this.mGoldApiService = goldApiService;
        this.mVtexApiService = vtexApiService;
    }

    @Override
    public Flowable<WelcomeBean> fetchWelcomeInfo(String res) {
        return mZhihuApiService.getWelcomeInfo(res);
    }

    @Override
    public Flowable<CommentBean> fetchShortCommentInfo(int id) {
        return mZhihuApiService.getShortCommentInfo(id);
    }

    @Override
    public Flowable<CommentBean> fetchLongCommentInfo(int id) {
        return mZhihuApiService.getLongCommentInfo(id);
    }

    @Override
    public Flowable<DailyBeforeListBean> fetchDailyBeforeListInfo(String date) {
        return mZhihuApiService.getDailyBeforeList(date);
    }

    @Override
    public Flowable<DailyListBean> fetchDailyListInfo() {
        return mZhihuApiService.getDailyList();
    }

    @Override
    public Flowable<MyHttpResponse<VersionBean>> fetchVersionInfo() {
        return mMyApiService.getVersionInfo();
    }
}
