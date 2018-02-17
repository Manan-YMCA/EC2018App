package com.manan.dev.ec2018app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CategoryEventDisplayActivity extends AppCompatActivity {

    private String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_event_display);

        clubName = getIntent().getStringExtra("clubname");
        Toast.makeText(this, clubName, Toast.LENGTH_SHORT).show();

        ImageView iv = (ImageView) findViewById(R.id.ec_logo);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryEventDisplayActivity.this, SingleEventActivity.class);
                startActivity(i);
            }
        });

//  Rounded Transformation Image!!
//        Picasso.with(mContext)
//                .load(com.app.utility.Constants.BASE_URL+b.image)
//                .placeholder(R.drawable.profile)
//                .error(R.drawable.profile)
//                .transform(new RoundedTransformation(50, 4))
//                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
//                .centerCrop()
//                .into(v.im_user);
//
    }
}
