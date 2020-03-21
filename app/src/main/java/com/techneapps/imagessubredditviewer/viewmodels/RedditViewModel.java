package com.techneapps.imagessubredditviewer.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.techneapps.imagessubredditviewer.repo.api.RedditClient;
import com.techneapps.imagessubredditviewer.data.models.Paging;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RedditViewModel extends ViewModel {
    private MutableLiveData<Paging> redditHomePageModelMutableLiveData;

    public MutableLiveData<Paging> getSubRedditModel(String subredditName) {
        if (redditHomePageModelMutableLiveData == null) {
            redditHomePageModelMutableLiveData = new MutableLiveData<>();
        }
        return getSubRedditModelFromServer(subredditName);
    }

    private MutableLiveData<Paging> getSubRedditModelFromServer(String subredditName) {
            RedditClient client = new RedditClient();
            client.getSubRedditPosts(subredditName,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                    JSONArray items = null;
                    try {
                        Paging currentPage = Paging.createPagingObject( subredditName, client.getSubRedditURL(subredditName), responseBody);
                        redditHomePageModelMutableLiveData.setValue(currentPage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        return redditHomePageModelMutableLiveData;
    }

    public MutableLiveData<Paging> getNextRedditHomePageModelFromServer(String subredditName,String after) {
        MutableLiveData<Paging> nextPagingData=new MutableLiveData<>();
        RedditClient client = new RedditClient();
        String nextPageURL = Paging.getNextPageURL(client.getSubRedditURL(subredditName), after);
        //Log.e("nextPageURL",nextPageURL);
        client.getSubRedditPostsByURL(nextPageURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                try {
                    Paging currentPage = Paging.createPagingObject( subredditName, client.getSubRedditURL(""), responseBody);
                    nextPagingData.setValue(currentPage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();
            }
        });

        return nextPagingData;
    }


}
