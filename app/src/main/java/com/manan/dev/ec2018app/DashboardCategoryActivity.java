package com.manan.dev.ec2018app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DashboardCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_category);

        TextView tvHelloWorld = findViewById(R.id.tv_hello_world);

        tvHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardCategoryActivity.this , SingleEventActivity.class);
                startActivity(i);
            }
        });

    }
}
