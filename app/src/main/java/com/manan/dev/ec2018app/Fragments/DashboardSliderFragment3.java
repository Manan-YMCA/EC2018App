package com.manan.dev.ec2018app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.manan.dev.ec2018app.BrixxActivity;
import com.manan.dev.ec2018app.Models.CategoryItemModel;
import com.manan.dev.ec2018app.R;

import java.io.ByteArrayOutputStream;


public class DashboardSliderFragment3 extends Fragment {
    private LinearLayout circleLinearLayout;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_dashboard_slider_fragment3, container, false);

        Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(),
                R.raw.sae);
      final CategoryItemModel singleItem = new CategoryItemModel();
      singleItem.setClubName("Brixx");
      singleItem.setDisplayName("Non-Formal");
      singleItem.setImage(icon);
 Button expre=rootView.findViewById(R.id.buuton_explore_brixx);
        circleLinearLayout = rootView.findViewById(R.id.dsb_tap);

        expre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                singleItem.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
           //     Toast.makeText(mContext,singleItem.getClubName()+byteArray,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, BrixxActivity.class));
//                        .putExtra("clubname", singleItem.getClubName())
//                        .putExtra("clubPhoto", byteArray)
//                        .putExtra("clubdisplay", singleItem.getDisplayName()));

            }
        });
        return rootView;
    }

}
