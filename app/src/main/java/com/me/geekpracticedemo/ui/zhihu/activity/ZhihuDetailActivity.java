package com.me.geekpracticedemo.ui.zhihu.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.Constants;
import com.me.geekpracticedemo.base.RootActivity;
import com.me.geekpracticedemo.base.contract.zhihu.ZhihuDetailContract;
import com.me.geekpracticedemo.model.bean.DetailExtraBean;
import com.me.geekpracticedemo.model.bean.ZhihuDetailBean;
import com.me.geekpracticedemo.presenter.zhihu.ZhihuDetailPresenter;
import com.me.geekpracticedemo.util.HtmlUtil;
import com.me.geekpracticedemo.util.ImageLoader;
import com.me.geekpracticedemo.util.ShareUtil;
import com.me.geekpracticedemo.util.SystemUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.id;

/**
 * Created by user on 2017/7/25.
 */

public class ZhihuDetailActivity extends RootActivity<ZhihuDetailPresenter> implements ZhihuDetailContract.View {


    @BindView(R.id.detail_bar_image)
    ImageView mDetailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView mDetailBarCopyright;
    @BindView(R.id.view_toolbar)
    Toolbar mViewToolbar;
    @BindView(R.id.clp_toolbar)
    CollapsingToolbarLayout mClpToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.tv_detail_bottom_like)
    TextView mTvDetailBottomLike;
    @BindView(R.id.tv_detail_bottom_comment)
    TextView mTvDetailBottomComment;
    @BindView(R.id.tv_detail_bottom_share)
    TextView mTvDetailBottomShare;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout mLlDetailBottom;
    @BindView(R.id.fab_like)
    FloatingActionButton mFabLike;
    @BindView(R.id.view_main)
    WebView wvDetailContent;
    @BindView(R.id.nsv_scroller)
    NestedScrollView mNsvScroller;
    private int mId = 0;
    private int allNum = 0;
    private int shortNum = 0;
    private int longNum = 0;
    private boolean mIsNotTransition = false;
    private boolean isBottomView = true;
    private boolean isTransitionEnd = false;
    private boolean mIsImageShow = false;

    String shareUrl;
    String imgUrl;


    @Override
    public void showContent(ZhihuDetailBean zhihuDetailBean) {
        stateMain();
        Log.w("hongTest", "showContent: stateMain执行了~" );
        imgUrl = zhihuDetailBean.getImage();
        shareUrl = zhihuDetailBean.getShare_url();
        if (mIsNotTransition) {
            ImageLoader.load(mContext, zhihuDetailBean.getImage(), mDetailBarImage);
        } else {
            if (!mIsImageShow && isTransitionEnd) {
                ImageLoader.load(mContext, zhihuDetailBean.getImage(), mDetailBarImage);
            }
        }
        mClpToolbar.setTitle(zhihuDetailBean.getTitle());
        mDetailBarCopyright.setText(zhihuDetailBean.getImage_source());
        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),
                                                  zhihuDetailBean.getCss(),
                                                  zhihuDetailBean.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showExtralInfo(DetailExtraBean detailExtraBean) {
        mTvDetailBottomLike.setText(String.format("%d个赞", detailExtraBean.getPopularity()));
        mTvDetailBottomComment.setText(String.format("%d条评论", detailExtraBean.getComments()));
        allNum = detailExtraBean.getComments();
        shortNum = detailExtraBean.getShort_comments();
        longNum = detailExtraBean.getLong_comments();
    }

    @Override
    public void setLikeButtonState(boolean isLiked) {
        mFabLike.setSelected(isLiked);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvDetailContent.canGoBack()) {
            wvDetailContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        setToolBar(mViewToolbar, "");
        Intent intent = getIntent();
        mId = intent.getExtras().getInt(Constants.IT_ZHIHU_DETAIL_ID);
        mIsNotTransition = intent.getBooleanExtra("isNotTransition", false);
        mPresenter.queryLikeData(mId);
        mPresenter.getDetailData(mId);
        mPresenter.getExtraData(mId);
        stateLoading();
        WebSettings settings = wvDetailContent.getSettings();
        if (mPresenter.getNoImageState()) {
            settings.setBlockNetworkImage(true); //web view 渲染加速,图片在最后加载.
        }
        if (mPresenter.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);    //webview 设置缓存
            settings.setDomStorageEnabled(true);  // webview
            settings.setDatabaseEnabled(true);    //webView 支持本地存储
            if (SystemUtil.isNetworkConnected()) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置存储模式
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);   //webview支持js
        settings.setLoadWithOverviewMode(true); //webview的html页面缩放至屏幕大小
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);  // 设置支持缩放
        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        mNsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0 && isBottomView) { //下移隐藏
                    isBottomView = false;
                    mLlDetailBottom
                        .animate()
                        .translationY(mLlDetailBottom.getHeight());
                } else if (scrollY - oldScrollY < 0 && !isBottomView) {  //上移出现
                    isBottomView = true;
                    mLlDetailBottom
                        .animate()
                        .translationY(0);
                }
            }
        });
        (getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                /**
                 * 测试发现部分手机(如红米note2)上加载图片会变形,没有达到centerCrop效果
                 * 查阅资料发现Glide配合SharedElementTransition是有坑的,需要在Transition动画结束后再加载图片
                 * https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_847_shared_transition/DetailFragment.java
                 */
                isTransitionEnd = true;
                if (imgUrl != null) {
                    mIsImageShow = true;
                    ImageLoader.load(mContext, imgUrl, mDetailBarImage);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }


    @OnClick({R.id.fab_like,
              R.id.tv_detail_bottom_comment,
              R.id.tv_detail_bottom_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_like:

                if (mFabLike.isSelected()){
                    mFabLike.setSelected(false);
                    mPresenter.deleteLikeData();
                }else{
                    mFabLike.setSelected(true);
                    mPresenter.insertLikeData();
                }

                break;
            case R.id.tv_detail_bottom_comment:
                Intent intent = getIntent();
                intent.setClass(this,CommentActivity.class);
                intent.putExtra(Constants.IT_ZHIHU_COMMENT_ID, id);
                intent.putExtra(Constants.IT_ZHIHU_COMMENT_ALL_NUM, allNum);
                intent.putExtra(Constants.IT_ZHIHU_COMMENT_SHORT_NUM, shortNum);
                intent.putExtra(Constants.IT_ZHIHU_COMMENT_LONG_NUM, longNum);
                startActivity(intent);


                break;
            case R.id.tv_detail_bottom_share:
                ShareUtil.shareText(mContext,shareUrl,"分享一篇文章");
                break;
        }
    }
}
