package com.manan.dev.ec2018app;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static com.manan.dev.ec2018app.Utilities.BitmapHandler.decodeSampledBitmapFromResource;

public class DashboardCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_category);


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
//                Intent i = new Intent(DashboardCategoryActivity.this , SingleEventActivity.class);
//                startActivity(i);
//            }
//        });

    }
}
