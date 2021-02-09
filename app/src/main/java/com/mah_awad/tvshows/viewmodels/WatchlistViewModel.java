package com.mah_awad.tvshows.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.mah_awad.tvshows.database.TVShowDatabase;
import com.mah_awad.tvshows.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class WatchlistViewModel  extends AndroidViewModel {

    private TVShowDatabase tvShowDatabase ;

    public  WatchlistViewModel(@NonNull Application application){
        super(application);
        tvShowDatabase = TVShowDatabase.getTVShowDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchlist(){
        return tvShowDatabase.tvShowDao().getWatchList();
    }

    public Completable removeTVShowFromWatchlist(TVShow tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
