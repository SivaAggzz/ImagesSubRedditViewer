package com.techneapps.imagessubredditviewer.data.models;

import android.util.Log;

import com.techneapps.imagessubredditviewer.parser.ListingParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Paging {
    //reddit page model
    private String after;
    private String before;
    private int dist;
    private String modhash;
    private ArrayList<RedditPost> children;
    private String currentPageURL;
    private String redditName;

    public static Paging createPagingObject(String subRedditName,String currentPageURL,JSONObject responseBody) throws JSONException {
        Paging currentPagingItem = new Paging();
        JSONObject data = responseBody.getJSONObject("data");
        JSONArray childrens = data.getJSONArray("children");
        currentPagingItem.children = new ListingParser(childrens).parsePostListing();
        currentPagingItem.currentPageURL = currentPageURL;
        currentPagingItem.setRedditName(subRedditName);
        if (data.has("after")) {
            currentPagingItem.after = data.getString("after");
            //Log.e("after",currentPagingItem.after);
        }else {
            //Log.e("after","not found");

        }
        if (data.has("before")) {
            currentPagingItem.before = data.getString("before");
        }
        if (data.has("dist")) {
            currentPagingItem.dist = data.getInt("dist");
        }
        if (data.has("modhash")) {
            currentPagingItem.modhash = data.getString("modhash");
        }
        return currentPagingItem;
    }

    public static String getNextPageURL(String redditURL,String after){
        return redditURL+"?after="+after;
    }

    public String getPreviousPageURL(){
        return "";
    }
    public String getCurrentPageURL() {
        return currentPageURL;
    }


    public String getRedditName() {
        return redditName;
    }

    public void setRedditName(String redditName) {
        this.redditName = redditName;
    }

    public void setCurrentPageURL(String currentPageURL) {
        this.currentPageURL = currentPageURL;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public ArrayList<RedditPost> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<RedditPost> children) {
        this.children = children;
    }
}
