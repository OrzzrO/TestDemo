package com.me.geekpracticedemo.ui.main.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.BaseActivity;
import com.me.geekpracticedemo.base.contract.main.WelcomeContract;
import com.me.geekpracticedemo.model.bean.WelcomeBean;
import com.me.geekpracticedemo.presenter.main.WelcomePresenter;
import com.me.geekpracticedemo.util.ImageLoader;

import butterknife.BindView;

/**
 * Created by user on 2017/7/20.
 */

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {
    @BindView(R.id.tv_welcome_author)
    TextView mTvWelcomeAuthor;
    @BindView(R.id.iv_welcome_bg)
    ImageView mIvWelcomeBg;

    @Override
    public void showContent(WelcomeBean welcomeBean) {
        Log.w("hongTest", "showContent: 展示数据~" );
        ImageLoader.load(this,welcomeBean.getImg(),mIvWelcomeBg);
        mIvWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(1000).start();
        mTvWelcomeAuthor.setText(welcomeBean.getText());
    }

    @Override
    public void jumpToMain() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();

        //activity进入退出动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initEventAndData() {
        /**
         * 联网请求数据
         */
        Log.w("hongTest", "initEventAndData: 联网请求数据~" );
        mPresenter.getWelcomeData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onDestroy() {
        Glide.clear(mIvWelcomeBg);
        super.onDestroy();

    }

    @Override
    public void showErrorMsg(String msg) {
    }

    @Override
    public void useNightMode(boolean isNight) {
    }

    @Override
    public void stateError() {
    }

    @Override
    public void stateEmpty() {
    }

    @Override
    public void stateLoading() {
    }

    @Override
    public void stateMain() {
    }
}
