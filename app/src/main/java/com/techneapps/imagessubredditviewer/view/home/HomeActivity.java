package com.techneapps.imagessubredditviewer.view.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techneapps.imagessubredditviewer.R;
import com.techneapps.imagessubredditviewer.data.models.Paging;
import com.techneapps.imagessubredditviewer.data.models.RedditPost;
import com.techneapps.imagessubredditviewer.databinding.ActivityHomeBinding;
import com.techneapps.imagessubredditviewer.utils.PermissionHelper;
import com.techneapps.imagessubredditviewer.view.adapter.recyclerview.RedditPostAdapter;
import com.techneapps.imagessubredditviewer.viewmodels.RedditViewModel;

import java.util.ArrayList;
import java.util.Objects;

import static com.techneapps.imagessubredditviewer.utils.MustMethods.showToast;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding homeBinding;
    private RedditViewModel redditViewModel;
    private RedditPostAdapter redditPostAdapter;
    private ArrayList<RedditPost> postsFetched = new ArrayList<>();
    private Paging currentPage;
    private boolean isLoading = false;
    private static final int STORAGE_REQ_CODE = 8912;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        redditViewModel = new ViewModelProvider(this).get(RedditViewModel.class);
        initializeViews();

    }

    private void initializeViews() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("/r/images");
        checkStoragePermission();
    }

    private void checkStoragePermission() {
        if (PermissionHelper.isStoragePermissionGranted(this)) {
            observeRedditHomePage();
        } else {
            PermissionHelper.requestStoragePermission(this, STORAGE_REQ_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.toolbar_bg_color));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionHelper.isStoragePermissionGranted(getApplicationContext())) {
            observeRedditHomePage();
        } else {
            showToast(this, "Cannot continue without storage Permission");
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void observeRedditHomePage() {
        redditPostAdapter = new RedditPostAdapter(getApplicationContext(), postsFetched);
        homeBinding.feedsView.setAdapter(redditPostAdapter);

        redditViewModel.getSubRedditModel("images/hot")
                .observe(this, pageModel -> {
                    currentPage = pageModel;
                    postsFetched.addAll(currentPage.getChildren());
                    redditPostAdapter.notifyDataSetChanged();
                    homeBinding.progressBar.setVisibility(View.GONE);
                    initScrollListenerForLoadingNextPage();
                });

    }

    private void initScrollListenerForLoadingNextPage() {
        homeBinding.feedsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition()
                            == postsFetched.size() - 1) {
                        loadMorePages(currentPage.getAfter());
                    }
                }
            }
        });
    }

    private void loadMorePages(String after) {
        homeBinding.progressBar.setVisibility(View.VISIBLE);
        isLoading = true;
        redditViewModel.getNextRedditHomePageModelFromServer("images/hot", after)
                .observe(this, paging -> {
                    postsFetched.addAll(paging.getChildren());
                    currentPage = paging;
                    redditPostAdapter.notifyDataSetChanged();
                    isLoading = false;
                    homeBinding.progressBar.setVisibility(View.GONE);
                });


    }
}
