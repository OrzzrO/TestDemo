package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.model.bean.SectionListBean;
import com.me.geekpracticedemo.ui.zhihu.activity.SectionActivity;
import com.me.geekpracticedemo.util.ImageLoader;
import com.me.geekpracticedemo.util.SystemUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.me.geekpracticedemo.app.Constants.IT_ZHIHU_SECTION_ID;
import static com.me.geekpracticedemo.app.Constants.IT_ZHIHU_SECTION_TITLE;

/**
 * Created by user on 2017/7/27.
 */

public class SectionAdapter
    extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    Context mContext;
    List<SectionListBean.DataBean> mList;
    private final LayoutInflater mInflater;


    public SectionAdapter(Context context, List<SectionListBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_section, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        ViewGroup.LayoutParams lp = holder.mSectionBg.getLayoutParams();
        lp.width = (App.SCREEN_WIDTH - SystemUtil.dp2px(mContext, 12)) / 2;
        lp.height = SystemUtil.dp2px(mContext, 120);

        ImageLoader.load(mContext, mList.get(position).getThumbnail(), holder.mSectionBg);
        holder.mSectionKind.setText(mList.get(position).getName());
        holder.mSectionDes.setText(mList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, SectionActivity.class);
                intent.putExtra(IT_ZHIHU_SECTION_ID, mList.get(holder.getAdapterPosition()).getId());
                intent.putExtra(IT_ZHIHU_SECTION_TITLE, mList.get(holder.getAdapterPosition()).getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.section_bg)
        ImageView mSectionBg;
        @BindView(R.id.section_kind)
        TextView mSectionKind;
        @BindView(R.id.section_des)
        TextView mSectionDes;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
