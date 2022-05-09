package com.voltaire.matchessimulator.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Match implements Parcelable {

    @SerializedName("descricao")
    private String description;
    @SerializedName("local")
    private Place place;
    @SerializedName("mandante")
    private Team homeTeam;
    @SerializedName("visitante")
    private Team awayTeam;

    protected Match(Parcel in) {
        description = in.readString();
        place = in.readParcelable(Place.class.getClassLoader());
        homeTeam = in.readParcelable(Team.class.getClassLoader());
        awayTeam = in.readParcelable(Team.class.getClassLoader());
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeParcelable(place, i);
        parcel.writeParcelable(homeTeam, i);
        parcel.writeParcelable(awayTeam, i);
    }
}
