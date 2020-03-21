package com.techneapps.imageloader.lib;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageLoaderManager {
    private final ThreadPoolExecutor threadPoolExecutor;
    private final BlockingQueue<Runnable> runnableBlockingQueue;

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 50;

    private static ImageLoaderManager imageLoaderManager = null;
    private static MainThreadExecutor handler;

    static {
        imageLoaderManager = new ImageLoaderManager();
        handler = new MainThreadExecutor();
    }

    private ImageLoaderManager(){
        runnableBlockingQueue = new LinkedBlockingQueue<>();

        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, runnableBlockingQueue);
    }

    public static ImageLoaderManager getImageLoaderManager() {
        return imageLoaderManager;
    }

    public void runImageLoadingTask(Runnable task){
        threadPoolExecutor.execute(task);
    }

    //to runs task on main thread from background thread
    public MainThreadExecutor getMainThreadExecutor(){
        return handler;
    }

}
