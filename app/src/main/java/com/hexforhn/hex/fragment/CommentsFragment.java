package com.hexforhn.hex.fragment;

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
import com.hexforhn.hex.viewmodel.CommentViewModel;

import java.util.List;


public class CommentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mRefreshing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_comments, container,
                false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        setupItemsUnavailableView(rootView);
        setupRefreshLayout(rootView);

        return rootView;
    }

    public void onCommentsReady(List<CommentViewModel> comments) {
        mAdapter = new CommentListAdapter(getActivity(), comments);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        setRefreshing(false);
        updateRefreshSpinner();
        showCommentList();
    }

    public void onCommentsUnavailable() {
        setRefreshing(false);
        updateRefreshSpinner();
        showCommentsUnavailable();
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        updateRefreshSpinner();
        requestNewComments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroy();
        mSwipeRefreshLayout.setOnRefreshListener(null);
    }

    private void setupRefreshLayout(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(
                R.id.comment_layout);

        setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateRefreshSpinner();
            }
        }, 500);
    }

    private void setupItemsUnavailableView(View rootView) {
        ((TextView) rootView.findViewById((R.id.loading_failed_text)))
                .setText(R.string.unable_to_load_comments);
        Button tryAgain = (Button) rootView.findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNewComments();
            }
        });
    }

    private void setRefreshing(boolean refreshing) {
        mRefreshing = refreshing;
    }

    private void updateRefreshSpinner() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(mRefreshing);
        }
    }

    private void showCommentList() {
        getView().findViewById(R.id.comment_layout).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.content_unavailable).setVisibility(View.GONE);
    }

    private void showCommentsUnavailable() {
        getView().findViewById(R.id.comment_layout).setVisibility(View.GONE);
        getView().findViewById(R.id.content_unavailable).setVisibility(View.VISIBLE);
    }

    private void requestNewComments() {
        ((StoryActivity) getActivity()).onCommentRefreshRequested();
    }
}
