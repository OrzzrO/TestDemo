package com.me.geekpracticedemo.model.db;

import com.me.geekpracticedemo.model.bean.ReadStateBean;
import com.me.geekpracticedemo.model.bean.RealmLikeBean;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by user on 2017/7/19.
 */

public class RealmHelper implements DBHelper {

    private static final String DB_NAME = "my_realm.realm";
    private Realm mRealm;


    @Inject
    public RealmHelper(){
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
        .deleteRealmIfMigrationNeeded()
        .name(DB_NAME)
        .build());
    }
    /**
     * 增加 阅读记录
     * @param id
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    @Override
    public void insertNewsId(int id) {
        ReadStateBean bean = new ReadStateBean();
        bean.setId(id);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }
    /**
     * 查询 阅读记录
     * @param id
     * @return
     */
    @Override
    public boolean queryNewsId(int id) {
        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
        for (ReadStateBean item : results) {
            if (item.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public void insertLikeBean(RealmLikeBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    /**
     * 查询 收藏记录
     * @param id
     * @return
     */
    @Override
    public boolean queryLikeId(String id) {
        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll();
        for(RealmLikeBean item : results) {
            if(item.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 删除 收藏记录
     * @param id
     */
    @Override
    public void deleteLikeBean(String id) {
        RealmLikeBean data = mRealm.where(RealmLikeBean.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        if (data != null) {
            data.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }
}
