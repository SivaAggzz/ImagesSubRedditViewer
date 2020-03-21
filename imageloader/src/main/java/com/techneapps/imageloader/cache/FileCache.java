package com.techneapps.imageloader.cache;

import android.content.Context;

import com.techneapps.imageloader.utils.Constants;

import java.io.File;

import static com.techneapps.imageloader.utils.MustMethods.getFileSize;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(Constants.DEFAULT_APP_DIR_PATH, ".cache");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    private void checkForOptimizations() {
        if (getFileSize(cacheDir) > (1024 * 1024 * 5)) {
            //cache greater than 200mb
            clear();
        }
    }

    public File getFile(String url) {
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        return new File(cacheDir, filename);

    }

    private void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}
