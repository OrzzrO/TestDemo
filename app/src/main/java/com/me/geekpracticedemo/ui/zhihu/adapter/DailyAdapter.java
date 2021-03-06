package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.model.bean.DailyBeforeListBean;
import com.me.geekpracticedemo.model.bean.DailyListBean;
import com.me.geekpracticedemo.util.ImageLoader;
import com.me.geekpracticedemo.weight.ZhihuDiffCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/7/25.
 */

public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private List<DailyListBean.StoriesBean> mList;
    private List<DailyListBean.TopStoriesBean> mTopList;
    private Context mContext;
    private final LayoutInflater mInflater;
    private boolean isBefore = false;
    private TopPagerAdapter mAdapter;
    private OnItemClickListener onItemClickListener;
    private ViewPager topViewPager;
    private String currentTitle = "今日热闻";


    public enum ITEM_TYPE {
        ITEM_TOP,
        //滚动栏
        ITEM_DATE,
        //日期
        ITEM_CONTENT    //内容
    }

    public DailyAdapter(Context context, List<DailyListBean.StoriesBean> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (!isBefore) {
            if (position == 0) {
//                Log.w("hongTest", "getItemViewType: 第一个条目是viewpager" );
                return ITEM_TYPE.ITEM_TOP.ordinal();
            } else if (position == 1) {
//                Log.w("hongTest", "getItemViewType: 第二个条目是textview" );
                return ITEM_TYPE.ITEM_DATE.ordinal();
            } else {
//                Log.w("hongTest", "getItemViewType:  普通条目" );
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        } else {
            if (position == 0) {
                return ITEM_TYPE.ITEM_DATE.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            mAdapter = new TopPagerAdapter(mContext, mTopList);
            return new TopViewHolder(mInflater.inflate(R.layout.item_top, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_DATE.ordinal()) {
            return new DateViewHolder(mInflater.inflate(R.layout.item_date, parent, false));
        }
        return new ContentViewHolder(mInflater.inflate(R.layout.item_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder){
            final int contentPosition;
            if (isBefore){
                contentPosition = position - 1;
            }else{
                contentPosition = position - 2;
            }
            ((ContentViewHolder)holder).mTvDailyItemTitle.setText(mList.get(contentPosition).getTitle());
            if (mList.get(contentPosition).getReadState()){
                ((ContentViewHolder)holder).mTvDailyItemTitle.setTextColor(ContextCompat.getColor(mContext,R.color.news_read));
            }else{
                ((ContentViewHolder)holder).mTvDailyItemTitle.setTextColor(ContextCompat.getColor(mContext,R.color.news_unread));
            }
            ImageLoader.load(mContext,mList.get(contentPosition).getImages().get(0),((ContentViewHolder)holder).mIvDailyItemImage);
            ((ContentViewHolder) holder).mIvDailyItemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null){
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_daily_item_image);
                        onItemClickListener.onItemClick(contentPosition,iv);
                    }
                }
            });
        }else if (holder instanceof DateViewHolder){
            ((DateViewHolder)holder).mTvDailyDate.setText(currentTitle);
        }else{
            ((TopViewHolder)holder).mVpTop.setAdapter(mAdapter);
            topViewPager =((TopViewHolder)holder).mVpTop;
        }




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addDailyData(DailyListBean info) {
        currentTitle = "今日热闻";
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ZhihuDiffCallback(mList,info.getStories()),false);
        mList = info.getStories();
        mTopList = info.getTop_stories();
        isBefore = false;
        diffResult.dispatchUpdatesTo(this);

    }

    public void addDailyBeforeData(DailyBeforeListBean info) {
        currentTitle = info.getDate();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ZhihuDiffCallback(mList, info.getStories()), true);
        mList = info.getStories();
        isBefore = true;
        diffResult.dispatchUpdatesTo(this);

    }

    public boolean getIsBefore() {
        return isBefore;
    }


    public void setReadState(int position, boolean readState) {
        mList.get(position).setReadState(readState);
    }



    public void changeTopPager(int currentCount) {
        if (!isBefore && topViewPager != null){
            topViewPager.setCurrentItem(currentCount);
        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily_item_image)
        ImageView mIvDailyItemImage;
        @BindView(R.id.tv_daily_item_title)
        TextView mTvDailyItemTitle;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_daily_date)
        TextView mTvDailyDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vp_top)
        ViewPager mVpTop;
        @BindView(R.id.ll_point_container)
        LinearLayout mLlPointContainer;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position,View view);
    }

}
