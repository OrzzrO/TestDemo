package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.model.bean.ThemeChildListBean;
import com.me.geekpracticedemo.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/7/27.
 */

public class ThemeChildAdapter
    extends RecyclerView.Adapter<ThemeChildAdapter.ViewHolder> {


    private Context mContext;
    private List<ThemeChildListBean.StoriesBean> mList;
    private final LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

    public ThemeChildAdapter(Context context, List<ThemeChildListBean.StoriesBean> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ThemeChildAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_daily, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ThemeChildAdapter.ViewHolder holder, int position) {
        if (mList.get(position).getImages() != null && mList.get(position).getImages().size() != 0){
            ImageLoader.load(mContext,mList.get(position).getImages().get(0),((ViewHolder)holder).mIvDailyItemImage);
        }
        ((ViewHolder)holder).mTvDailyItemTitle.setText(mList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView iv = (ImageView) view.findViewById(R.id.iv_daily_item_image);
                onItemClickListener.onItemClick(holder.getAdapterPosition(),iv);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily_item_image)
        ImageView mIvDailyItemImage;
        @BindView(R.id.tv_daily_item_title)
        TextView mTvDailyItemTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setReadState(int position,boolean readState) {
        mList.get(position).setReadState(readState);
    }
}
