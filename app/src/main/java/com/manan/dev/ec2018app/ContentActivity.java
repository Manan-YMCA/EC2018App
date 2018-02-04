package com.manan.dev.ec2018app;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.manan.dev.ec2018app.Fragments.DashboardCategoryFragment;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class ContentActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

    public static final String CLOSE = "Close";
    public static final String PROFILE = "Profile";
    public static final String TRENDING = "Trending Events";
    public static final String MYTICKETS = "My Tickets";
    public static final String SPONSORS = "Sponsors";
    public static final String ABOUT = "About";
    public static final String DEVELOPERS = "Developers";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private DashboardCategoryFragment dashboardCategoryFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


//        View myView = findViewById(R.id.jhalak);
//
//// get the center for the clipping circle
//        int cx = myView.getWidth() / 2;
//        int cy = myView.getHeight() / 2;
//
//// get the final radius for the clipping circle
//        float finalRadius = (float) Math.hypot(cx, cy);
//
//// create the animator for this view (the start radius is zero)
//        Animator anim =
//                null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
//        }
//
//// make the view visible and start the animation
//        myView.setVisibility(View.VISIBLE);
//        anim.start();


//ImageView imageViewdrama= (ImageView) findViewById(R.id.but1);

//        imageViewdrama.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.drawable.an, 92, 92));
//        TextView tvHelloWorld = findViewById(R.id.tv_hello_world);
//
//        tvHelloWorld.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ContentActivity.this , SingleEventActivity.class);
//                startActivity(i);
//            }
//        });
        dashboardCategoryFragment = DashboardCategoryFragment.newInstance();   //Default Set for Dashboard
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, dashboardCategoryFragment)
                .commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        Log.d("hey", list.toString());
        Log.d("hey", list.get(0).getName());
        viewAnimator = new ViewAnimator<>(this, list, dashboardCategoryFragment, drawerLayout, this);

    }


    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(PROFILE, R.drawable.logo_300);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(TRENDING, R.drawable.an);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MYTICKETS, R.drawable.ek);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(SPONSORS, R.drawable.icn_close);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ABOUT, R.drawable.icn_close);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(DEVELOPERS, R.drawable.icn_close);
        list.add(menuItem6);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.open,
                R.string.close
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case CLOSE:
                return screenShotable;
            default:
                return replaceFragment(screenShotable, position, slideMenuItem.getName());
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, String name) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        // dummy intents
        switch (name) {
            case PROFILE: {
//                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
//                return contentFragment;
                startActivity(new Intent(ContentActivity.this, CategoryEventDisplayActivity.class));
            }
            case TRENDING: {
//                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
//                return contentFragment;
//                startActivity(new Intent(ContentActivity.this, XunbaoActivity.class));
            }
            case MYTICKETS: {
                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                return contentFragment;
            }
            case SPONSORS: {
                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                return contentFragment;
            }
            case ABOUT: {
                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                return contentFragment;
            }
            case DEVELOPERS: {
                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                return contentFragment;
            }
            default:
                DashboardCategoryFragment contentFragment = DashboardCategoryFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                return contentFragment;
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
