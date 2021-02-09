package com.mah_awad.tvshows.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.mah_awad.tvshows.database.TVShowDatabase;
import com.mah_awad.tvshows.models.TVShow;
import com.mah_awad.tvshows.repositories.TVShowDetailsRepository;
import com.mah_awad.tvshows.responses.TVShowDetailsResponse;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository tvShowDetailsRepository;
    private TVShowDatabase tvShowDatabase ;

    public TVShowDetailsViewModel(@NonNull Application application ) {
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowDatabase = TVShowDatabase.getTVShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId) {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList (TVShow tvShow ){
        return tvShowDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowFromWatchlist(String tvShow){
        return tvShowDatabase.tvShowDao().getTVShowFromWatchlist(tvShow);
    }

    public Completable removeTVShowFromWatchlist(TVShow tvShow ){
        return tvShowDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}
