package com.mah_awad.tvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;



import com.mah_awad.tvshows.R;
import com.mah_awad.tvshows.adapter.TVShowAdapter;
import com.mah_awad.tvshows.databinding.ActivityMainBinding;
import com.mah_awad.tvshows.listeners.TVShowListeners;
import com.mah_awad.tvshows.models.TVShow;
import com.mah_awad.tvshows.viewmodels.MostPopularTVShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowListeners {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowViewModel viewModels;
    private List<TVShow> tvShowList = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();

    }

    private void doInitialization() {
        activityMainBinding.tVShowsRecyclerView.setHasFixedSize(true);
        viewModels = new ViewModelProvider(this).get(MostPopularTVShowViewModel.class);
        tvShowAdapter = new TVShowAdapter(tvShowList , this);
        activityMainBinding.tVShowsRecyclerView.setAdapter(tvShowAdapter);
        activityMainBinding.tVShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tVShowsRecyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getMostPopularTVShow();
                    }
                }
            }
        });
        activityMainBinding.imageWatchlist.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), WatchListActivity.class)));
        activityMainBinding.imageSearch.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
        getMostPopularTVShow();

    }

    public void getMostPopularTVShow() {
        toggleLoading();
        viewModels.getMostPopularTVShow(currentPage).observe(this, mostPopularTVShowResponse -> {
            toggleLoading();
            if (mostPopularTVShowResponse != null) {
                totalAvailablePages = mostPopularTVShowResponse.getTotalPages();
                if (mostPopularTVShowResponse.getTvShows() != null) {
                    int oldCount = tvShowList.size();
                    tvShowList.addAll(mostPopularTVShowResponse.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount, tvShowList.size());
                }
            }
        });
    }
    // loading pages
    public void toggleLoading() {
        if (currentPage == 1) {
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }
}