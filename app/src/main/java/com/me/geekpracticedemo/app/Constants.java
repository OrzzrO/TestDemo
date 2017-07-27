package com.me.geekpracticedemo.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by user on 2017/7/19.
 */

public class Constants {


    //PATH ---START---
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment
        .getExternalStorageDirectory().getAbsolutePath() + File.separator + "demo" + File.separator + "GeekNews";
    //PATH ---END---

    public static final String SP_NO_IMAGE = "no_image";
    public static final String SP_CURRENT_ITEM = "current_item";
    public static final String SP_VERSION_POINT = "version_point";
    public static final String SP_AUTO_CACHE = "auto_cache";







    public static final String IT_ZHIHU_THEME_ID = "zhihu_theme_id";
    public static final String IT_ZHIHU_DETAIL_ID = "zhihu_detail_id";
    public static final String IT_ZHIHU_COMMENT_ID = "zhihu_comment_id" ;
    public static final String IT_ZHIHU_COMMENT_ALL_NUM ="zhihu_comment_all_num";
    public static final String IT_ZHIHU_COMMENT_SHORT_NUM = "zhihu_comment_short_num";
    public static final String IT_ZHIHU_COMMENT_LONG_NUM = "zhihu_comment_long_num";
    public static final String IT_ZHIHU_DETAIL_TRANSITION = "zhihu_detail_transition";

    public static final String IT_ZHIHU_SECTION_ID = "zhihu_section_id";
    public static final String IT_ZHIHU_SECTION_TITLE = "zhihu_section_title";


    public static final int TYPE_ZHIHU = 101;
    public static final int TYPE_GANK = 102;
    public static final int TYPE_WECHAT = 106;
    public static final int TYPE_GOLD = 108;
    public static final int TYPE_VTEX = 109;
    public static final int TYPE_LIKE = 111;
    public static final int TYPE_SETTING = 110;
    public static final int TYPE_ABOUT = 112;
}
