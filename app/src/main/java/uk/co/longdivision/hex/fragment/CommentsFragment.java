package uk.co.longdivision.hex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.longdivision.hex.HexApplication;
import uk.co.longdivision.hex.R;
import uk.co.longdivision.hex.adapter.CommentListAdapter;
import uk.co.longdivision.hex.asynctask.GetItem;
import uk.co.longdivision.hex.asynctask.ItemHandler;
import uk.co.longdivision.hex.decoration.DividerItemDecoration;
import uk.co.longdivision.hex.model.Comment;
import uk.co.longdivision.hex.model.Item;
import uk.co.longdivision.hex.model.Story;
import uk.co.longdivision.hex.viewmodel.CommentViewModel;


public class CommentsFragment extends Fragment implements ItemHandler,
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";
    private boolean mRefreshing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadItem();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_comments, container,
                false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(
                R.id.comment_layout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        setupRefreshLayout();

        return rootView;
    }

    @Override
    public void onItemReady(Item item) {
        Story story = (Story) item;
        List<Comment> comments = story.getComments();
        List<CommentViewModel> viewComments = new ArrayList<>();

        for (Comment comment : comments) {
            addCommentToList(comment, viewComments, 0);
        }

        mAdapter = new CommentListAdapter(getActivity(), viewComments);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        setRefreshing(false);
    }

    public void addCommentToList(Comment comment, List<CommentViewModel> list, int depth) {
        list.add(new CommentViewModel(comment.getUser(), comment.getText(), depth, new Date()));

        for(uk.co.longdivision.hex.model.Comment childComment : comment.getChildComments()) {
            addCommentToList(childComment, list, depth + 1);
        }
    }

    private void loadItem() {
        String storyId = this.getActivity().getIntent().getStringExtra(STORY_ID_INTENT_EXTRA_NAME);
        HexApplication appContext = (HexApplication) this.getContext()
                .getApplicationContext();
        new GetItem(this, appContext).execute(storyId);
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        loadItem();
    }

    private void setupRefreshLayout() {
        mRefreshing = true;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(mRefreshing);
            }
        }, 500);
    }

    private void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }
}
