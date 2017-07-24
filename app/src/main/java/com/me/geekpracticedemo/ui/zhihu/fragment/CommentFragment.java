package com.me.geekpracticedemo.ui.zhihu.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.base.RootFragment;
import com.me.geekpracticedemo.base.contract.zhihu.CommentContract;
import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.presenter.zhihu.CommentPresenter;
import com.me.geekpracticedemo.ui.zhihu.adapter.CommentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by user on 2017/7/21.
 */

public class CommentFragment extends RootFragment<CommentPresenter> implements CommentContract.View {
    @BindView(R.id.view_main)
    RecyclerView mRvCommentList;
    Unbinder unbinder;
    private ArrayList mList;
    private CommentAdapter mAdapter;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        Bundle bundle = getArguments();
        stateLoading();
        mPresenter.getCommentData(bundle.getInt("id"), bundle.getInt("kind"));
        mRvCommentList.setVisibility(View.INVISIBLE);
        mList = new ArrayList();
        mAdapter = new CommentAdapter(mContext, mList);
        mRvCommentList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCommentList.setAdapter(mAdapter);

    }

    @Override
    public void showContent(CommentBean commentBean) {
        mRvCommentList.setVisibility(View.VISIBLE);
        mList.clear();
        mList.addAll(commentBean.getComments());
        mAdapter.notifyDataSetChanged();
    }



}
