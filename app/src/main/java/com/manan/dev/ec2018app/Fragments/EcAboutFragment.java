package com.manan.dev.ec2018app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manan.dev.ec2018app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EcAboutFragment extends Fragment {

    public EcAboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ec_about, container, false);
    }

}
