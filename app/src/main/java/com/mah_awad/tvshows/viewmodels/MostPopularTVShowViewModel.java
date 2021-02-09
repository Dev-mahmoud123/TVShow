package com.mah_awad.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mah_awad.tvshows.repositories.MostPopularTVShowRepositories;
import com.mah_awad.tvshows.responses.TVShowResponse;

public class MostPopularTVShowViewModel extends ViewModel {

    private MostPopularTVShowRepositories mostPopularTVShowRepositories;

    public MostPopularTVShowViewModel(){
        mostPopularTVShowRepositories = new MostPopularTVShowRepositories();
    }

    public LiveData<TVShowResponse> getMostPopularTVShow(int page){
        return mostPopularTVShowRepositories.getMostPopularTVShows(page);
    }
}
