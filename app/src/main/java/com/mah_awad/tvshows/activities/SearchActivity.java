package com.mah_awad.tvshows.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;


import com.mah_awad.tvshows.R;
import com.mah_awad.tvshows.adapter.TVShowAdapter;
import com.mah_awad.tvshows.databinding.ActivitySearchBinding;
import com.mah_awad.tvshows.listeners.TVShowListeners;
import com.mah_awad.tvshows.models.TVShow;
import com.mah_awad.tvshows.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowListeners {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<TVShow> tvShowList = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        doInitialization();
    }

    private void doInitialization() {
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.searchRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        tvShowAdapter = new TVShowAdapter(tvShowList, this);
        activitySearchBinding.searchRecyclerView.setAdapter(tvShowAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchTVShow(editable.toString());
                            });
                        }
                    }, 800);
                } else {
                    tvShowList.clear();
                    tvShowAdapter.notifyDataSetChanged();
                }
            }
        });

        activitySearchBinding.searchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.searchRecyclerView.canScrollVertically(1)) {
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTVShow(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }

    private void searchTVShow(String query) {
        toggleLoading();
        searchViewModel.getSearchTVShow(query, currentPage).observe(this, tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totalAvailablePages = tvShowResponse.getTotalPages();
                if (tvShowResponse.getTvShows() != null) {
                    int oldCount = tvShowList.size();
                    tvShowList.addAll(tvShowResponse.getTvShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount, tvShowList.size());
                }
            }
        });
    }

    // loading pages
    public void toggleLoading() {
        if (currentPage == 1) {
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()) {
                activitySearchBinding.setIsLoading(false);
            } else {
                activitySearchBinding.setIsLoading(true);
            }
        } else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()) {
                activitySearchBinding.setIsLoadingMore(false);
            } else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}