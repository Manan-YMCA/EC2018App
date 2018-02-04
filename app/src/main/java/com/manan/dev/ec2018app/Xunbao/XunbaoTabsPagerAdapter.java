package com.manan.dev.ec2018app.Xunbao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by KASHISH on 03-02-2018.
 */

public class XunbaoTabsPagerAdapter extends FragmentPagerAdapter {

    public XunbaoTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AboutFragment();
            case 1:
                return new QuestionFragment();
        }
        return new LeaderboardFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
