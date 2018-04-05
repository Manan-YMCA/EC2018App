package com.manan.dev.ec2018app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.manan.dev.ec2018app.Fragments.OprationsFragment;
import com.manan.dev.ec2018app.Fragments.DevelopesFragment_1;
import com.manan.dev.ec2018app.Fragments.SpecialsFragment;

/**
 * Created by subham on 13/03/18.
 */


public class DevelopersPagerAdapter extends FragmentStatePagerAdapter {
    private static final int TOTAL_PAGES = 3;

    public DevelopersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DevelopesFragment_1();
            case 1:
                return new OprationsFragment();
            case 2:
                return new SpecialsFragment();
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

