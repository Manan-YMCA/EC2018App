package com.manan.dev.ec2018app.Utilities;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

/**
 * Created by naman on 2/4/2018.
 */

public class DrawerToggleBtn extends android.support.v7.widget.AppCompatImageView implements DrawerLayout.DrawerListener {

    private DrawerLayout mDrawerLayout;
    private int side = Gravity.LEFT;

    public DrawerToggleBtn(Context context) {
        super(context);
    }

    public DrawerToggleBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerToggleBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void changeState() {
        if (mDrawerLayout.isDrawerOpen(side)) {
            mDrawerLayout.closeDrawer(side);
        } else {
            mDrawerLayout.openDrawer(side);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        Log.e("BUTTOM DRAWER: ", "onDrawerSlide");
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        Log.e("BUTTOM DRAWER: ", "onDrawerOpened");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        Log.e("BUTTOM DRAWER: ", "onDrawerClosed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        Log.e("BUTTOM DRAWER: ", "onDrawerStateChanged");
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public DrawerToggleBtn setDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
        return this;
    }
}
