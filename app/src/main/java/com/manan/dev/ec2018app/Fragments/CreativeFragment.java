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
        DeveloperModel dev_obj1 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/sourabh_final.png?alt=media&token=cb8d55f1-7a2e-4256-8e11-821fd8c7ea11","Sourabh","https://www.google.co.in/","Marketing",k,k,k,k);
        DeveloperModel dev_obj2 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/anuj.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Anuj Raj Sharma","https://www.google.co.in/","Marketing",k,k,k,k);
        DeveloperModel dev_obj3 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/sanjog.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Sanjog Garv","https://www.google.co.in/","Marketing",k,k,k,k);
        DeveloperModel dev_obj4 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/sushant.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Sushant Sharma","https://www.google.co.in/","Marketing",k,k,k,k);
        DeveloperModel dev_obj5 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/randeep.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Randeep","https://www.google.co.in/","Marketing",k,k,k,k);
        DeveloperModel dev_obj6 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/nishant.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Nishant","https://www.google.co.in/","Logistics",k,k,k,k);
        DeveloperModel dev_obj7 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/devd.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Davinderpreet Bedi","https://www.google.co.in/","Logistics",k,k,k,k);
        DeveloperModel dev_obj8 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/kushal.png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Kushal Sharma","https://www.google.co.in/","Logistics",k,k,k,k);
        DeveloperModel dev_obj9 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/sushantg_png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Karan Handa","https://www.google.co.in/","Logistics",k,k,k,k);
        DeveloperModel dev_obj10 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/payal_png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Sushant Gawri","https://www.google.co.in/","Logistics",k,k,k,k);
        DeveloperModel dev_obj11 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/karan_png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Payal","https://www.google.co.in/","Hospitality",k,k,k,k);
        DeveloperModel dev_obj12 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/karan_png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Kshitiz Manocha","https://www.google.co.in/","Hospitality",k,k,k,k);

        DeveloperModel dev_obj13 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/sahil_png?alt=media&token=cbdd7d6f-f9d2-43aa-82d9-5ee2295e741a","Sahil Kasana","https://www.google.co.in/","Seurity",k,k,k,k);

        devlist.add(dev_obj2);
        devlist.add(dev_obj1);
        devlist.add(dev_obj3);
        devlist.add(dev_obj4);
        devlist.add(dev_obj5);
        devlist.add(dev_obj6);
        devlist.add(dev_obj7);
        devlist.add(dev_obj8);
        devlist.add(dev_obj9);
        devlist.add(dev_obj10);
        devlist.add(dev_obj11);
        devlist.add(dev_obj12);
        devlist.add(dev_obj13);



        Toast.makeText(getActivity(),String.valueOf(devlist.size()),Toast.LENGTH_SHORT).show();
        DevelopersAdapter dev_ad = new DevelopersAdapter(getActivity(), devlist);
        devrecyclerView.setAdapter(dev_ad);

        return rootView;
    
    
    }

}
