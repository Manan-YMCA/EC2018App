package com.manan.dev.ec2018app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.manan.dev.ec2018app.Adapters.DevelopersAdapter;
import com.manan.dev.ec2018app.Models.DeveloperModel;
import com.manan.dev.ec2018app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreativeFragment extends Fragment {


    private RecyclerView devrecyclerView;

    public CreativeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_creative, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_creative, container, false);



        devrecyclerView = (RecyclerView) rootView.findViewById(R.id.dev_recycler_view_f3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        devrecyclerView.setLayoutManager(mLayoutManager);

        Boolean k=true;
        List<DeveloperModel> devlist=new ArrayList<>();
        DeveloperModel dev_obj = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/shubham_final.png?alt=media&token=cb8d55f1-7a2e-4256-8e11-821fd8c7ea11","Shubham Sharma","https://www.google.co.in/","Core Application Developer",k,k,k,k);
        DeveloperModel dev_obj2 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/yatin.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Yatin Dhingra","https://www.google.co.in/","Team Head",k,k,k,k);
        devlist.add(dev_obj2);
        devlist.add(dev_obj);
        devlist.add(dev_obj2);
        devlist.add(dev_obj);


        Toast.makeText(getActivity(),String.valueOf(devlist.size()),Toast.LENGTH_SHORT).show();
        DevelopersAdapter dev_ad = new DevelopersAdapter(getActivity(), devlist);
        devrecyclerView.setAdapter(dev_ad);

        return rootView;
    
    
    }

}
