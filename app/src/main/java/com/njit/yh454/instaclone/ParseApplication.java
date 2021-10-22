package com.njit.yh454.instaclone;

import android.app.Application;

import com.njit.yh454.instaclone.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("hwwwnfO3i24KoBJjNDfcDZajA1JMpdjjCNc3exez")
                .clientKey("8gzJkxZ3gAhLYBjcYlsO7ExRn8FyWDXpH9zuS32D")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
