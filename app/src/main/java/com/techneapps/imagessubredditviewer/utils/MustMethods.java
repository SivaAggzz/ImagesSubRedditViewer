package com.techneapps.imagessubredditviewer.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.techneapps.imageloader.lib.ImageLoaderBuilder;

public class MustMethods {

    public static void loadImage(Context context,String url, ImageView imageView) {
        //load image in imageview using my custom made library
        new ImageLoaderBuilder.Builder().with(context).load(url).into(imageView);
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }


    //using def download manager to download file
    public static void startDownloadFileWithDefDM(Context context, String url,String filename) {
        showToast(context,"Download started");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Downloading Image");
        request.setTitle(filename.replace(".","")+".jpg");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename.replace(".","")+".jpg");
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
        }
    }
}
