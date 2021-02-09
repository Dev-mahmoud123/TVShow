package com.mah_awad.tvshows.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.mah_awad.tvshows.R;
import com.mah_awad.tvshows.adapter.WatchlistAdapter;
import com.mah_awad.tvshows.databinding.ActivityWatchListBinding;
import com.mah_awad.tvshows.listeners.WatchlistListener;
import com.mah_awad.tvshows.models.TVShow;
import com.mah_awad.tvshows.utilities.TempDataHolder;
import com.mah_awad.tvshows.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchlistViewModel watchlistViewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        doInitialization();
    }

    private void doInitialization() {
        watchlistViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WatchlistViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(v -> onBackPressed());
        watchlist = new ArrayList<>();
        loadWatchlist();
    }

    private void loadWatchlist() {
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    if (watchlist.size() > 0) {
                        watchlist.clear();
                    }
                    watchlist.addAll(tvShows);
                    watchlistAdapter = new WatchlistAdapter(watchlist, this);
                    activityWatchListBinding.watchlistRecyclerView.setAdapter(watchlistAdapter);
                    activityWatchListBinding.watchlistRecyclerView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();


                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATED){
            loadWatchlist();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchlist(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(watchlistViewModel.removeTVShowFromWatchlist(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchlist.remove(position);
                    watchlistAdapter.notifyItemRemoved(position);
                    watchlistAdapter.notifyItemRangeChanged(position, watchlistAdapter.getItemCount());
                    compositeDisposableForDelete.dispose();
                }));
    }
}