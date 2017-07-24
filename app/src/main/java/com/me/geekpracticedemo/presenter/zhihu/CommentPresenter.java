package com.me.geekpracticedemo.presenter.zhihu;

import com.me.geekpracticedemo.base.RxPresenter;
import com.me.geekpracticedemo.base.contract.zhihu.CommentContract;
import com.me.geekpracticedemo.model.DataManager;
import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.util.RxUtil;
import com.me.geekpracticedemo.weight.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by user on 2017/7/24.
 */

public class CommentPresenter extends RxPresenter<CommentContract.View> implements CommentContract.Presenter{

    private DataManager mDataManager;

    public static final int SHORT_COMMENT = 0;

    public static final int LONG_COMMENT = 1;


    @Inject
    public CommentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void getCommentData(int id, int commentKind) {
        if (commentKind == SHORT_COMMENT){
            addSubscribe(mDataManager.fetchShortCommentInfo(id)
            .compose(RxUtil.<CommentBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<CommentBean>(mView) {
                @Override
                public void onNext(CommentBean commentBean) {
                    mView.stateMain();
                    mView.showContent(commentBean);
                }
            })
            );
        }else {
            addSubscribe(mDataManager.fetchLongCommentInfo(id)
            .compose(RxUtil.<CommentBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<CommentBean>(mView) {
                @Override
                public void onNext(CommentBean commentBean) {
                    mView.stateMain();
                    mView.showContent(commentBean);
                }
            }));
        }

    }
}
