package com.manan.dev.ec2018app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.EventDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CategoryEventDisplayActivity extends AppCompatActivity {

    private String clubName;
    private TextView clubDisplayName;
    private ImageView clubImage;
    private RecyclerView myRecyclerView;
    private DatabaseController databaseController;
    private ImageView backButton;
//    private ProgressDialog myDialog;
    ArrayList<EventDetails> eventList;
    private TextView clubDescpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_event_display);

        backButton = findViewById(R.id.iv_back_button);
        clubName = getIntent().getStringExtra("clubname");
        databaseController = new DatabaseController(getApplicationContext());
        eventList = databaseController.retreiveCategory(clubName);
        Toast.makeText(this, clubName, Toast.LENGTH_SHORT).show();

        byte[] byteArray = getIntent().getByteArrayExtra("clubPhoto");
        Bitmap clubphoto = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        String displayName = getIntent().getStringExtra("clubdisplay");

        clubImage = (ImageView) findViewById(R.id.iv_category_image);
        clubDisplayName = (TextView) findViewById(R.id.tv_category_name_heading);
        if (clubName.equals("Jhalak")) {
            clubDisplayName.setText("Photography");
        } else {
            clubDisplayName.setText(displayName);
        }
        Drawable drawable = new BitmapDrawable(this.getResources(), clubphoto);
        clubImage.setImageDrawable(drawable);

        clubDescpTextView = findViewById(R.id.tv_category_descp_heading);

//        myDialog = new ProgressDialog(CategoryEventDisplayActivity.this);
//        myDialog.setMessage("Events Loading...");
//        myDialog.setCancelable(false);
//        myDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(CategoryEventDisplayActivity.this, "Error!", Toast.LENGTH_LONG).show();
//            }
//        });
//        myDialog.show();

        myRecyclerView = (RecyclerView) findViewById(R.id.events_list);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(new eventAdapter(this, eventList));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public class eventAdapter extends RecyclerView.Adapter<eventAdapter.MyViewHolder> {

        private ArrayList<EventDetails> eventList;
        private Context context;

        public eventAdapter(Context context, ArrayList<EventDetails> ll) {
            this.context = context;
            this.eventList = ll;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_event_card_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final EventDetails eventDetails = eventList.get(position);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(eventDetails.getmStartTime());
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yy", Locale.ENGLISH);
            String formattedDate = sdf.format(cal.getTime());

            SimpleDateFormat sdf1 = new SimpleDateFormat("kk:mm", Locale.US);
            String formattedTime = sdf1.format(cal.getTime());

            holder.name.setText(eventDetails.getmName());
            holder.date.setText(formattedDate);
            holder.time.setText(formattedTime);

            if(!eventDetails.getmEventTeamSize().equals("solo")) {
                holder.typeOfEventPhoto.setImageDrawable(getResources().getDrawable(R.drawable.vector_team));
//              Picasso.with(CategoryEventDisplayActivity.this).load(R.drawable.vector_team).resize(50, 50).centerCrop().into(holder.typeOfEventPhoto);
            }
            else if(eventDetails.getmEventTeamSize().equals("solo")){
                holder.typeOfEventPhoto.setImageDrawable(getResources().getDrawable(R.drawable.vector_single));
//              Picasso.with(CategoryEventDisplayActivity.this).load(R.drawable.vector_single).resize(50, 50).centerCrop().into(holder.typeOfEventPhoto);
            }

            if(!eventDetails.getmEventTeamSize().equals("NA")) {
                holder.eventType.setText(eventDetails.getmEventTeamSize());
            }else if(eventDetails.getmEventTeamSize().equals("NA")){
                holder.eventType.setText("Open to All");
            }

            holder.desc.setText(eventDetails.getmDesc().substring(0, Math.min(eventDetails.getmDesc().length(), 100)));
            if(eventDetails.getmDesc().length()>100)
                holder.desc.append("...");

            if (eventDetails.getmFees() == 0) {
                holder.fees.setText("Free");
            } else
                holder.fees.setText(String.valueOf(eventDetails.getmFees()));
            holder.venue.setText(eventDetails.getmVenue());
            holder.viewmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CategoryEventDisplayActivity.this, SingleEventActivity.class)
                            .putExtra("eventId", eventDetails.getmEventId()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView typeOfEventPhoto;
            public TextView name, date, time, fees, venue, eventType,desc;
            public CardView mCardView;
            public Button viewmore;

            public MyViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                typeOfEventPhoto = itemLayoutView.findViewById(R.id.iv_event_type);
                name = (TextView) itemLayoutView.findViewById(R.id.tv_cv_event_name);
                desc = (TextView) itemLayoutView.findViewById(R.id.tv_cv_event_desc);
                date = (TextView) itemLayoutView.findViewById(R.id.tv_cv_event_date);
                time = (TextView) itemLayoutView.findViewById(R.id.tv_cv_event_time);
                fees = (TextView) itemLayoutView.findViewById(R.id.tv_cv_event_fees);
                venue = (TextView) itemLayoutView.findViewById(R.id.tv_cv_event_venue);
                viewmore = (Button) itemLayoutView.findViewById(R.id.iv_button_viewmore);
                eventType = (TextView) itemLayoutView.findViewById(R.id.tv_event_type);
                mCardView = (CardView) itemLayoutView.findViewById(R.id.cv_event_card);
            }
        }

    }
}
