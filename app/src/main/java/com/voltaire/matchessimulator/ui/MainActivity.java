package com.voltaire.matchessimulator.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.load.resource.gif.StreamGifDecoder;
import com.google.android.material.snackbar.Snackbar;
import com.voltaire.matchessimulator.R;
import com.voltaire.matchessimulator.adapter.MatchesAdapter;
import com.voltaire.matchessimulator.data.MatchesAPI;
import com.voltaire.matchessimulator.databinding.ActivityDetailBinding;
import com.voltaire.matchessimulator.databinding.ActivityMainBinding;
import com.voltaire.matchessimulator.models.Match;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesAPI matchesAPI;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();
    }

    private void setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener(view -> {
            view.animate().rotationBy(360).setDuration(500);
                    Random random = new Random();

                    for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                        Match match = matchesAdapter.getMatches().get(i);
                        match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                        match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                        matchesAdapter.notifyItemChanged(i);
                    }
            });
    }

    private void setupMatchesRefresh() {
        binding.srMatches.setOnRefreshListener(this::findMatchesAPI);
    }

    private void setupMatchesList() {
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));
        findMatchesAPI();
    }

    private void findMatchesAPI() {
        binding.srMatches.setRefreshing(true);
        matchesAPI.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    List<Match> matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches);
                     binding.rvMatches.setAdapter(matchesAdapter);
                } else {
                    showERRORMessage();
                }
                binding.srMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showERRORMessage();
                binding.srMatches.setRefreshing(true);
            }
        });
    }

    private void showERRORMessage() {
        Snackbar.make(binding.fabSimulate, R.string.errorAPI, Snackbar.LENGTH_LONG).show();
    }

    private void setupHttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://andreesperanca.github.io/matches-simulator-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matchesAPI = retrofit.create(MatchesAPI.class);
    }
}