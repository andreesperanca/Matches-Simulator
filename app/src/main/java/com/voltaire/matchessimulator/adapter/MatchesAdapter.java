package com.voltaire.matchessimulator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.voltaire.matchessimulator.databinding.MatchItemBinding;
import com.voltaire.matchessimulator.models.Match;
import com.voltaire.matchessimulator.ui.DetailActivity;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private List<Match> matches;

    public List<Match> getMatches() {
        return matches;
    }

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(layoutInflater, parent, false);
        return new MatchesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Match match = matches.get(position);


            Glide.with(context).load(match.getHomeTeam().getUrl()).into(holder.binding.ivTeamHome);
            holder.binding.tvTeamHome.setText(String.valueOf(match.getHomeTeam().getName()));
        if (match.getHomeTeam().getScore() != null) {
            holder.binding.scoreTeamHome.setText(String.valueOf(match.getHomeTeam().getScore()));
        }

            Glide.with(context).load(match.getAwayTeam().getUrl()).into(holder.binding.ivTeamVisitant);
            holder.binding.tvTeamVisitant.setText(String.valueOf(match.getAwayTeam().getName()));
        if (match.getAwayTeam().getScore() != null) {
            holder.binding.scoreTeamVisitant.setText(String.valueOf(match.getAwayTeam().getScore()));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("MATCH", match);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class MatchesViewHolder extends RecyclerView.ViewHolder {

        private final MatchItemBinding binding;

        public MatchesViewHolder(MatchItemBinding binding) {
            super(binding.getRoot() );
            this.binding = binding;
        }
    }

}

