package com.njit.yh454.instaclone.fragment;

import android.util.Log;

import com.njit.yh454.instaclone.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileFragment extends PostsFragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_CREATED_AT);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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