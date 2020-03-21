package com.techneapps.imageloader.lib;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.techneapps.imageloader.R;

class LoadImageResultUpdateTask implements Runnable {
    private ImageView imageView;
    private Bitmap bitmap;

    private final int defPlaceHolder = R.color.md_grey_300;

    LoadImageResultUpdateTask(ImageView imageView) {
        this.imageView = imageView;
    }

    void setImage(Bitmap incomingBitmap) {
        this.bitmap = incomingBitmap;
    }

    @Override
    public void run() {
        if (this.bitmap != null) {
            imageView.setImageBitmap(this.bitmap);
        } else {
            imageView.setImageResource(defPlaceHolder);
        }
    }


}