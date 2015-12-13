package uk.co.longdivision.hex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import uk.co.longdivision.hex.R;
import uk.co.longdivision.hex.listener.ClickListener;
import uk.co.longdivision.hex.viewmodel.ItemListItemViewModel;


public class FrontPageListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ItemListItemViewModel> mDataset;
    private ClickListener mClickListener;

    public FrontPageListAdapter(List<ItemListItemViewModel> myDataset, ClickListener clickListener) {
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
        ItemListItemViewModel item = mDataset.get(position);

        TextView scoreText = (TextView) holder.mTextView.findViewById(R.id.score);
        TextView domainText = (TextView) holder.mTextView.findViewById(R.id.domain);
        TextView titleText = (TextView) holder.mTextView.findViewById(R.id.title);
        TextView commentCountText = (TextView) holder.mTextView.findViewById(R.id.commentCount);
        TextView relativeTimeText = (TextView) holder.mTextView.findViewById(R.id.relative_time);

        holder.setClickListener(mClickListener);

        scoreText.setText(item.getScore());
        domainText.setText(item.getDomain());
        titleText.setText(item.getTitle());
        commentCountText.setText(item.getCommentCount());
        relativeTimeText.setText(item.getRelativeTime());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
