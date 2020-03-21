package com.techneapps.imagessubredditviewer.view.adapter.recyclerview.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.imagessubredditviewer.databinding.SingleRedditImagePostBinding;

public class RedditPostViewHolder extends RecyclerView.ViewHolder {

    public SingleRedditImagePostBinding imagePostBinding;
    public RedditPostViewHolder(@NonNull SingleRedditImagePostBinding imagePostBinding) {
        super(imagePostBinding.getRoot());
        this.imagePostBinding=imagePostBinding;
    }
}
