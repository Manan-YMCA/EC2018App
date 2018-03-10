package com.manan.dev.ec2018app.Xunbao;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manan.dev.ec2018app.R;


public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ProgressDialog progressBar;
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Loading!");
        progressBar.setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

}
