package com.techneapps.imagessubredditviewer.utils;

import android.widget.ImageView;

import com.techneapps.imagessubredditviewer.data.models.RedditPost;

import static com.techneapps.imagessubredditviewer.utils.MustMethods.loadImage;

public class BindingAdapter {

    //load post image
    @androidx.databinding.BindingAdapter(value = "thumbnailURL")
    public static void loadPostThumbnail(ImageView imageView, RedditPost redditPost) {
        if (redditPost.getImageURL().length() >= 1 && !redditPost.getImageURL().equals("self")) {
            loadImage(imageView.getContext(),redditPost.getImageURL(),imageView);
        }
    }

}
