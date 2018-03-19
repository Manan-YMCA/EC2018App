package com.manan.dev.ec2018app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.manan.dev.ec2018app.Fragments.EcAboutFragment;
import com.manan.dev.ec2018app.Fragments.YmcaAboutFragment;

/**
 * Created by subham on 18/03/18.
 */

public class AboutAdapter extends FragmentStatePagerAdapter {

    private static final int TOTAL_PAGES = 2;

    public AboutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EcAboutFragment();
            case 1:
                return new YmcaAboutFragment();

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

