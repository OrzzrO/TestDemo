package com.me.geekpracticedemo.model.db;

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
}
