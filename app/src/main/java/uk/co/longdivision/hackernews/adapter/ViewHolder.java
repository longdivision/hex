package uk.co.longdivision.hackernews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import uk.co.longdivision.hackernews.listener.ClickListener;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener {
    public LinearLayout mTextView;
    public ClickListener clickListener;

    public ViewHolder(LinearLayout v) {
        super(v);
        mTextView = v;
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v, getPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        clickListener.onClick(v, getPosition(), true);
        return true;
    }
}
