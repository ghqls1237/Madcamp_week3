package com.treasurehunt.madcamp_week3;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import androidx.fragment.app.FragmentPagerAdapter;

import org.json.JSONException;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public VPAdapter(FragmentManager fm) throws JSONException {
        super(fm);
        items = new ArrayList<Fragment>();
//        items.add(new Fragment1());
        items.add(new Fragment2());
        items.add(new Fragment3());

//        itext.add("HIDE");
        itext.add("SEEK");
        itext.add("MY Items");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
