package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;

/**
 * Created by user on 2017/7/24.
 */

public interface SectionContract {

    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter<View>{

    }
}
