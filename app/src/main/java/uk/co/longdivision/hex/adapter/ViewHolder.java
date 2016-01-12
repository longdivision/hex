package uk.co.longdivision.hex.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import uk.co.longdivision.hex.listener.ClickListener;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public View mView;
    public ClickListener clickListener;

    public ViewHolder(View v) {
        super(v);
        mView = v;
        v.setOnClickListener(this);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v, getAdapterPosition(), false);
    }
}
