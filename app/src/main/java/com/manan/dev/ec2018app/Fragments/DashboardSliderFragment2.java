package com.manan.dev.ec2018app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manan.dev.ec2018app.R;

import java.util.concurrent.TimeUnit;

public class DashboardSliderFragment2 extends Fragment {
    private TextView time;
    // TODO: Rename parameter arguments, choose names that match

    private static final String FORMAT = "%02d:%02d:%02d:%02d";

    int seconds , minutes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_dashboard_slider_fragment2, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(getResources().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();




          long fest_day=1522866600;
          long curr =System.currentTimeMillis()/1000;
          long diff=fest_day-curr;
          Log.e("CurrentTime",String.valueOf(curr));

            time=rootView.findViewById(R.id.timer);

                new CountDownTimer((diff*1000), 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                time.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toDays(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)- TimeUnit.DAYS.toHours(
                                TimeUnit.MILLISECONDS.toDays(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                time.setText("Fest is Live");
            }
        }.start();






        return rootView;






    }



}



