package com.me.geekpracticedemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.me.geekpracticedemo.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by user on 2017/7/19.
 * 无mvp的基类
 */

public abstract class SimpleActivity extends SupportActivity {

    private Unbinder mUnbinder;
    private Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    /**
     * 基类抽出来设置toolbar的方法让每个要用到的类直接set就可以调用了.
     * @param toolBar
     * @param str
     */
    public void setToolBar(Toolbar toolBar,String str){
        toolBar.setTitle(str);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnbinder.unbind(); //界面销毁butterknife解绑
    }

    protected void onViewCreated() {

    }

    protected abstract void initEventAndData();
    protected abstract int getLayoutId() ;
}
