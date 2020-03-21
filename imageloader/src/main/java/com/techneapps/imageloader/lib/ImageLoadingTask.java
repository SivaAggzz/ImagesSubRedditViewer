package com.techneapps.imageloader.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.techneapps.imageloader.cache.FileCache;
import com.techneapps.imageloader.cache.MemoryCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ImageLoadingTask implements Runnable {
    private String url;
    private LoadImageResultUpdateTask resultUpdateTask;

    private FileCache fileCache;
    private MemoryCache memoryCache;


    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;


    public ImageLoadingTask(Context context, String urlIn, LoadImageResultUpdateTask drUpdateTask) {
        this.url = urlIn;
        this.resultUpdateTask = drUpdateTask;
        fileCache = new FileCache(context);
        memoryCache = new MemoryCache();
    }

    @Override
    public void run() {
        Bitmap resolvedBitmap = null;
        resolvedBitmap = getBitmap(url);
        //update results download status on the main thread
        resultUpdateTask.setImage(resolvedBitmap);
        ImageLoaderManager.getImageLoaderManager().getMainThreadExecutor()
                .execute(resultUpdateTask);
    }

    private Bitmap getBitmap(String url) {
        //from Memory cache
        Bitmap bitmapMemoryCache = memoryCache.get(url.replace(" ",""));
        if (bitmapMemoryCache!=null){
            Log.e("getBitmap","bitmapMemoryCache");
            return bitmapMemoryCache;
        }else {
            Log.e("getBitmap","no bitmapMemoryCache");

        }

        //from Storage cache
        File bitmapFileCacheFile = fileCache.getFile(url);
        Bitmap bitmapFileCache = null;
        try {
            bitmapFileCache = decodeSampledBitmap(bitmapFileCacheFile);
            if (bitmapFileCache != null) {
                memoryCache.put(url.replace(" ",""),bitmapFileCache);
                return bitmapFileCache;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //finally, no cache so download now
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setInstanceFollowRedirects(true);
            InputStream is = httpURLConnection.getInputStream();
            OutputStream os = new FileOutputStream(bitmapFileCacheFile);
            final int buffer_size = 1024;

            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
            os.close();
            bitmap = decodeSampledBitmap(bitmapFileCacheFile);
            memoryCache.put(url.replace(" ",""),bitmap);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }


    private static Bitmap decodeSampledBitmap(File file) throws FileNotFoundException {
        // Decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        //getting def max height and width
        //loading the best quality == 100%
        //using 90% to show now
        int reqWidth = (options.outWidth / 100) * 90;
        int reqHeight = (options.outHeight / 100) * 90;
        if (reqWidth > 1000 || reqHeight > 1000) {
            reqWidth = (options.outWidth / 100) * 70;
            reqHeight = (options.outHeight / 100) * 70;
        }
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
