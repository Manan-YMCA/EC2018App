package com.manan.dev.ec2018app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manan.dev.ec2018app.Adapters.DevelopersAdapter;
import com.manan.dev.ec2018app.Models.DeveloperModel;
import com.manan.dev.ec2018app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DesignFragment extends Fragment {

    private RecyclerView devrecyclerView;

    public DesignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_design, container, false);


        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_design, container, false);



        devrecyclerView = (RecyclerView) rootView.findViewById(R.id.dev_recycler_view_f2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        devrecyclerView.setLayoutManager(mLayoutManager);

        Boolean k=true;
        List<DeveloperModel> devlist=new ArrayList<>();
        DeveloperModel dev_obj1 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/augustine_final.png?alt=media&token=cb8d55f1-7a2e-4256-8e11-821fd8c7ea11","Augustine","https://www.google.co.in/","Core Application Developer",k,k,k,k);
        DeveloperModel dev_obj2 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/kartik.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Kartik Singla","https://www.google.co.in/","Team Head",k,k,k,k);
        DeveloperModel dev_obj3 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/tanisha.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Tanisha","https://www.google.co.in/","Team Head",k,k,k,k);
        DeveloperModel dev_obj4 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/shubham.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Shubham Sharma","https://www.google.co.in/","Team Head",k,k,k,k);

        devlist.add(dev_obj1);
        devlist.add(dev_obj2);
        devlist.add(dev_obj3);
        devlist.add(dev_obj4);

        Log.e("TAG", "onCreateView: " + String.valueOf(devlist.size()) );
        DevelopersAdapter dev_ad = new DevelopersAdapter(getActivity(), devlist);
        devrecyclerView.setAdapter(dev_ad);

        return rootView;

    }

}
