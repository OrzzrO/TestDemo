package com.me.geekpracticedemo.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.me.geekpracticedemo.app.App;

/**
 * Created by user on 2017/7/20.
 */

public class ImageLoader {

    public static void load(Context context, String url, ImageView imageView){
        if (!App.getAppComponent().preferenceHelper().getNoImageState()){
            //diskCacheStrategy 设置缓存策略,SOURCE 表示缓存原始数据
            Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
        }
    }


    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if(!activity.isDestroyed()) {
            Glide.with(activity).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
        }
    }
}
