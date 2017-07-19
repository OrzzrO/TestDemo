package com.me.geekpracticedemo.model.db;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
}
