package com.techneapps.imagessubredditviewer.view.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.imagessubredditviewer.R;
import com.techneapps.imagessubredditviewer.data.models.RedditPost;
import com.techneapps.imagessubredditviewer.databinding.SingleRedditImagePostBinding;
import com.techneapps.imagessubredditviewer.view.adapter.recyclerview.clickhandler.RedditPostItemClickListener;
import com.techneapps.imagessubredditviewer.view.adapter.recyclerview.viewholder.RedditPostViewHolder;

import java.util.ArrayList;


public class RedditPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<RedditPost> redditPosts;


    public RedditPostAdapter(Context context, ArrayList<RedditPost> redditPosts) {
        this.context = context;
        this.redditPosts = redditPosts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);


        SingleRedditImagePostBinding imagePostBinding =
                DataBindingUtil.inflate(inflater, R.layout.single_reddit_image_post, parent, false);
        return new RedditPostViewHolder(imagePostBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RedditPost post = redditPosts.get(position);
        ((RedditPostViewHolder) viewHolder).imagePostBinding.setRedditPost(post);
        ((RedditPostViewHolder) viewHolder).imagePostBinding
                .setClickHandler(new RedditPostItemClickListener());


    }

    @Override
    public int getItemCount() {
        return redditPosts == null ? 0 : redditPosts.size();
    }


}