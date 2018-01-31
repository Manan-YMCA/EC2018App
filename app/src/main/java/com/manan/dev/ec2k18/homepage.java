package com.manan.dev.ec2k18;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.manan.dev.ec2k18.R;
import com.victor.loading.newton.NewtonCradleLoading;

public class homepage extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4;
    Button bt1;
    ImageView ig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        tv1=(TextView)this.findViewById(R.id.text1);
        tv2=(TextView)this.findViewById(R.id.text2);
        tv3=(TextView)this.findViewById(R.id.text3);
        tv4=(TextView)this.findViewById(R.id.text4);
        bt1=(Button)this.findViewById(R.id.loginbutton);
        ig=(ImageView)this.findViewById(R.id.eclogo);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        bt1.setVisibility(View.GONE);
        ig.setVisibility(View.GONE);

        final NewtonCradleLoading newtonCradleLoading;
        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(R.color.white);
        ig.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.ec_logo, 250, 250));
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                tv1.setVisibility(View.VISIBLE);
                tv1.animate().alpha(1.0f).setDuration(2000);
                tv2.setVisibility(View.VISIBLE);
                tv2.animate().alpha(1.0f).setDuration(2000);
                tv3.setVisibility(View.VISIBLE);
                tv3.animate().alpha(1.0f).setDuration(2000);
                tv4.setVisibility(View.VISIBLE);
                tv4.animate().alpha(1.0f).setDuration(2000);
                bt1.setVisibility(View.VISIBLE);
                bt1.animate().alpha(1.0f).setDuration(1000);
                ig.setVisibility(View.VISIBLE);
                ig.animate().alpha(1.0f).setDuration(1000);

                newtonCradleLoading.stop();

            }
        },3000);
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
