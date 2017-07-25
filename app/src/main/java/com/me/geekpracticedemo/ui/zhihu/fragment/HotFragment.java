package com.me.geekpracticedemo.ui.zhihu.fragment;

import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.HotContract;
import com.me.geekpracticedemo.model.bean.HotListBean;
import com.me.geekpracticedemo.presenter.zhihu.HotPresenter;

/**
 * Created by user on 2017/7/24.
 */

public class HotFragment extends RootFragment<HotPresenter> implements HotContract.View {
    @Override
    protected void initInject() {
//        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return 0;
    }

    @Override
    public void showContent(HotListBean hotListBean) {
    }
}
