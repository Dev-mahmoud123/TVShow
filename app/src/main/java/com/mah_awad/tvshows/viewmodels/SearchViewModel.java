package com.mah_awad.tvshows.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mah_awad.tvshows.repositories.SearchTVShowRepository;
import com.mah_awad.tvshows.responses.TVShowResponse;

public class SearchViewModel extends ViewModel {

    private SearchTVShowRepository searchTVShowRepository ;

    public SearchViewModel() {
       searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowResponse> getSearchTVShow(String query , int page){
        return searchTVShowRepository.getSearchTVShow(query , page);
    }
}
