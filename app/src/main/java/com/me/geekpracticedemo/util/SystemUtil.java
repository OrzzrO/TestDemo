package com.me.geekpracticedemo.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.me.geekpracticedemo.app.App;

/**
 * Created by user on 2017/7/19.
 */

public class SystemUtil {

    /**
     * 检查是否有可用网络
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetwork() != null;
    }
}
