package com.me.geekpracticedemo.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.me.geekpracticedemo.BuildConfig;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.di.qualifier.GankUrl;
import com.me.geekpracticedemo.di.qualifier.GoldUrl;
import com.me.geekpracticedemo.di.qualifier.MyUrl;
import com.me.geekpracticedemo.di.qualifier.VtexUrl;
import com.me.geekpracticedemo.di.qualifier.WechatUrl;
import com.me.geekpracticedemo.di.qualifier.ZhihuUrl;
import com.me.geekpracticedemo.model.http.api.GankApis;
import com.me.geekpracticedemo.model.http.api.GoldApis;
import com.me.geekpracticedemo.model.http.api.MyApis;
import com.me.geekpracticedemo.model.http.api.VtexApis;
import com.me.geekpracticedemo.model.http.api.WeChatApis;
import com.me.geekpracticedemo.model.http.api.ZhihuApis;
import com.me.geekpracticedemo.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017/7/19.
 */

/**
 * 在编写该类的时候想一想dagger2之间的依赖关系,
 * 就明白为什么在这么多地方都要加上provides了.
 */

@Module
public class HttpModule {



    @Provides
    @Singleton
    @ZhihuUrl
    Retrofit provideZhiHuRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,ZhihuApis.HOST);
    }

    @Provides
    @Singleton
    @WechatUrl
    Retrofit provideWeChatRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,WeChatApis.HOST);
    }

    @Provides
    @Singleton
    @VtexUrl
    Retrofit provideVtexRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,VtexApis.HOST);
    }

    @Provides
    @Singleton
    @GoldUrl
    Retrofit provideGoldRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,GoldApis.HOST);
    }

    @Provides
    @Singleton
    @GankUrl
    Retrofit provideGankRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,GankApis.HOST);
    }

    @Provides
    @Singleton
    @MyUrl
    Retrofit provideMyRetrofit(Retrofit.Builder builder,OkHttpClient client){
        return createRetrofit(builder,client,MyApis.HOST);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        final Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()){
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                }
                Response response = chain.proceed(request);
                if(SystemUtil.isNetworkConnected()){
                    int maxAge = 0;
                    //有网络,不缓存,最大保存时长为0;
                    response.newBuilder()
                        .header("Cache-Control","public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
                }else{
                    //无网络时 ,设置超时时长为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }

                return response;
            }
        };
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpBuilder(){
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    @Provides
    @Singleton
    ZhihuApis provideZhihuService(@ZhihuUrl Retrofit retrofit){
        return retrofit.create(ZhihuApis.class);
    }

    @Provides
    @Singleton
    WeChatApis provideWeChatService(@WechatUrl Retrofit retrofit){
        return retrofit.create(WeChatApis.class);
    }

    @Provides
    @Singleton
    VtexApis provideVtexService(@VtexUrl Retrofit retrofit){
        return retrofit.create(VtexApis.class);
    }

    @Provides
    @Singleton
    GoldApis provideGoldService(@GoldUrl Retrofit retrofit){
        return retrofit.create(GoldApis.class);
    }

    @Provides
    @Singleton
    GankApis provideGankService(@GankUrl Retrofit retrofit){
        return retrofit.create(GankApis.class);
    }

    @Provides
    @Singleton
    MyApis provideMyService(@MyUrl Retrofit retrofit){
        return retrofit.create(MyApis.class);
    }


    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client,String url){
        return builder
            .baseUrl(url)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
