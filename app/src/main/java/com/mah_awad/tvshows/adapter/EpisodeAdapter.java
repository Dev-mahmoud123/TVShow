package com.mah_awad.tvshows.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mah_awad.tvshows.R;
import com.mah_awad.tvshows.databinding.ItemContainerEpisodeBinding;
import com.mah_awad.tvshows.models.Episode;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    public EpisodeAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodeBinding episodeBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container_episode, parent, false
        );
        return new EpisodeViewHolder(episodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {

        private ItemContainerEpisodeBinding itemContainerEpisodeBinding;

        public EpisodeViewHolder(@NonNull ItemContainerEpisodeBinding itemContainerEpisodeBinding) {
            super(itemContainerEpisodeBinding.getRoot());

            this.itemContainerEpisodeBinding = itemContainerEpisodeBinding;
        }

        public void bindEpisode(Episode episode) {
            String title = "s";
            String season = episode.getSeason();
            if (season.length() == 1) {
                season = "0".concat(season);
            }
            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            itemContainerEpisodeBinding.setTitle(title);
            itemContainerEpisodeBinding.setName(episode.getName());
            itemContainerEpisodeBinding.setAirDate(episode.getAirDate());
        }
    }
}
