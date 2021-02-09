package com.mah_awad.tvshows.listeners;

import com.mah_awad.tvshows.models.TVShow;

public interface WatchlistListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchlist(TVShow tvShow, int Position);
}
