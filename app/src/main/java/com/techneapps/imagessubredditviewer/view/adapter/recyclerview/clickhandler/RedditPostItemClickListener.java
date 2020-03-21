package com.techneapps.imagessubredditviewer.view.adapter.recyclerview.clickhandler;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.techneapps.imagessubredditviewer.data.models.RedditPost;
import com.techneapps.imagessubredditviewer.view.imageviewer.ImageViewer;

public class RedditPostItemClickListener {
    //click handler for recyclerview
    public RedditPostItemClickListener() {
    }

    public void onItemClicked(View view, RedditPost redditPost) {
        Intent imageViewerIntent = new Intent(view.getContext(), ImageViewer.class);
        imageViewerIntent.setData(Uri.parse(redditPost.getImageURL()));
        imageViewerIntent.putExtra("name",redditPost.getTitle());
        imageViewerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(imageViewerIntent);
    }
}
