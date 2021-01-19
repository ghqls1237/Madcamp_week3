package com.treasurehunt.madcamp_week3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList  = new ArrayList<ListViewItem>();

    public ListViewAdapter(){

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
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView textTime = (TextView) convertView.findViewById(R.id.time) ;
        TextView textLat = (TextView) convertView.findViewById(R.id.latitude);
        TextView textLong = (TextView) convertView.findViewById(R.id.longitude);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

//        // 아이템 내 각 위젯에 데이터 반영
//        iconImageView.setImageDrawable(listViewItem.getIcon());
        textLat.setText(listViewItem.getLatitude().toString());
        textLong.setText(listViewItem.getLongitude().toString());
        textTime.setText(listViewItem.getTime());

        return convertView;
    }

    public void addItem(String time, String latitude, String longitude) {
        ListViewItem item = new ListViewItem();

        item.setTime(time);
        item.setLatitude(latitude);
        item.setLongitude(longitude);

        listViewItemList.add(item);
    }
}
