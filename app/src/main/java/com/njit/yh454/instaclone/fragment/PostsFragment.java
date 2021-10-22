package com.njit.yh454.instaclone.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njit.yh454.instaclone.models.Post;
import com.njit.yh454.instaclone.R;
import com.njit.yh454.instaclone.models.PostsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";

    private RecyclerView rvPosts;
    protected SwipeRefreshLayout swipeRefreshContainer;

    protected PostsAdapter adapter;
    protected List<Post> allPosts;


    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        swipeRefreshContainer = view.findViewById(R.id.scPosts);

        swipeRefreshContainer.setOnRefreshListener(() -> this.queryPosts());

        swipeRefreshContainer.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        allPosts = new ArrayList<>();
        // create row layout
        // create adapter
        adapter = new PostsAdapter(getContext(), allPosts);
        // create source
        // set adapter on recycler
        rvPosts.setAdapter(adapter);
        // sort layout manager on recycler
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        this.queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_CREATED_AT);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "queryPosts: Error getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "queryPosts: Post: " + post.getDescription() + " by user " + post.getUser().getUsername());
                    Log.i(TAG, "queryPosts: createdAt " + post.getCreatedAt());
                }
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeRefreshContainer.setRefreshing(false);
            }
        });
    }
}