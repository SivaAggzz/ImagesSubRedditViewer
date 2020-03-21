package com.techneapps.imageloader.lib;

import android.content.Context;
import android.widget.ImageView;

import com.techneapps.imageloader.lib.ImageLoaderManager;
import com.techneapps.imageloader.lib.ImageLoadingTask;
import com.techneapps.imageloader.lib.LoadImageResultUpdateTask;

public final class ImageLoaderBuilder {

    public static final class Builder {
        private Context context;
        private String imageURL;
        private ImageView imageView;

        public Builder() {
        }


        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder load(String imageURL) {
            this.imageURL = imageURL;
            return this;
        }

        public void into(ImageView imageView) {
            this.imageView = imageView;
            loadImageNow();
        }

        private void loadImageNow() {
            if (this.context == null) {
                throw new IllegalStateException("No context object found!Please pass a context instance");
            }
            // if (this.imageURL==null|| TextUtils.isEmpty(this.imageURL)){
            //throw new IllegalStateException("No url found!Please pass a url to load");
            //}
            this.imageView.setImageDrawable(null);
            LoadImageResultUpdateTask loadImageResultUpdateTask = new LoadImageResultUpdateTask(this.imageView);
            ImageLoadingTask imageLoadingTask = new ImageLoadingTask(this.context, imageURL, loadImageResultUpdateTask);
            ImageLoaderManager.getImageLoaderManager().runImageLoadingTask(imageLoadingTask);
        }
    }
}
