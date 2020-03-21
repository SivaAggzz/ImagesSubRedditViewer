package com.techneapps.imagessubredditviewer.parser;

import com.techneapps.imagessubredditviewer.data.models.RedditPost;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListingParser {
    private JSONArray jsonArray;

    public ListingParser(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public ArrayList<RedditPost> parsePostListing() {
        ArrayList<RedditPost> posts = new ArrayList<>(jsonArray.length());
        // Convert each element in the json array to a json object, then to a Post
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject postJson = null;
            try {
                postJson = jsonArray.getJSONObject(i).getJSONObject("data");
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            RedditPost post = new PostParser(postJson).getPostFromJson();
            if (post != null) {
                if (post.isImage()) {
                    posts.add(post);
                }
            }

        }

        return posts;
    }
}
