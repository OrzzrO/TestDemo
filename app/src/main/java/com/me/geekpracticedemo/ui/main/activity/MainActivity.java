package com.me.geekpracticedemo.ui.main.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.BaseActivity;
import com.me.geekpracticedemo.base.contract.main.MainContract;
import com.me.geekpracticedemo.presenter.main.MainPresenter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.view_search)
    MaterialSearchView mSearchView;
    @BindView(R.id.toolbar_container)
    FrameLayout mToolbarContainer;
    @BindView(R.id.fl_main_content)
    FrameLayout mFlMainContent;
    @BindView(R.id.navigation)
    NavigationView mNavigation;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;



    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mToolBar,getResources().getString(R.string.zhihu));
    }




    @Override
    public void showUpdateDialog(String versionContent) {
    }

    @Override
    public void startDownloadService() {
    }


}
