package com.me.geekpracticedemo.util;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by user on 2017/7/20.
 */

public class RxUtil {
    /**
     * 统一线程处理, 利用compose操作符,将切换线程的代码封装成一句话
     * @param <T>
     * @return
     */
    public static <T>FlowableTransformer<T,T> rxSchedulerHelper(){
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
