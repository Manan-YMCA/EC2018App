package com.manan.dev.ec2018app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.manan.dev.ec2018app.Fragments.RangritiFragment;
import com.manan.dev.ec2018app.Fragments.StandUpFragment;
import com.manan.dev.ec2018app.Fragments.StarNightFragment;

/**
 * Created by subham on 15/03/18.
 */


public class BrixxAdapter extends FragmentStatePagerAdapter {
    private static final int TOTAL_PAGES = 3;

    public BrixxAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RangritiFragment();
            case 1:
                return new StarNightFragment();
            case 2:
                return new StandUpFragment();

            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_PAGES;
    }

}