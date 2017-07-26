package com.me.geekpracticedemo.model.http.api;

import com.me.geekpracticedemo.model.bean.VersionBean;
import com.me.geekpracticedemo.model.http.response.MyHttpResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by user on 2017/7/19.
 */

public interface MyApis {
    String HOST = "http://codeest.me/api/geeknews/";
    String APK_DOWNLOAD_URL = "http://codeest.me/apk/geeknews.apk";

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("version")
    Flowable<MyHttpResponse<VersionBean>> getVersionInfo();
}
