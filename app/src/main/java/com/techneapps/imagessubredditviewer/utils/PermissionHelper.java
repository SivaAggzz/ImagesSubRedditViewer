package com.techneapps.imagessubredditviewer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.techneapps.imagessubredditviewer.utils.MustMethods.showToast;

public class PermissionHelper {
    public static boolean isStoragePermissionGranted(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStoragePermission(Activity activity,int STORAGE_PERMISSION_REQUEST_CODE) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showToast(activity, "Write External Storage permission allows us to do store images. " +
                    "Please allow this permission in App Settings.");
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }
    }
}
