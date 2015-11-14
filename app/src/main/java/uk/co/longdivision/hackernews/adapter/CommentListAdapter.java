package uk.co.longdivision.hackernews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import uk.co.longdivision.hackernews.R;
import uk.co.longdivision.hackernews.viewmodel.CommentViewModel;


public class CommentListAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private final static int INDENT_SIZE = 5;
    private final Context mContext;
    private List<CommentViewModel> mDataset;

    public CommentListAdapter(Context context, List<CommentViewModel> myDataset) {
        mContext = context;
        mDataset = myDataset;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentViewModel comment = mDataset.get(position);
        View commentView = holder.mCommentView;

        renderView(commentView, comment);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void renderView(View commentView, CommentViewModel comment) {
        TextView usernameView = (TextView) commentView.findViewById(R.id.username);
        TextView relativeTimeView = (TextView) commentView.findViewById(R.id.relative_time);
        TextView textView = (TextView) commentView.findViewById(R.id.text);
        LinearLayout indentView = (LinearLayout) commentView.findViewById(R.id.indent);

        usernameView.setText(comment.getUser());
        relativeTimeView.setText(comment.getRelativeTime());
        textView.setText(Html.fromHtml(comment.getText()));
        setupIndent(indentView, comment.getDepth());
    }

    private void setupIndent(View indentView, int depth) {
        LinearLayout indentBarView = (LinearLayout) indentView.findViewById(R.id.indent_bar);

        if (depth <= 0) {
            hideIndent(indentBarView, indentView);
        } else {
            positionAndColorIndentForDepth(indentBarView, indentView, depth);
        }
    }

    private void hideIndent(View indentBarView, View indentView) {
        indentView.getLayoutParams().width = 0;

        indentView.setVisibility(View.INVISIBLE);
        indentBarView.setVisibility(View.INVISIBLE);
    }

    private void positionAndColorIndentForDepth(View indentBarView, View indentView, int depth) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

        int indentSize = depth * INDENT_SIZE;
        int indentSizeScaledForDisplay = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indentSize, metrics));
        indentView.getLayoutParams().width = indentSizeScaledForDisplay;

        int[] indentColors = mContext.getResources().getIntArray(R.array.comment_colors);
        int indentColor = indentColors[depth % indentColors.length];
        indentBarView.setBackgroundColor(indentColor);

        indentView.setVisibility(View.VISIBLE);
        indentBarView.setVisibility(View.VISIBLE);
    }
}
