package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.model.bean.HotListBean;
import com.me.geekpracticedemo.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/7/27.
 */

public class HotAdapter
    extends RecyclerView.Adapter<HotAdapter.ViewHolder> {



    private Context mContext;
    private List<HotListBean.RecentBean> mList;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

    public HotAdapter(Context context, List<HotListBean.RecentBean> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_daily, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTvTitle.setText(mList.get(position).getTitle());
        if (mList.get(position).getReadState()){
            holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext,R.color.news_read));
        }else{
            holder.mTvTitle.setTextColor(ContextCompat.getColor(mContext,R.color.news_unread));
        }
        ImageLoader.load(mContext,mList.get(position).getThumbnail(),holder.mIvImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (onItemClickListener != null){
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_daily_item_image);
                        onItemClickListener.onItemClick(holder.getAdapterPosition(),iv);
                    }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily_item_image)
        ImageView mIvImage;
        @BindView(R.id.tv_daily_item_title)
        TextView mTvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position,View view);
    }

    public void setReadState(int position,boolean readState) {
        mList.get(position).setReadState(readState);
    }
}
