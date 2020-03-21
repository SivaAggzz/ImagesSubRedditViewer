package com.techneapps.imagessubredditviewer.parser;


import com.techneapps.imagessubredditviewer.data.models.RedditPost;

import org.json.JSONException;
import org.json.JSONObject;

import static com.techneapps.imageloader.utils.MustMethods.isImageFile;


public class PostParser {
    private JSONObject jsonObject;

    public PostParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public RedditPost getPostFromJson(){
        RedditPost redditPost = new RedditPost();
        try{
            redditPost.setTitle(jsonObject.getString("title"));
            redditPost.setPostAuthor(jsonObject.getString("author"));

            redditPost.setCreatedTimeStamp(jsonObject.getLong("created"));
            redditPost.setOver18(jsonObject.getBoolean("over_18"));

            redditPost.setDomain (jsonObject.getString("domain"));
            redditPost.setNumComments ( jsonObject.getInt("num_comments"));
            redditPost.setSubreddit ( jsonObject.getString("subreddit"));
            redditPost.setUpvotes ( jsonObject.getInt("score"));

            redditPost.setPermaLink(jsonObject.getString("permalink"));
            try{
                String url = jsonObject.getString("url");
                redditPost.setImageURL(url);
                if (isImageFile(url)) {
                    redditPost.setImage(true);
                }else {
                    redditPost.setImage(false);
                }
            } catch (JSONException e){
                // There is no thumbnail
                redditPost.setImageURL("");
                redditPost.setImage(false);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return redditPost;
    }



}
