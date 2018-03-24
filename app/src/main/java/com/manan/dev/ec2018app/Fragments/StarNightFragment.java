package com.manan.dev.ec2018app.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.manan.dev.ec2018app.Models.BrixxEventModel;
import com.manan.dev.ec2018app.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class StarNightFragment extends Fragment {

    private ChildEventListener mChildEventListener;
    private com.google.firebase.database.Query mDatabaseReference;
    private FirebaseAuth mAuth;
    private TextView title_view, date_view, content_view;
    private ImageView fb_btn, insta;
    private ImageView back_image;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public StarNightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_star_night, container, false);

        if(mContext == null){
            mContext = getActivity();
        }

        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("brixx");
        title_view = rootView.findViewById(R.id.inf_event_title);
        date_view = rootView.findViewById(R.id.inf_date);
        content_view = rootView.findViewById(R.id.inf_content);
        fb_btn = rootView.findViewById(R.id.fb);
        insta = rootView.findViewById(R.id.insta);
        back_image = rootView.findViewById(R.id.back_img);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        attachDatabaseListener();
    }


    @Override
    public void onPause() {
        super.onPause();
        detatchDatabaseListener();
    }


    private void detatchDatabaseListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void attachDatabaseListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals("starnight")) {
                        BrixxEventModel w = dataSnapshot.getValue(BrixxEventModel.class);
                        updateUI(w);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    try {
                        if (dataSnapshot.getKey().equals("starnight")) {
                            BrixxEventModel w = dataSnapshot.getValue(BrixxEventModel.class);
                            updateUI(w);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void updateUI(final BrixxEventModel w) {
        title_view.setText(w.getTitle());
        date_view.setText(w.getDate().toString());
        content_view.setText(w.getContent().toString());
        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(w.getFblink().toString()));
                mContext.startActivity(myIntent);
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(w.getInstalink().toString()));
                mContext.startActivity(myIntent);
            }
        });

        Picasso.with(mContext).load(w.getPhotourl().toString()).into(back_image);
    }
}