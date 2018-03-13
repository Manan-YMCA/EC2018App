package com.manan.dev.ec2018app.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.manan.dev.ec2018app.Fragments.CreativeFragment;
import com.manan.dev.ec2018app.Fragments.DesignFragment;
import com.manan.dev.ec2018app.Fragments.DevelopesFragment_1;
import com.manan.dev.ec2018app.Fragments.OperationalFragment;

/**
 * Created by subham on 13/03/18.
 */


    public class DevelopersPagerAdapter extends FragmentStatePagerAdapter {
        private static final int TOTAL_PAGES = 4;

        public DevelopersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DevelopesFragment_1();
                case 1:
                    return new DesignFragment();
                case 2:
                    return new CreativeFragment();
                case 3:
                    return new OperationalFragment();

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

