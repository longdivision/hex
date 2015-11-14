package uk.co.longdivision.hackernews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.longdivision.hackernews.HackerNewsApplication;
import uk.co.longdivision.hackernews.R;
import uk.co.longdivision.hackernews.adapter.CommentListAdapter;
import uk.co.longdivision.hackernews.asynctask.GetItem;
import uk.co.longdivision.hackernews.asynctask.ItemHandler;
import uk.co.longdivision.hackernews.decoration.DividerItemDecoration;
import uk.co.longdivision.hackernews.model.Comment;
import uk.co.longdivision.hackernews.model.Item;
import uk.co.longdivision.hackernews.model.Story;
import uk.co.longdivision.hackernews.viewmodel.CommentViewModel;


public class CommentsFragment extends Fragment implements ItemHandler {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private final static String STORY_ID_INTENT_EXTRA_NAME = "storyId";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadItem();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_comments, container,
                false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

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
    }

    public void addCommentToList(Comment comment, List<CommentViewModel> list, int depth) {
        list.add(new CommentViewModel(comment.getUser(), comment.getText(), depth, new Date()));

        for(uk.co.longdivision.hackernews.model.Comment childComment : comment.getChildComments()) {
            addCommentToList(childComment, list, depth + 1);
        }
    }

    private void loadItem() {
        String storyId = this.getActivity().getIntent().getStringExtra(STORY_ID_INTENT_EXTRA_NAME);
        HackerNewsApplication appContext = (HackerNewsApplication) this.getContext()
                .getApplicationContext();
        new GetItem(this, appContext).execute(storyId);
    }
}
