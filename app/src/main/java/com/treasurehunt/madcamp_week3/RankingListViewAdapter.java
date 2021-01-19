package com.treasurehunt.madcamp_week3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingListViewAdapter extends BaseAdapter {

    private ArrayList<RankingListViewItem> listViewItemList  = new ArrayList<RankingListViewItem>();

    public RankingListViewAdapter(){

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rankinglistview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textRankername = (TextView) convertView.findViewById(R.id.rankername) ;
        TextView textScore = (TextView) convertView.findViewById(R.id.rankerscore);
        ImageView imageRanking = (ImageView) convertView.findViewById(R.id.rankingImage);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        RankingListViewItem listViewItem = listViewItemList.get(position);

        textRankername.setText(listViewItem.getName());
        textScore.setText(Integer.toString(listViewItem.getScore()));
        imageRanking.setImageDrawable(listViewItem.getRankingImage());
        return convertView;
    }

    public void addItem(Drawable image, String name, Integer score) {
        RankingListViewItem item = new RankingListViewItem();

        item.setRankingImage(image);
        item.setName(name);
        item.setScore(score);

        listViewItemList.add(item);
    }

}
