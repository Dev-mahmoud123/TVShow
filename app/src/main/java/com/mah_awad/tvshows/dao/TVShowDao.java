package com.mah_awad.tvshows.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mah_awad.tvshows.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvShow")
    Flowable<List<TVShow>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList (TVShow tvShow);

    @Delete
    Completable removeFromWatchList(TVShow tvShow);

    @Query("SELECT * FROM tvShow WHERE id  = :tvShowId")
    Flowable<TVShow> getTVShowFromWatchlist(String tvShowId);
}
