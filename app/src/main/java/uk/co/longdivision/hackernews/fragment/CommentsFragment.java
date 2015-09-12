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

import uk.co.longdivision.hackernews.R;
import uk.co.longdivision.hackernews.adapter.CommentListAdapter;
import uk.co.longdivision.hackernews.decoration.DividerItemDecoration;
import uk.co.longdivision.hackernews.viewmodel.Comment;

public class CommentsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_comments, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comment_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("jfaucett", "Awesome. I'd just like to thank the whole Debian team myself - I'm not affiliated so its not a pat on my own back :). I've been running Debian for years now on my servers, on my own personal computers and its a great system, so thanks!", 0, new Date()));
        comments.add(new Comment("jfaucett", "Not at all, devuan is not considered trustworthy by the majority (to put it mildly).", 1, new Date()));
        comments.add(new Comment("jfaucett", "As a speculation from an outsider, I'll list these reasons: Release team was very motivated in this cycle (auto removal of packages, not letting any new package in freeze, very strict exceptions even for packages that fix bugs etc.), most of current rc bugs are lurking there for months without any progress (and since Debian is all volunteer work, they can't force anybody to fix 'em), and they couldn't also remove those packages because they've already removed packages they could remove. Another thing is some of those RC bugs are security issues, which are handled by Security Team for stable and oldstable, so there is no reason for security issues to delay the release. When all these are considered, they may have not deemed further delaying the release worthwhile.", 1, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 2, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 3, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 4, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 5, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 6, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 7, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 8, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 9, new Date()));
        comments.add(new Comment("jfaucett", "Took a quick look at some of the bugs that would affect me... It's systemd, systemd and systemd.", 10, new Date()));
        comments.add(new Comment("jfaucett", "Awesome. I'd just like to thank the whole Debian team myself - I'm not affiliated so its not a pat on my own back :). I've been running Debian for years now on my servers, on my own personal computers and its a great system, so thanks!", 0, new Date()));
        comments.add(new Comment("jfaucett", "Awesome. I'd just like to thank the whole Debian team myself - I'm not affiliated so its not a pat on my own back :). I've been running Debian for years now on my servers, on my own personal computers and its a great system, so thanks!", 0, new Date()));

        mAdapter = new CommentListAdapter(getActivity(), comments);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
