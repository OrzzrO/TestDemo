package com.me.geekpracticedemo.base;

import android.view.View;
import android.view.ViewGroup;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.weight.ProgressImageView;

/**
 * Created by user on 2017/7/21.
 */

public abstract class RootFragment<T extends BasePresenter> extends BaseFragment<T> {

    private static final int STATE_MAIN = 0x00;
    private static final int STATE_LOADING = 0x01;
    private static final int STATE_ERROR = 0x02;


    private int mErrorResource = R.layout.view_error;

    private int currentState = STATE_MAIN;
    private boolean isErrorViewAdded = false;
    private ViewGroup mViewMain;
    private ViewGroup mParent;
    private View mViewLoading;
    private ProgressImageView mIvLoading;
    private View mViewError;


    @Override
    protected void initEventAndData() {
            if (getView() == null){
                return;
            }
        mViewMain = getView().findViewById(R.id.view_main);
        if (mViewMain == null){
            throw new  IllegalStateException(
                "the subclass of RootActivity must contain a View named 'view_main'"
            );
        }
        if (!(mViewMain.getParent() instanceof ViewGroup)){
            throw new IllegalStateException(
                "view_main's parentView should be a ViewGroup"
            );
        }
        mParent = (ViewGroup) mViewMain.getParent();
        View.inflate(mContext,R.layout.view_progress,mParent);
        mViewLoading = mParent.findViewById(R.id.view_loading);
        mIvLoading = mViewLoading.findViewById(R.id.iv_progress);
        mViewLoading.setVisibility(View.GONE);
        mViewMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateEmpty() {
    }


    @Override
    public void showErrorMsg(String msg) {
    }

    @Override
    public void useNightMode(boolean isNight) {
    }

    @Override
    public void stateError() {
        if (currentState == STATE_ERROR){
            return;
        }
        if (!isErrorViewAdded){
            isErrorViewAdded = true;
            View.inflate(mContext,mErrorResource,mParent);
            mViewError = mParent.findViewById(R.id.view_error);
            if (mViewError == null){
                throw  new IllegalStateException(
                    "A View should be named 'view_error' in ErrorLayoutResource"
                );
            }

            hideCurrentView();
            currentState = STATE_ERROR;
            mViewError.setVisibility(View.VISIBLE);
        }

    }

    private void hideCurrentView() {
        switch (currentState) {
            case STATE_MAIN:
                mViewMain.setVisibility(View.GONE);
                 break;
            case STATE_LOADING:
                mIvLoading.stop();
                mViewLoading.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                if (mViewError != null){
                    mViewError.setVisibility(View.GONE);
                }
                break;
            default:
                 break;
        }

    }




    @Override
    public void stateLoading() {
        if (currentState == STATE_LOADING){
            return;
        }
        hideCurrentView();
        currentState = STATE_LOADING;
        mViewLoading.setVisibility(View.VISIBLE);
        mIvLoading.start();
    }

    @Override
    public void stateMain() {
        if (currentState == STATE_MAIN){
            return;
        }
        hideCurrentView();
        currentState = STATE_MAIN;
        mViewMain.setVisibility(View.VISIBLE);
    }

    public void setErrorResource(int errorLayoutResource) {
        this.mErrorResource = errorLayoutResource;
    }
}
