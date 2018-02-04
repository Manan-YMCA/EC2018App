package com.manan.dev.ec2018app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.manan.dev.ec2018app.Fragments.DashboardSliderFragment1;
import com.manan.dev.ec2018app.Fragments.DashboardSliderFragment2;
import com.manan.dev.ec2018app.Fragments.DashboardSliderFragment3;

/**
 * Created by shubhamsharma on 04/02/18.
 */

public class DashboardSlideAdapter extends FragmentStatePagerAdapter {
    private static final int TOTAL_PAGES = 3;

    public DashboardSlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DashboardSliderFragment1();
            case 1:
                return new DashboardSliderFragment2();
            case 2:
                return new DashboardSliderFragment3();

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

