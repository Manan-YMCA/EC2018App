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
public class DevelopesFragment_1 extends Fragment {
    private RecyclerView devrecyclerView;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public DevelopesFragment_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_developers_1, container, false);

        if(mContext == null){
            mContext = getActivity();
        }

        devrecyclerView = (RecyclerView) rootView.findViewById(R.id.dev_recycler_view_f1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        devrecyclerView.setLayoutManager(mLayoutManager);

        Boolean k = true;
        List<DeveloperModel> devlist = new ArrayList<>();

        DeveloperModel dev_obj1 = new DeveloperModel
                ("https://media.licdn.com/mpr/mpr/AAIAAQDGAAAAAQAAAAAAAAnYAAAAJDAzMTUyY2Q4LTEwYjAtNDNlMi05ODFjLTI1MzZmYjIyNDczMg.jpg", "Yatin Dhingra", "https://linkedin.com/in/yatin-dhingra-58635a128", "App Team Head", "https://github.com/yatind01");

        DeveloperModel dev_obj2 = new DeveloperModel
                ("https://media.licdn.com/dms/image/C5603AQEF5xNAR6L4lA/profile-displayphoto-shrink_800_800/0?e=1527966000&v=alpha&t=p7cNpJ0YcD-MBNn5GsCYkWGUkxPXJhti_AxsPWbU5Go", "Shubham Sharma", "https://linkedin.com/in/shubham-sharma-ba185614b", "Core App Developer", "https://github.com/shubham0008");

        DeveloperModel dev_obj3 = new DeveloperModel
                ("https://media.licdn.com/dms/image/C5603AQFkgj7DX14oSA/profile-displayphoto-shrink_200_200/0?e=1527058800&v=alpha&t=dy4AvqHiWMO8Ls6x2CloM8T0cPTSmLwLkSdlGC3jjFM", "Shubham Kachroo", "https://www.linkedin.com/in/alchemister", "Core App Developer", "https://github.com/the-Alchemister");

        DeveloperModel dev_obj4 = new DeveloperModel
                ("https://avatars2.githubusercontent.com/u/23135250", "Kashish Gupta", "https://linkedin.com/in/kashish-gupta-19562b144", "Core App Developer", "https://github.com/gkashish");

        DeveloperModel dev_obj5 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/manan-club.appspot.com/o/dp%2FSI_20180128_140843.jpg?alt=media&token=618055f8-7449-4056-a02d-72aa6c4b315d", "Prerna Suneja", "https://linkedin.com/in/prerna-suneja-96b97714b", "App Developer", "https://github.com/Prerna1");

        DeveloperModel dev_obj6 = new DeveloperModel
                ("https://scontent.fdel13-1.fna.fbcdn.net/v/t1.0-9/17458297_1230560560393523_6408638150449422745_n.jpg?oh=5d5adaf747f15955a7931796bacca52e&oe=5B373F36", "Jayati Bhayana", "https://linkedin.com/in/jayati-bhayana-503484128", "App Developer", "https://github.com/jayati2016");

        DeveloperModel dev_obj7 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/manan-club.appspot.com/o/dp%2FIMG_20170918_225148_472.jpg?alt=media&token=72a28b92-c94c-40ee-a131-23cf98571d51", "Neha Yadav", "https://linkedin.com/in/neha-yadav-336370152", "App Developer", "https://github.com/NehaYadav113");

        DeveloperModel dev_obj8 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/manan-club.appspot.com/o/dp%2Fpp.jpg?alt=media&token=2725435d-622f-4f72-8988-ea5b23d8a395", "Naman Sachdeva", "https://linkedin.com/in/namansachdeva", "Team Coordinator", "https://github.com/namansachdeva");

        DeveloperModel dev_obj9 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/manan-club.appspot.com/o/dp%2FIMG_20160913_122252.jpg?alt=media&token=d1f41a74-af60-400b-8f1a-61cf74dfb9d7", "Shubham Goel", "https://linkedin.com/in/shubham-goel-0a2972141", "Admin App Developer", "https://github.com/Shubhamgoel123");

        DeveloperModel dev_obj10 = new DeveloperModel
                ("https://scontent.fdel13-1.fna.fbcdn.net/v/t31.0-8/17097542_1767506106898555_8444256737289112828_o.jpg?oh=2287d64a0ec2c2f722429ea6014697e5&oe=5B3CAB2A", "Hemant Bansal", "https://linkedin.com/in/hemantbansal950", "Backend Developer", "https://github.com/HemantGTX950");

        DeveloperModel dev_obj11 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/Screenshot_20180331-171720_2.png?alt=media&token=90b78435-723c-40c2-a7ab-2035032b1f44", "Aashif Khan", "https://linkedin.com/in/aashifkhan", "App Developer", "https://github.com/aashifkhanate");

        DeveloperModel dev_obj13 = new DeveloperModel
                ("https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/12705373_1054447334607052_709296921303246727_n.jpg?alt=media&token=696d367a-bd56-44f2-a80a-a719ae92f166", "Himanshu Vishwakarma", "https://linkedin.com/in/himanshu-vishwakarma-a10b60119", "Admin App Developer", "https://github.com/HimanshuVishwakarma");

        DeveloperModel dev_obj14 = new DeveloperModel
                ("https://manan-ymca.github.io/ElementsCulmyca2018Website/images/team/t1.jpg", "Rishabh Mahajan", "https://www.linkedin.com/in/rishabh-mahajan-a12764151", "Admin App Developer", "https://github.com/rishabhrishabh");


        devlist.add(dev_obj1);
        devlist.add(dev_obj8);
        devlist.add(dev_obj2);
        devlist.add(dev_obj3);
        devlist.add(dev_obj4);
        devlist.add(dev_obj11);
        devlist.add(dev_obj5);
        devlist.add(dev_obj6);
        devlist.add(dev_obj7);
        devlist.add(dev_obj9);
        devlist.add(dev_obj13);
        devlist.add(dev_obj14);
        devlist.add(dev_obj10);

        DevelopersAdapter dev_ad = new DevelopersAdapter(mContext, devlist);
        devrecyclerView.setAdapter(dev_ad);

        return rootView;
    }
}
