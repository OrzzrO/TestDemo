package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.me.geekpracticedemo.model.bean.DailyListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/7/25.
 */

public class TopPagerAdapter extends PagerAdapter {

    private List<DailyListBean.TopStoriesBean> mList = new ArrayList<>();
    private Context mContext;

    public TopPagerAdapter(Context context, List<DailyListBean.TopStoriesBean> list ) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
