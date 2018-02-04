package com.manan.dev.ec2018app;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.manan.dev.ec2018app.Adapters.DashboardCategoryScrollerAdapter;
import com.manan.dev.ec2018app.Adapters.DashboardSlideAdapter;
import com.manan.dev.ec2018app.Fragments.DashboardCategoryFragment;
import com.manan.dev.ec2018app.Models.CategoryItemModel;

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
    private ImageView drawerToggleBtn;
    private List<SlideMenuItem> list = new ArrayList<>();
    private DashboardCategoryFragment dashboardCategoryFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    private ViewPager mPager;
    private ViewPager viewPager;
    private TextView[] dots;

    private DashboardSlideAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private ArrayList<CategoryItemModel> allSampleData = new ArrayList<CategoryItemModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        viewPager = (ViewPager) findViewById(R.id.slliderview_pager);
        myViewPagerAdapter = new DashboardSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
TextView developersnav = findViewById(R.id.developers);
developersnav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(ContentActivity.this,DevelopersActivity.class));
    }
});



        addBottomDots(0);

       addData();

        RecyclerView categoryRecycleview = (RecyclerView) findViewById(R.id.category_recycler_view);

        categoryRecycleview.setHasFixedSize(true);

        DashboardCategoryScrollerAdapter adapter = new DashboardCategoryScrollerAdapter(ContentActivity.this, allSampleData);

        categoryRecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecycleview.setAdapter(adapter);




        dashboardCategoryFragment = DashboardCategoryFragment.newInstance();   //Default Set for Dashboard
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, dashboardCategoryFragment)
                .commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggleBtn = findViewById(R.id.drawerTogglebtn);
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

        drawerToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    private void addData() {
        CategoryItemModel item1 = new CategoryItemModel();
        item1.setName("Dance");
        item1.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(),
                R.drawable.ek));

        allSampleData.add(item1);

        CategoryItemModel item3 = new CategoryItemModel();
        item3.setName("Photography");
        item3.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(),
                R.drawable.jh));
        allSampleData.add(item3);
        CategoryItemModel item4 = new CategoryItemModel();
        item4.setName("Debate");
        item4.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(),
                R.drawable.dk));
        allSampleData.add(item4);

        CategoryItemModel item5 = new CategoryItemModel();
        item5.setName("Music");
        item5.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(),
                R.drawable.ek));
        allSampleData.add(item5);
        CategoryItemModel item6 = new CategoryItemModel();
        item6.setName("Love");

        item6.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(),
                R.drawable.an));
        allSampleData.add(item6);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[3];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }



    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }


    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(PROFILE, R.drawable.icn_ec);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(TRENDING, R.drawable.icn_close);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(MYTICKETS, R.drawable.icn_ec);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(SPONSORS, R.drawable.icn_close);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ABOUT, R.drawable.icn_ec);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(DEVELOPERS, R.drawable.icn_close);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(DEVELOPERS, R.drawable.icn_ec);
        list.add(menuItem7);
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
