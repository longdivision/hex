package com.hexforhn.hex.fragment.comments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hexforhn.hex.R;
import com.hexforhn.hex.activity.story.StoryActivity;
import com.hexforhn.hex.adapter.CommentListAdapter;
import com.hexforhn.hex.util.view.RefreshHandler;
import com.hexforhn.hex.util.view.SwipeRefreshManager;
import com.hexforhn.hex.viewmodel.CommentViewModel;

import java.util.ArrayList;
import java.util.List;


public class CommentsFragment extends Fragment implements CommentsStateHandler, RefreshHandler {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshManager mSwipeRefreshManager;
    private CommentsState mCommentsState;
    private List<CommentViewModel> mComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_comments, container,
                false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.commentList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mComments = new ArrayList<>();

        setupItemsUnavailableView(rootView);
        setupRefreshLayout(rootView);
        setupState();

        return rootView;
    }

    @Override
    public void onEnterLoading() {
        requestNewComments();
        mSwipeRefreshManager.start();
        mSwipeRefreshManager.disable();
    }

    @Override
    public void onEnterLoaded() {
        mAdapter = new CommentListAdapter(getActivity(), mComments);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshManager.stop();
        mSwipeRefreshManager.enable();
        showCommentList();
        hideCommentsUnavailable();
    }

    @Override
    public void onEnterRefresh() {
        requestNewComments();
        mSwipeRefreshManager.start();
    }

    @Override
    public void onEnterUnavailable() {
        mSwipeRefreshManager.stop();
        if (mComments.isEmpty()) {
            mSwipeRefreshManager.disable();
            hideCommentList();
            showCommentsUnavailable();
        } else {
            mSwipeRefreshManager.enable();
        }
    }

    public void onCommentsReady(List<CommentViewModel> comments) {
        mComments = comments;
        mCommentsState.sendEvent(CommentsState.Event.COMMENTS_PROVIDED);
    }

    public void onCommentsUnavailable() {
        mCommentsState.sendEvent(CommentsState.Event.COMMENTS_UNAVAILABLE);
    }

    @Override
    public void onRefresh() {
        mCommentsState.sendEvent(CommentsState.Event.LOAD_REQUESTED);
    }

    private void setupState() {
        mCommentsState = new CommentsState(this);
    }

    private void setupItemsUnavailableView(View rootView) {
        ((TextView) rootView.findViewById((R.id.loading_failed_text)))
                .setText(R.string.error_unableToLoadComments);
        Button tryAgain = (Button) rootView.findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentsState.sendEvent(CommentsState.Event.LOAD_REQUESTED);
            }
        });
    }

    private void setupRefreshLayout(View rootView) {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        mSwipeRefreshManager = new SwipeRefreshManager(refreshLayout, this);
    }

    private void showCommentList() {
        getView().findViewById(R.id.commentList).setVisibility(View.VISIBLE);
    }

    private void hideCommentList() {
        getView().findViewById(R.id.commentList).setVisibility(View.GONE);
    }

    private void showCommentsUnavailable() {
        getView().findViewById(R.id.content_unavailable).setVisibility(View.VISIBLE);
    }

    private void hideCommentsUnavailable() {
        getView().findViewById(R.id.content_unavailable).setVisibility(View.GONE);
    }

    private void requestNewComments() {
        ((StoryActivity) getActivity()).onCommentRefreshRequested();
    }
}
