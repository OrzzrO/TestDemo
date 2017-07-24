package com.me.geekpracticedemo.ui.zhihu.fragment;

import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.DailyContract;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.presenter.zhihu.DailyPresenter;

/**
 * Created by user on 2017/7/24.
 */

public class DailyFragment extends RootFragment<DailyPresenter> implements DailyContract.View {
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return 0;
    }

    @Override
    public void showContent(DailyListBean info) {
    }

    @Override
    public void showMoreContent(String date, DailyBeforeListBean info) {
    }

    @Override
    public void doInterval(int currentCount) {
    }
}
