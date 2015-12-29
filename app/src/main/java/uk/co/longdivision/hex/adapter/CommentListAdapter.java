package uk.co.longdivision.hex.adapter;

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

import uk.co.longdivision.hex.R;
import uk.co.longdivision.hex.adapter.helper.CommentListManager;
import uk.co.longdivision.hex.adapter.helper.TextHelper;
import uk.co.longdivision.hex.listener.ClickListener;
import uk.co.longdivision.hex.viewmodel.CommentViewModel;


public class CommentListAdapter extends RecyclerView.Adapter<ViewHolder> implements ClickListener {

    private final static int INDENT_SIZE = 5;
    private final Context mContext;
    private CommentListManager mCommentListManager;

    public CommentListAdapter(Context context, List<CommentViewModel> comments) {
        mContext = context;
        mCommentListManager = new CommentListManager(comments);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.comment, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentViewModel comment = mCommentListManager.getVisibleComments().get(position);
        renderCommentIntoView(comment, holder.mView);
    }

    @Override
    public int getItemCount() {
        return mCommentListManager.getVisibleComments().size();
    }

    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        mCommentListManager.toggleCommentAtPosition(position);
        notifyDataSetChanged();
    }

    private void renderCommentIntoView(CommentViewModel comment, View commentView) {
        TextView usernameView = (TextView) commentView.findViewById(R.id.username);
        TextView relativeTimeView = (TextView) commentView.findViewById(R.id.relativeTime);
        TextView textView = (TextView) commentView.findViewById(R.id.text);
        LinearLayout indentView = (LinearLayout) commentView.findViewById(R.id.indent);
        View childCommentsHidden = commentView.findViewById(R.id.childCommentsHidden);

        usernameView.setText(comment.getUser());
        relativeTimeView.setText(comment.getRelativeTime());
        textView.setText(TextHelper.removeTrailingNewlinesFromText(Html.fromHtml(comment.getText())));

        setupIndent(indentView, comment.getDepth());

        if (comment.isCollapsed() && comment.getCommentCount() > 0) {
            TextView commentCount = (TextView) commentView.findViewById(R.id.childCommentCount);
            commentCount.setText(String.valueOf(comment.getCommentCount()));
            childCommentsHidden.setVisibility(View.VISIBLE);
        } else {
            childCommentsHidden.setVisibility(View.GONE);
        }
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
