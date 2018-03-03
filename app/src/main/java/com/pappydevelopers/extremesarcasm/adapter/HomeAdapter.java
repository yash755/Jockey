package com.pappydevelopers.extremesarcasm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pappydevelopers.extremesarcasm.fragment.FirstFragment;
import com.pappydevelopers.extremesarcasm.fragment.SecondFragment;
import com.pappydevelopers.extremesarcasm.fragment.ThirdFragment;

public class HomeAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;



    public HomeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FirstFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SecondFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return ThirdFragment.newInstance(2, "Page # 3");
            default:
                return null;
        }
    }


}