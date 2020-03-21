package com.techneapps.imagessubredditviewer.view.imageviewer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.techneapps.imagessubredditviewer.R;
import com.techneapps.imagessubredditviewer.databinding.ActivityImageViewerBinding;
import com.techneapps.imagessubredditviewer.utils.MustMethods;

import java.util.Objects;

import static com.techneapps.imagessubredditviewer.utils.MustMethods.loadImage;

public class ImageViewer extends AppCompatActivity {

    private String incomingImageURL;
    private String incomingFileName;
    private ActivityImageViewerBinding imageViewerBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        imageViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer);
        initializeViews();
    }

    private void initializeViews() {
        incomingImageURL = Objects.requireNonNull(getIntent().getData()).toString();
        incomingFileName = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).getString("name"));
        loadImage(getApplicationContext(),incomingImageURL, imageViewerBinding.imageView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void finishActivity(View view) {
        finish();
    }


    public void downloadImage(View view) {
        MustMethods.startDownloadFileWithDefDM(getApplicationContext(), incomingImageURL, incomingFileName);
    }


}
