package com.me.geekpracticedemo.ui.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.BaseActivity;
import com.me.geekpracticedemo.base.contract.main.MainContract;
import com.me.geekpracticedemo.presenter.main.MainPresenter;
import com.me.geekpracticedemo.ui.main.fragment.SettingFragment;
import com.me.geekpracticedemo.ui.zhihu.fragment.ZhiHuMainFragment;
import com.me.geekpracticedemo.util.SystemUtil;
import com.me.geekpracticedemo.util.UpdateService;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

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
    private ZhiHuMainFragment mZhiHuMainFragment;
    private ActionBarDrawerToggle mDrawerToggle;

    MenuItem mSearchMenuItem;
     MenuItem mLastMenuItem;

    private int hideFragment = Constants.TYPE_ZHIHU;
    private int showFragment = Constants.TYPE_ZHIHU;
    private SettingFragment mSettingFragment;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            mPresenter.setNightModeState(false);
        }else{
            showFragment = mPresenter.getCurrentItem();
            hideFragment = Constants.TYPE_ZHIHU;
            showHideFragment(getTargetFragment(showFragment),getTargetFragment(hideFragment));
            mNavigation.getMenu().findItem(R.id.drawer_zhihu).setCheckable(false);
            mToolBar.setTitle(mNavigation.getMenu().findItem(getCurrentItem(showFragment)).getTitle().toString());
            hideFragment = showFragment;
        }

    }

    @Override
    protected void initEventAndData() {
        setToolBar(mToolBar,getResources().getString(R.string.zhihu));
        mZhiHuMainFragment = new ZhiHuMainFragment();
        mSettingFragment = new SettingFragment();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolBar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawer.addDrawerListener(mDrawerToggle);
        mLastMenuItem = mNavigation.getMenu().findItem(R.id.drawer_zhihu);
        loadMultipleRootFragment(R.id.fl_main_content,0,mZhiHuMainFragment,mSettingFragment);
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_zhihu:
                        showFragment = Constants.TYPE_ZHIHU;
                            mSearchMenuItem.setVisible(false);
                         break;
                    case R.id.drawer_setting:
                        showFragment = Constants.TYPE_SETTING;
                        mSearchMenuItem.setVisible(false);
                        break;

                    default:
                         break;
                }
                if (mLastMenuItem != null){
                    mLastMenuItem.setCheckable(false);
                }
                mLastMenuItem = item;
                mPresenter.setCurrentItem(showFragment);
                item.setCheckable(true);
                mToolBar.setTitle(item.getTitle());
                mDrawer.closeDrawers();
                showHideFragment(getTargetFragment(showFragment),getTargetFragment(hideFragment));
                hideFragment = showFragment;

                return true;
            }
        });
//        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(showFragment == Constants.TYPE_GANK) {
//                    mGankFragment.doSearch(query);
//                } else if(showFragment == Constants.TYPE_WECHAT) {
//                    RxBus.getDefault().post(new SearchEvent(query, Constants.TYPE_WECHAT));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        if (!mPresenter.getVersionPoint() && SystemUtil.isWifiConnected()) {
            mPresenter.setVersionPoint(true);
            try {
                PackageManager pm = getPackageManager();
                PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
                String versionName = pi.versionName;
                mPresenter.checkVersion(versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private int getCurrentItem(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return R.id.drawer_zhihu;
            case Constants.TYPE_GANK:
                return R.id.drawer_gank;
            case Constants.TYPE_WECHAT:
                return R.id.drawer_wechat;
            case Constants.TYPE_GOLD:
                return R.id.drawer_gold;
            case Constants.TYPE_VTEX:
                return R.id.drawer_vtex;
            case Constants.TYPE_LIKE:
                return R.id.drawer_like;
            case Constants.TYPE_SETTING:
                return R.id.drawer_setting;
            case Constants.TYPE_ABOUT:
                return R.id.drawer_about;
        }
        return R.id.drawer_zhihu;

    }
    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_ZHIHU:
                return mZhiHuMainFragment;
//            case Constants.TYPE_GANK:
//                return mGankFragment;
//            case Constants.TYPE_WECHAT:
//                return mWechatFragment;
//            case Constants.TYPE_GOLD:
//                return mGoldFragment;
//            case Constants.TYPE_VTEX:
//                return mVtexFragment;
//            case Constants.TYPE_LIKE:
//                return mLikeFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
//            case Constants.TYPE_ABOUT:
//                return mAboutFragment;
        }
        return mZhiHuMainFragment;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        mSearchView.setMenuItem(item);
        mSearchMenuItem = item;
        return true;

    }

    @Override
    public void onBackPressedSupport() {
            if (mSearchView.isSearchOpen()){
                mSearchView.closeSearch();
            }else{
                showExitDialog();
            }
    }

    private void showExitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出GeekNews吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().exitApp();
            }
        });
        builder.show();

    }

    @Override
    public void showUpdateDialog(String versionContent) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("检测到新版本!");
        builder.setMessage(versionContent);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPermissions();
            }
        });
        builder.show();


    }

    public  void checkPermissions() {
        mPresenter.checkPermission(new RxPermissions(this));
    }

    @Override
    public void startDownloadService() {
        startService(new Intent(mContext,UpdateService.class));
    }



}
