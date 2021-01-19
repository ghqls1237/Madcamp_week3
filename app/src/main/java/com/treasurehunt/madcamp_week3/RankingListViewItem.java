package com.treasurehunt.madcamp_week3;

import android.graphics.drawable.Drawable;

public class RankingListViewItem {
    private String name;
    private Integer score;
    private Drawable rankingImage;

    public void setRankingImage(Drawable icon){
        rankingImage = icon;
    }
    public void setName(String name2){
        name = name2;
    }

    public void setScore(Integer score2){
        score = score2;
    }

    public String getName() {
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public Drawable getRankingImage(){
        return this.rankingImage;
    }
}


