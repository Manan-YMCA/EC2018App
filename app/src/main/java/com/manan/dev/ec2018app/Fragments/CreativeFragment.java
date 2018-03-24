package com.manan.dev.ec2018app.Fragments;


import android.content.Context;
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
public class CreativeFragment extends Fragment {


    private RecyclerView devrecyclerView;
    private Context mContext;

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

        if(mContext == null){
            mContext = getActivity();
        }


        devrecyclerView = (RecyclerView) rootView.findViewById(R.id.dev_recycler_view_f3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        devrecyclerView.setLayoutManager(mLayoutManager);

        Boolean k = true;
        List<DeveloperModel> devlist = new ArrayList<>();
        DeveloperModel dev_obj1 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a12.jpg", "Sourabh", "", "Marketing", "");

        DeveloperModel dev_obj2 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a1.jpg", "Anuj Raj Sharma", "", "Marketing", "");

        DeveloperModel dev_obj3 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a11.jpg", "Sanjog Garv", "", "Marketing", "");

        DeveloperModel dev_obj4 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a13.jpg", "Sushant Sharma", "", "Marketing", "");

        DeveloperModel dev_obj5 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a9.jpg", "Randeep", "", "Marketing", "");

        DeveloperModel dev_obj6 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a8.jpg", "Nishant", "", "Logistics", "");

        DeveloperModel dev_obj7 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a4.jpg", "Davinderpreet Bedi", "", "Logistics", "");

        DeveloperModel dev_obj8 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a7.jpg", "Kushal Sharma", "", "Logistics", "");

        DeveloperModel dev_obj9 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a5.jpg", "Karan Handa", "", "Logistics", "");

        DeveloperModel dev_obj10 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a14.jpg", "Sushant Gawri", "", "Logistics", "");

        DeveloperModel dev_obj11 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a3.jpg", "Payal", "", "Hospitality", "");

        DeveloperModel dev_obj12 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a2.jpg", "Kshitiz Manocha", "", "Hospitality", "");

        DeveloperModel dev_obj13 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a10.jpg", "Sahil Kasana", "", "Seurity", "");

        DeveloperModel dev_obj14 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a6.jpg", "Kartik Singla", "", "Designer", "");

        devlist.add(dev_obj14);
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

        Log.e("TAG", "onCreateView: " + String.valueOf(devlist.size()));
        DevelopersAdapter dev_ad = new DevelopersAdapter(mContext, devlist);
        devrecyclerView.setAdapter(dev_ad);

        return rootView;


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
