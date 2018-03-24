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
 * Created by Shubham on 3/22/2018.
 */

public class SpecialsFragment extends Fragment {
    private RecyclerView devrecyclerView;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public SpecialsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_creative, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_specials, container, false);

        if(mContext == null){
            mContext = getActivity();
        }


        devrecyclerView = (RecyclerView) rootView.findViewById(R.id.dev_recycler_view_f_spl);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        devrecyclerView.setLayoutManager(mLayoutManager);

        Boolean k = true;
        List<DeveloperModel> devlist = new ArrayList<>();

        DeveloperModel dev_obj1 = new DeveloperModel
                ("https://media.licdn.com/mpr/mpr/shrinknp_400_400/AAIA_wDGAAAAAQAAAAAAAAuBAAAAJGFlOTMyMTVjLTBlZTQtNGNlZi05YjE1LTdlYzc1NTZkNzgzYw.jpg", "Shubham Sharma", "", "Core App Designer", "");

        DeveloperModel dev_obj2 = new DeveloperModel
                ("http://www.elementsculmyca.com/images/team/a6.jpg", "Kartik Singla", "", "Design Support", "");

        DeveloperModel dev_obj3 = new DeveloperModel
                ("https://media.licdn.com/dms/image/C5603AQFkgj7DX14oSA/profile-displayphoto-shrink_200_200/0?e=1527058800&v=alpha&t=dy4AvqHiWMO8Ls6x2CloM8T0cPTSmLwLkSdlGC3jjFM", "Shubham Kachroo", "", "Content Writer", "");

        DeveloperModel dev_obj4 = new DeveloperModel
                ("https://scontent.fdel13-1.fna.fbcdn.net/v/t1.0-9/16387986_936688949807174_5989047283443205597_n.jpg?oh=1af0268bc94fcda344767084b27831e7&oe=5B38CD58", "Bhumika Mudgal", "", "Content Writer", "");

        DeveloperModel dev_obj5 = new DeveloperModel
                ("https://scontent.fdel13-1.fna.fbcdn.net/v/t1.0-9/16266232_217999301939790_3251840955779237127_n.jpg?oh=7466cb02836a81de3ca6f6cf64deef70&oe=5B441463", "Tanisha Tyagi", "", "Content Writer", "");

        DeveloperModel dev_obj6 = new DeveloperModel
                ("https://scontent.fdel13-1.fna.fbcdn.net/v/t1.0-9/26167985_2237398856486896_7078806577793551786_n.jpg?_nc_cat=0&oh=71df234c93cbce985a7f1b84ea5358a9&oe=5B458C61", "Prince Augustine", "", "App Video Animations", "");

        devlist.add(dev_obj1);
        devlist.add(dev_obj6);
        devlist.add(dev_obj2);
        devlist.add(dev_obj3);
        devlist.add(dev_obj4);
        devlist.add(dev_obj5);

        Log.e("TAG", "onCreateView: " + String.valueOf(devlist.size()));
        DevelopersAdapter dev_ad = new DevelopersAdapter(mContext, devlist);
        devrecyclerView.setAdapter(dev_ad);

        return rootView;
    }
}
