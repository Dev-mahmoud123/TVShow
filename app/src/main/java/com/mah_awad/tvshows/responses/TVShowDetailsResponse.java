package com.mah_awad.tvshows.responses;

import com.google.gson.annotations.SerializedName;
import com.mah_awad.tvshows.models.TVShowsDetails;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowsDetails tvShowsDetails;

    public TVShowsDetails getTvShowsDetails() {
        return tvShowsDetails;
    }
}
