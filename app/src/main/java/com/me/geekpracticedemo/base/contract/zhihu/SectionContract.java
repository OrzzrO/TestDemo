package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.SectionListBean;

/**
 * Created by user on 2017/7/24.
 */

public interface SectionContract {

    interface View extends BaseView{

        void showContent(SectionListBean sectionListBean);

    }

    interface Presenter extends BasePresenter<View>{

            void getSectionData();

    }
}
