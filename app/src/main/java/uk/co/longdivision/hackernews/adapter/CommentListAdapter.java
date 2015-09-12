package uk.co.longdivision.hackernews.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.longdivision.hackernews.R;
import uk.co.longdivision.hackernews.viewmodel.Comment;

public class CommentListAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private static final String[] INDENT_COLORS = {
            "#00BCD4", // Cyan
            "#2196F3", // Blue
            "#8BC34A", // Light green
            "#E91E63", // Pink
            "#CDDC39", // Lime
            "#FFEB3B", // Yellow
            "#FFC107", // Indigo
            "#00BCD4", // Cyan
    };

    private final Context mContext;

    private ArrayList<Comment> mDataset;

    public CommentListAdapter(Context context, ArrayList<Comment> myDataset) {
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
        Comment comment = mDataset.get(position);
        View commentView = holder.mCommentView;

        renderView(commentView, comment);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void renderView(View commentView, Comment comment) {
        TextView usernameView = (TextView) commentView.findViewById(R.id.username);
        TextView relativeTimeView = (TextView) commentView.findViewById(R.id.relative_time);
        TextView textView = (TextView) commentView.findViewById(R.id.text);
        LinearLayout indentView = (LinearLayout) commentView.findViewById(R.id.indent);

        usernameView.setText(comment.getUser());
        relativeTimeView.setText(comment.getRelativeTime());
        textView.setText(comment.getText());
        setIndentSize(indentView, comment.getDepth());
    }

    private void setIndentSize(View indentView, int depth) {
        LinearLayout indentBarView = (LinearLayout) indentView.findViewById(R.id.indent_bar);

        if (depth <= 0) {
            indentView.getLayoutParams().width = 0;
            indentBarView.setVisibility(View.INVISIBLE);
        } else {
            int indentSize = depth * 5;
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int indentSizeScaledForDisplay = Math.round(
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indentSize, metrics));
            int indentColor = Color.parseColor(INDENT_COLORS[depth % INDENT_COLORS.length]);

            indentView.getLayoutParams().width = indentSizeScaledForDisplay;
            indentView.setVisibility(View.VISIBLE);

            indentBarView.setBackgroundColor(indentColor);
        }
    }
}
