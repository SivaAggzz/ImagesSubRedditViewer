package com.techneapps.imagessubredditviewer.repo.api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class RedditClient {
    private AsyncHttpClient client;

    public RedditClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String subreddit) {
        String url = "https://www.reddit.com/";
        if(!subreddit.equals("")){
            url += "r/" + subreddit;
        }
        return url + ".json";
    }


    public String getSubRedditURL(String subRedditName) {
        return getApiUrl(subRedditName);
    }

    public void getSubRedditPosts(String subRedditName,JsonHttpResponseHandler handler) {
        //used for accessing sub reddit by name
        String url = getApiUrl(subRedditName);
        client.get(url, handler);
    }
    public void getSubRedditPostsByURL(String pageURL,JsonHttpResponseHandler handler) {
        //used for accessing sub reddit by url
        client.get(pageURL, handler);
    }

}