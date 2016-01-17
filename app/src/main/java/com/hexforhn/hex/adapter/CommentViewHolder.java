package com.hexforhn.hex.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout mCommentView;

    public CommentViewHolder(LinearLayout view) {
        super(view);
        mCommentView = view;
    }
}
