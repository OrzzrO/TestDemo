package com.me.geekpracticedemo.ui.zhihu.fragment;

import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.SectionContract;
import com.me.geekpracticedemo.presenter.zhihu.SectionPresenter;

/**
 * Created by user on 2017/7/24.
 */

public class SectionFragment extends RootFragment<SectionPresenter> implements SectionContract.View{
    @Override
    protected void initInject() {
//        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return 0;
    }
}
