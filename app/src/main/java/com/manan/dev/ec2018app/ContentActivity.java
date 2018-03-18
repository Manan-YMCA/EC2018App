package com.manan.dev.ec2018app;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.manan.dev.ec2018app.Adapters.DashboardCategoryScrollerAdapter;
import com.manan.dev.ec2018app.Adapters.DashboardSlideAdapter;
import com.manan.dev.ec2018app.Models.CategoryItemModel;
import com.manan.dev.ec2018app.Utilities.ConnectivityReciever;
import com.manan.dev.ec2018app.Utilities.MyApplication;
import com.manan.dev.ec2018app.Xunbao.XunbaoActivity;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReciever.ConnectivityReceiverListener {

    private ViewPager viewPager;
    private TextView[] dots;
    private DashboardSlideAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private ArrayList<CategoryItemModel> allSampleData = new ArrayList<CategoryItemModel>();
    TextView categoriesHeadingTextView;
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private String phoneNumber;
    private LinearLayout cotainer_root_frame;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_content);
        cotainer_root_frame=(LinearLayout)findViewById(R.id.content_frame);



        try {
            Log.d("auth", FirebaseAuth.getInstance().getUid());
        } catch (Exception e) {
            Log.d("auth", e.getMessage());
        }

        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                ContentActivity.this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        nav_view.setCheckedItem(R.id.nav_home);


        categoriesHeadingTextView = findViewById(R.id.text_viewcategories);
        viewPager = (ViewPager) findViewById(R.id.slliderview_pager);
        myViewPagerAdapter = new DashboardSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        ImageView img = findViewById(R.id.drawerTogglebtn);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        addBottomDots(0);

        addData();
        RecyclerView categoryRecycleview = (RecyclerView) findViewById(R.id.category_recycler_view);

        categoryRecycleview.setHasFixedSize(true);

        DashboardCategoryScrollerAdapter adapter = new DashboardCategoryScrollerAdapter(ContentActivity.this, allSampleData);

        categoryRecycleview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecycleview.setAdapter(adapter);

    }

    private void addData() {

        CategoryItemModel manan = new CategoryItemModel();
        manan.setClubName("Manan");
        manan.setDisplayName("Coding");
        manan.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.manan));
        allSampleData.add(manan);

        CategoryItemModel ananya = new CategoryItemModel();
        ananya.setClubName("Ananya");
        ananya.setDisplayName("Literature");
        ananya.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.ananya));
        allSampleData.add(ananya);

        CategoryItemModel vividha = new CategoryItemModel();
        vividha.setClubName("Vividha");
        vividha.setDisplayName("Drama");
        vividha.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.vividha));
        allSampleData.add(vividha);

        CategoryItemModel jhalak = new CategoryItemModel();
        jhalak.setClubName("Jhalak");
        jhalak.setDisplayName("Photography & Designing");
        jhalak.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.jhalak));
        allSampleData.add(jhalak);

        CategoryItemModel eklavya = new CategoryItemModel();
        eklavya.setClubName("Eklavya");
        eklavya.setDisplayName("Fun Events");
        eklavya.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.eklavya));
        allSampleData.add(eklavya);

        CategoryItemModel ieee = new CategoryItemModel();
        ieee.setClubName("IEEE");
        ieee.setDisplayName("Techno Fun");
        ieee.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.ieee));
        allSampleData.add(ieee);

        CategoryItemModel mechnext = new CategoryItemModel();
        mechnext.setClubName("Mechnext");
        mechnext.setDisplayName("Mechanical");
        mechnext.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.mechnext));
        allSampleData.add(mechnext);

        CategoryItemModel microbird = new CategoryItemModel();
        microbird.setClubName("Microbird");
        microbird.setDisplayName("Electronics");
        microbird.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.micobird));
        allSampleData.add(microbird);

        CategoryItemModel natraja = new CategoryItemModel();
        natraja.setClubName("Nataraja");
        natraja.setDisplayName("Dance");
        natraja.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.natraja));
        allSampleData.add(natraja);

        CategoryItemModel sae = new CategoryItemModel();
        sae.setClubName("SAE/BAJA");
        sae.setDisplayName("Automobiles");
        sae.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.sae));
        allSampleData.add(sae);

        CategoryItemModel samarpan = new CategoryItemModel();
        samarpan.setClubName("Samarpan");
        samarpan.setDisplayName("Electrical");
        samarpan.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.samarpan));
        allSampleData.add(samarpan);

        CategoryItemModel srijan = new CategoryItemModel();
        srijan.setClubName("Srijan");
        srijan.setDisplayName("Arts");
        srijan.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.srijan));
        allSampleData.add(srijan);

        CategoryItemModel tarannum = new CategoryItemModel();
        tarannum.setClubName("Taranuum");
        tarannum.setDisplayName("Music");
        tarannum.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.tarannum));
        allSampleData.add(tarannum);

        CategoryItemModel vivekanand = new CategoryItemModel();
        vivekanand.setClubName("Vivekanand Manch");
        vivekanand.setDisplayName("Socio-cultural");
        vivekanand.setImage(BitmapFactory.decodeResource(ContentActivity.this.getResources(), R.raw.vivekanand));
        allSampleData.add(vivekanand);
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
        //super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(GravityCompat.START, true);
        } else {
            startActivity(new Intent(ContentActivity.this, UserLoginActivity.class)
                    .putExtra("closeApp", true)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(false);

        drawer.closeDrawer(GravityCompat.START);

        Handler handler = new Handler();

        switch (id) {
            case R.id.nav_home:
                //handle home case
                break;
            case R.id.nav_profile:

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
                        String restoredText = prefs.getString("Phone", null);
                        if (restoredText == null) {
                            startActivity(new Intent(getApplicationContext(), RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    .putExtra("parent", "normal"));
                        } else {
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                }, 130);

                break;
            case R.id.nav_tickets:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(prefs.getString("Phone", null) != null) {
                            startActivity(new Intent(ContentActivity.this, Tickets.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(ContentActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(ContentActivity.this);
                            }
                            builder.setTitle("Log In")
                                    .setMessage("To view your tickets you must Log In first.")
                                    .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(ContentActivity.this, LoginActivity.class)
                                                    .putExtra("parent", "ct"));
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            nav_view.setCheckedItem(R.id.nav_home);
                                            dialog.dismiss();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    }
                }, 130);
                break;
            case R.id.nav_xunbao:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ContentActivity.this, XunbaoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, 130);
                break;
            case R.id.nav_culmyca:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ContentActivity.this, CulmycaTimesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, 130);
                break;
            case R.id.nav_about:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ContentActivity.this, AboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, 130);
                break;
            case R.id.nav_logout:
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                if (AccessToken.getCurrentAccessToken() != null) {


                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                            .Callback() {
                        @Override
                        public void onCompleted(GraphResponse graphResponse) {

                            LoginManager.getInstance().logOut();
                            Toast.makeText(getApplicationContext(), "fb logout ho gya", Toast.LENGTH_SHORT).show();

                        }
                    }).executeAsync();
                }
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class)
                        .putExtra("logout", true)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.nav_sponsors:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ContentActivity.this, SponsorsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, 130);
                break;
            case R.id.nav_share:
                String msg = "Install the elements culmyca app to stay updated about the latest events. Follow the link: ";
                shareTextMessage(msg);
                break;
            case R.id.nav_bug:
                String to = "manantechnosurge@gmail.com";
                String subject = "Bug Found";
                String messg = "I found a bug!\n";
                sendEmailBug(to, subject, messg);
                break;
            case R.id.nav_dev:
                //TODO
                startActivity(new Intent(ContentActivity.this, DevelopersActivity.class));
                //show developers
                break;
            case R.id.nav_location:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ContentActivity.this, MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, 130);
                break;
        }
        return true;
    }

    private void shareTextMessage(String msg) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(i);
    }

    private void sendEmailBug(String to, String subject, String msg) {

        Uri uri = Uri.parse("mailto:")
                .buildUpon()
                .appendQueryParameter("subject", subject)
                .appendQueryParameter("body", msg)
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nav_view != null) {
            nav_view.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(ContentActivity.this);


        //SharedPreferences prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);
        prefs = getSharedPreferences(getResources().getString(R.string.sharedPrefName), MODE_PRIVATE);

        phoneNumber = prefs.getString("Phone", null);
        if (phoneNumber == null) {
            Menu menu = nav_view.getMenu();
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_profile).setTitle("Log In");
        } else {
            Menu menu = nav_view.getMenu();
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_profile).setTitle("Profile");
        }
        if (nav_view != null) {
            nav_view.setCheckedItem(R.id.nav_home);
        }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


        private void showSnack(boolean isConnected) {
            String message;
            int color;
            if (isConnected) {

                message = "Connected";
                Snackbar.make(cotainer_root_frame,message,Snackbar.LENGTH_SHORT).show();
                color = Color.WHITE;
            } else {
                message = "Get a hotspot Buddy";
                Snackbar.make(cotainer_root_frame,message,Snackbar.LENGTH_SHORT).show();
                color = Color.RED;
            }
    }


}
