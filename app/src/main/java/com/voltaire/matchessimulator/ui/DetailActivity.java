package com.voltaire.matchessimulator.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.voltaire.matchessimulator.databinding.ActivityDetailBinding;
import com.voltaire.matchessimulator.models.Match;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Match match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadMatchFromExtra();

    }

    private void loadMatchFromExtra() {
        match = getIntent().getExtras().getParcelable("MATCH");
        Glide.with(this)
                .load(match.getPlace().getUrl())
                .into(binding.ivPlace);
        getSupportActionBar().setTitle(match.getPlace().getName());
        binding.tvDescription.setText(match.getDescription());

        Glide.with(this)
                .load(match.getHomeTeam().getUrl())
                .into(binding.ivHomeTeam);
        binding.tvHomeTeamName.setText(match.getHomeTeam().getName());
        binding.rbHomeTeamStars.setRating(match.getHomeTeam().getStars().floatValue());

        Glide.with(this)
                .load(match.getAwayTeam().getUrl())
                .into(binding.ivAwayTeam);
        binding.tvAwayTeamName.setText(match.getAwayTeam().getName());
        binding.rbAwayTeamStars.setRating(match.getAwayTeam().getStars().floatValue());

        if (match.getAwayTeam().getScore() != null) {
            binding.tvAwayTeamScore.setText(String.valueOf(match.getAwayTeam().getScore()));
        }
        if (match.getHomeTeam().getScore() != null) {
            binding.tvHomeTeamScore.setText(String.valueOf(match.getHomeTeam().getScore()));
        }
    }
}