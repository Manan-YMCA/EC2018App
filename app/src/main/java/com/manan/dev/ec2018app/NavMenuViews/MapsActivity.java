package com.manan.dev.ec2018app.NavMenuViews;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.manan.dev.ec2018app.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("Location Map");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        11);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 11) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                this.recreate();
            } else {
                mMap.setMyLocationEnabled(false);
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(false);
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
                mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Log.e("Maps", "" + e);
        }

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("TAG", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("TAG", "Can't find style. Error: ", e);
        }
        LatLng ymca = new LatLng(28.3674761, 77.3169494);
        CameraPosition target = CameraPosition.builder().target(ymca).zoom((float) 17.6).bearing(85).tilt(30).build();

        MarkerOptions defaultMarker = new MarkerOptions().position(ymca).title("YMCA University of Science and Technology").snippet("Elements Culmyca'17").icon(BitmapDescriptorFactory.fromResource(R.drawable.locationicon));
        mMap.addMarker(defaultMarker).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367732, 77.317092)).title("Main Stage"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367005, 77.316748)).title("Shakuntalam Stage"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367354, 77.316411)).title("Library"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367297, 77.316722)).title("Main Building"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.366900, 77.316433)).title("Canteen"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367250, 77.315419)).title("Parking"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.366504, 77.316674)).title("Mechanical Department"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367727, 77.317602)).title("MBA Department"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367418, 77.316191)).title("Electrical Chowk"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.366661, 77.316368)).title("Eco Café"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.366557, 77.315696)).title("Mother Dairy"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367620, 77.317181)).title("Lal Chowk"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(28.367410, 77.315951)).title("Antarang 18"));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context mContext = getApplicationContext();
                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());
                snippet.setGravity(Gravity.CENTER);

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(28.368196, 77.315378))
                .add(new LatLng(28.368404, 77.318650))
                .add(new LatLng(28.366487, 77.318736))
                .add(new LatLng(28.366308, 77.315463))
                .add(new LatLng(28.368196, 77.315378))
                .color(R.color.colorPrimary));

    /*    mMap.addPolyline(new PolylineOptions().geodesic(true)
                .add(new LatLng(28.842884, 77.104147))
                .add(new LatLng(28.842788, 77.104222))
                .add(new LatLng(28.842628, 77.104598))
                .add(new LatLng(28.842626, 77.104737))
                .add(new LatLng(28.842689, 77.105008))
                .add(new LatLng(28.842590, 77.105038))
                .add(new LatLng(28.842531, 77.104826))
                .add(new LatLng(28.842498, 77.104829))
                .add(new LatLng(28.842479, 77.104655))
                .add(new LatLng(28.842505, 77.104440))
                .add(new LatLng(28.842618, 77.104177))
                .add(new LatLng(28.842797, 77.104008))
                .add(new LatLng(28.842884, 77.104147))
                .color(R.color.colorPrimary));*/

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
