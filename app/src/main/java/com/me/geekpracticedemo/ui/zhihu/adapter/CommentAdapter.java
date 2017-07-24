package com.me.geekpracticedemo.ui.zhihu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.geekpracticedemo.R;
import com.me.geekpracticedemo.model.bean.CommentBean;
import com.me.geekpracticedemo.util.DateUtil;
import com.me.geekpracticedemo.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017/7/24.
 */

public class CommentAdapter
    extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private static final int STATE_NULL = 0;    //未知
    private static final int STATE_NONE = 1;    //无需展开
    private static final int STATE_EXPAND = 2;  //已展开
    private static final int STATE_SHRINK = 3;  //已收缩

    private static final int MAX_LINE = 2;  //起始最多显示2行


    private LayoutInflater inflater;
    private List<CommentBean.CommentsBean> mList;
    private Context mContext;

    public CommentAdapter(Context context, List<CommentBean.CommentsBean> list) {
        this.mList = list;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CommentBean.CommentsBean info = mList.get(position);
        ImageLoader.load(mContext,info.getAvatar(),holder.mCivCommentFace);
        holder.mTvCommentName.setText(info.getAuthor());
        holder.mTvCommentContent.setText(info.getContent());
        holder.mTvCommentTime.setText(DateUtil.formatTime2String(info.getTime()));
        holder.mTvCommentLike.setText(String.valueOf(info.getLikes()));
        if (info.getReply_to() != null && info.getReply_to().getId() != 0){
            holder.mTvCommentReply.setVisibility(View.VISIBLE);
            SpannableString ss = new SpannableString("@" + info.getReply_to().getAuthor()
                                                         + ": " +info.getReply_to().getContent());
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.comment_at)), 0
                , info.getReply_to().getAuthor().length() + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            holder.mTvCommentReply.setText(ss);
            if (info.getReply_to().getExpandState() == STATE_NULL){
                holder.mTvCommentReply.post(new Runnable() {
                    @Override
                    public void run() {
                        if (holder.mTvCommentReply.getLineCount() > MAX_LINE){
                            holder.mTvCommentReply.setMaxLines(MAX_LINE);
                            holder.mTvCommentExpand.setVisibility(View.VISIBLE);
                            holder.mTvCommentExpand.setText("展开");
                            mList.get(holder.getAdapterPosition()).getReply_to().setExpandState(STATE_SHRINK);
                            holder.mTvCommentExpand.setOnClickListener(new OnStateClickListener(holder.getAdapterPosition(),holder.mTvCommentReply));
                        }else{
                            holder.mTvCommentExpand.setVisibility(View.GONE);
                            mList.get(holder.getAdapterPosition()).getReply_to().setExpandState(STATE_NONE);
                        }
                    }
                });
            }else if (info.getReply_to().getExpandState() == STATE_NONE){  //无需展开
                holder.mTvCommentExpand.setVisibility(View.GONE);
            }else if (info.getReply_to().getExpandState() == STATE_EXPAND){ //已展开
                holder.mTvCommentReply.setMaxLines(Integer.MAX_VALUE);
                holder.mTvCommentExpand.setText("收起");
                holder.mTvCommentExpand.setVisibility(View.VISIBLE);
                holder.mTvCommentExpand.setOnClickListener(new OnStateClickListener(holder.getAdapterPosition(),holder.mTvCommentReply));
            }else{  //已收缩
                holder.mTvCommentReply.setMaxLines(MAX_LINE);
                holder.mTvCommentExpand.setText("展开");
                holder.mTvCommentExpand.setVisibility(View.VISIBLE);
                holder.mTvCommentExpand.setOnClickListener(new OnStateClickListener(holder.getAdapterPosition(),holder.mTvCommentReply));
            }
        }else{
            holder.mTvCommentReply.setVisibility(View.GONE);
            holder.mTvCommentExpand.setVisibility(View.GONE);
        }

    }

    private class OnStateClickListener implements View.OnClickListener {

        TextView replyView;
        int position;

        public OnStateClickListener(int position,TextView replyView) {
            this.position = position;
            this.replyView = replyView;
        }

        @Override
        public void onClick(View view) {
            TextView tv = (TextView) view;
            if (mList.get(position).getReply_to().getExpandState() == STATE_SHRINK) {
                tv.setText("收起");
                replyView.setMaxLines(Integer.MAX_VALUE);
                replyView.setEllipsize(null);
                mList.get(position).getReply_to().setExpandState(STATE_EXPAND);
            } else {
                tv.setText("展开");
                replyView.setMaxLines(MAX_LINE);
                replyView.setEllipsize(TextUtils.TruncateAt.END);
                mList.get(position).getReply_to().setExpandState(STATE_SHRINK);
            }
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_comment_face)
        com.me.geekpracticedemo.weight.CircleImageView mCivCommentFace;
        @BindView(R.id.tv_comment_name)
        TextView mTvCommentName;
        @BindView(R.id.tv_comment_content)
        TextView mTvCommentContent;
        @BindView(R.id.tv_comment_reply)
        TextView mTvCommentReply;
        @BindView(R.id.tv_comment_time)
        TextView mTvCommentTime;
        @BindView(R.id.tv_comment_expand)
        TextView mTvCommentExpand;
        @BindView(R.id.tv_comment_like)
        TextView mTvCommentLike;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
