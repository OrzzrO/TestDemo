package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.app.App;
import com.me.geekpracticedemo.model.bean.ThemeListBean;
import com.me.geekpracticedemo.util.ImageLoader;
import com.me.geekpracticedemo.util.SystemUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/7/27.
 */

public class ThemeAdapter
    extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {


    private List<ThemeListBean.OthersBean> mList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private final LayoutInflater mInflater;

    public ThemeAdapter(Context context, List<ThemeListBean.OthersBean> list) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.item_theme, parent, false);
        ViewHolder holder = new ViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Glide在加载GridView等时,由于ImageView和Bitmap实际大小不符合,第一次时加载可能会变形(我这里出现了放大),必须在加载前再次固定ImageView大小

        ViewGroup.LayoutParams lp = holder.mThemeBg.getLayoutParams();
        lp.width = (App.SCREEN_WIDTH - SystemUtil.dp2px(mContext, 12)) / 2;
        lp.height = SystemUtil.dp2px(mContext, 120);
        ImageLoader.load(mContext,mList.get(position).getThumbnail(),holder.mThemeBg);
        holder.mThemeKind.setText(mList.get(position).getName());
        final int id = mList.get(position).getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(id);
                    }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.theme_bg)
        ImageView mThemeBg;
        @BindView(R.id.theme_kind)
        TextView mThemeKind;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

}
