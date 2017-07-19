package com.me.geekpracticedemo.di.module;

import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.db.DBHelper;
import com.me.geekpracticedemo.model.db.RealmHelper;
import com.me.geekpracticedemo.model.http.HttpHelper;
import com.me.geekpracticedemo.model.http.RetrofitHelper;
import com.me.geekpracticedemo.model.prefs.ImplPreferencesHelper;
import com.me.geekpracticedemo.model.prefs.PreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2017/7/19.
 */
@Module
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper){
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(RealmHelper realmHelper){
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(ImplPreferencesHelper implPreferencesHelper){
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper,DBHelper dbHelper,PreferenceHelper preferenceHelper){
        return new DataManager(httpHelper,dbHelper,preferenceHelper);
    }


}
