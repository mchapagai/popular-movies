package com.example.mchapagai.model;

import android.support.annotation.NonNull;

public enum Sort {

    MOST_POPULAR("popularity.desc"),
    RELEASE_DATE("release_date.desc"),
    TOP_RATED("vote_average.desc"),
    MOST_RATED("vote_count.desc");

    private String value;

    Sort(String sort) {
        value = sort;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}