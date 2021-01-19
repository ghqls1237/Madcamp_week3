package com.treasurehunt.madcamp_week3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("score")
    @Expose
    private Integer score;

    public Integer getScore(){
        return score;
    }
}
