package com.me.geekpracticedemo.base.contract.zhihu;

import com.me.geekpracticedemo.base.BasePresenter;
import com.me.geekpracticedemo.base.BaseView;
import com.me.geekpracticedemo.model.bean.CommentBean;

/**
 * Created by user on 2017/7/24.
 */

public interface CommentContract {

    interface View extends BaseView{

        void showContent(CommentBean commentBean);
    }

    interface Presenter extends BasePresenter<View>{

        void getCommentData(int id ,int commentKind);
    }
}
