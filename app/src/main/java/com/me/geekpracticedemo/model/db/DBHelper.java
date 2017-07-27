package com.me.geekpracticedemo.model.db;

import com.me.geekpracticedemo.model.bean.RealmLikeBean;

/**
 * Created by user on 2017/7/19.
 */

public interface DBHelper {

    void insertNewsId(int id);

    /**
     * 查询 阅读记录
     * @param id
     * @return
     */
    boolean queryNewsId(int id);


    /**
     * 增加 收藏记录
     * @param bean
     */
    void insertLikeBean(RealmLikeBean bean);

    boolean queryLikeId(String s);

    void deleteLikeBean(String id);
}
