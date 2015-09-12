package uk.co.longdivision.hackernews.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.longdivision.hackernews.R;
import uk.co.longdivision.hackernews.listener.ClickListener;
import uk.co.longdivision.hackernews.viewmodel.ItemListItem;

public class HomeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<ItemListItem> mDataset;
    private ClickListener mClickListener;

    public HomeAdapter(ArrayList<ItemListItem> myDataset, ClickListener clickListener) {
        mDataset = myDataset;
        mClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemListItem item = mDataset.get(position);
        TextView tv = (TextView) holder.mTextView.findViewById(R.id.title);
        holder.setClickListener(mClickListener);
        tv.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
