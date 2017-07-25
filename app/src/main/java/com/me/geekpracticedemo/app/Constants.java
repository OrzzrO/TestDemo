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
    public static final String IT_ZHIHU_DETAIL_ID = "zhihu_detail_id";
}
