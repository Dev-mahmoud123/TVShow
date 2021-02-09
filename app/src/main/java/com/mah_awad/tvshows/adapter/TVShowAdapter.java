package com.mah_awad.tvshows.adapter;

import android.view.LayoutInflater;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mah_awad.tvshows.R;
import com.mah_awad.tvshows.databinding.ItemContainerTvShowBinding;
import com.mah_awad.tvshows.listeners.TVShowListeners;
import com.mah_awad.tvshows.models.TVShow;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;
    private TVShowListeners tvShowListeners ;

    public TVShowAdapter(List<TVShow> tvShows , TVShowListeners tvShowListeners) {
        this.tvShows = tvShows;
        this.tvShowListeners = tvShowListeners;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_tv_show, parent, false
        );
        return new TVShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
       holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerTvShowBinding itemContainerTvShowBinding;

        public TVShowViewHolder(@NonNull ItemContainerTvShowBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTVShow(TVShow tvShow) {
            itemContainerTvShowBinding.setTvShow(tvShow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(view -> tvShowListeners.onTVShowClicked(tvShow));
        }
    }


}
