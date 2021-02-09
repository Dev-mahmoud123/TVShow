package com.mah_awad.tvshows.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mah_awad.tvshows.network.ApiClient;
import com.mah_awad.tvshows.network.ApiService;
import com.mah_awad.tvshows.responses.TVShowResponse;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTVShowRepository {
    private ApiService apiService ;

    public SearchTVShowRepository() {
        this.apiService = ApiClient.getRetrofit().create(ApiService.class);
    }


    public LiveData<TVShowResponse> getSearchTVShow(String query , int page){
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.searchTVShow(query , page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call,@NonNull Response<TVShowResponse> response) {
                   data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
