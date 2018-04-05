package com.manan.dev.ec2018app.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class OprationsFragment extends Fragment {


    private RecyclerView devrecyclerView;
    private Context mContext;

    public OprationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_creative, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_creative, container, false);

        if (mContext == null) {
            mContext = getActivity();
        }


        devrecyclerView = (RecyclerView) rootView.findViewById(R.id.dev_recycler_view_f3);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        devrecyclerView.setLayoutManager(mLayoutManager);

        Boolean k = true;
        List<DeveloperModel> devlist = new ArrayList<>();

        DeveloperModel kartik = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a6.jpg", "Kartik Singla", "", "Designer", "");
        DeveloperModel saurabh = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a12.jpg", "Sourabh", "", "Marketing", "");
        DeveloperModel anuj = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a1.jpg", "Anuj Raj Sharma", "", "Marketing", "");
        DeveloperModel sanjog = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a11.jpg", "Sanjog Garva", "", "Marketing", "");
        DeveloperModel sushant = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a13.jpg", "Sushant", "", "Marketing", "");
        DeveloperModel randeep = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a9.jpg", "Randeep", "", "Logistics", "");
        DeveloperModel nishant = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a8.jpg", "Nishant Chetiwal", "", "Logistics", "");
        DeveloperModel bedi = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a4.jpg", "Davinderpreet Bedi", "", "Logistics", "");
        DeveloperModel alok = new DeveloperModel
                ("https://manan-ymca.github.io/ElementsCulmyca2018Website/images/team/a15.jpg", "Alok Tyagi", "", "Logistics", "");
        DeveloperModel shubham = new DeveloperModel
                ("https://manan-ymca.github.io/ElementsCulmyca2018Website/images/team/a16.jpg", "Shubham Saini", "", "Logistics", "");
        DeveloperModel payal = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a3.jpg", "Payal Malik", "", "Hospitality", "");
        DeveloperModel kshitiz = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a2.jpg", "Kshitiz Minocha", "", "Hospitality", "");
        DeveloperModel sahil = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a10.jpg", "Sahil Kasana", "", "Security", "");

        devlist.add(kartik);
        devlist.add(saurabh);
        devlist.add(anuj);
        devlist.add(sanjog);
        devlist.add(sushant);
        devlist.add(randeep);
        devlist.add(nishant);
        devlist.add(bedi);
        devlist.add(alok);
        devlist.add(shubham);
        devlist.add(payal);
        devlist.add(kshitiz);
        devlist.add(sahil);

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
