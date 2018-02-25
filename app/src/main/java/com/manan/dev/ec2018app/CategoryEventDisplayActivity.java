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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.manan.dev.ec2018app.DatabaseHandler.DatabaseController;
import com.manan.dev.ec2018app.Models.EventDetails;
import java.util.ArrayList;

public class CategoryEventDisplayActivity extends AppCompatActivity {

    private String clubName;
    private TextView clubDisplayName;
    private ImageView clubImage;
    private RecyclerView myRecyclerView;
    private DatabaseController databaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_event_display);

        clubName = getIntent().getStringExtra("clubname");
        databaseController = new DatabaseController(getApplicationContext());
        ArrayList<EventDetails> eventList;
        eventList = databaseController.retreiveCategory(clubName);
        Toast.makeText(this, clubName, Toast.LENGTH_SHORT).show();

        byte[] byteArray = getIntent().getByteArrayExtra("clubPhoto");
        Bitmap clubphoto = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        String displayName= getIntent().getStringExtra("clubdisplay");

        clubImage = (ImageView) findViewById(R.id.iv_category_image);
        clubDisplayName = (TextView) findViewById(R.id.tv_category_name_heading);

        clubDisplayName.setText(displayName);

        Drawable drawable = new BitmapDrawable(this.getResources(), clubphoto);
        clubImage.setImageDrawable(drawable);

        myRecyclerView = (RecyclerView) findViewById(R.id.events_list);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(new eventAdapter(this, eventList));

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
            holder.name.setText(eventDetails.getmName());
            holder.date.setText(eventDetails.getmName());
            holder.time.setText(String.valueOf(eventDetails.getmStartTime()));
            holder.fees.setText(String.valueOf(eventDetails.getmFees()));
            holder.venue.setText(eventDetails.getmVenue());
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
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
            public ImageView photo;
            public TextView name, date, time, fees, venue;
            public CardView mCardView;

            public MyViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                name = (TextView)itemLayoutView.findViewById(R.id.tv_cv_event_name);
                date = (TextView)itemLayoutView.findViewById(R.id.tv_cv_event_date);
                time = (TextView)itemLayoutView.findViewById(R.id.tv_cv_event_time);
                fees = (TextView)itemLayoutView.findViewById(R.id.tv_cv_event_fees);
                venue = (TextView)itemLayoutView.findViewById(R.id.tv_cv_event_venue);
                mCardView = (CardView) itemLayoutView.findViewById(R.id.cv_event_card);
            }
        }

    }
}
