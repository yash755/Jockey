package com.gappydevelopers.xsarcasm.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gappydevelopers.xsarcasm.fragment.GalleryFragment;
import com.gappydevelopers.xsarcasm.fragment.FavouriteFragment;
import com.gappydevelopers.xsarcasm.fragment.AboutFragment;

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
                return GalleryFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FavouriteFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return AboutFragment.newInstance(2, "Page # 3");
            default:
                return null;
        }
    }


}