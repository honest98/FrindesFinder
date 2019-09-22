package com.example.android.FriendsFinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.FriendsFinder.model.FriendInfo;
import com.example.android.FriendsFinder.view.FriendsAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View view;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        database = FirebaseDatabase.getInstance();
        String userid = getIntent().getStringExtra("userId");
        myRef = database.getReference("userProfile").child(userid);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        view = findViewById(R.id.map);
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

//        if(getIntent().hasExtra("lat")  && getIntent().hasExtra("lng")) {

            Toast.makeText(this, "map ready", Toast.LENGTH_LONG).show();

        double lat = getIntent().getDoubleExtra("lat", 0);
        double lng = getIntent().getDoubleExtra("lng", 0);
            final String friendName = getIntent().getStringExtra("friend");

//        myRef.addChildEventListener(new ChildEventListener() {
//                                        @Override
//                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot,@Nullable String s) {
//                                            Double lat = Double.parseDouble(dataSnapshot.child("Latitude").getValue(String.class));
//                                            Double lng = Double.parseDouble(dataSnapshot.child("Longitude").getValue(String.class));
//                                            LatLng friendPosition = new LatLng(lat,lng);
//                                            mMap.addMarker(new MarkerOptions().position(friendPosition).title(friendName));
//                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(friendPosition));
//                                        }
//
//                                        @Override
//                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot,@Nullable String s) {
//                                            Double lat = Double.parseDouble(dataSnapshot.child("Latitude").getValue(String.class));
//                                            Double lng = Double.parseDouble(dataSnapshot.child("Longitude").getValue(String.class));
//                                            LatLng friendPosition = new LatLng(lat,lng);
//                                            mMap.addMarker(new MarkerOptions().position(friendPosition).title(friendName));
//                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(friendPosition));
//                                        }
//
//                                        @Override
//                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                        }
//
//                                        @Override
//                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot,@Nullable String s) {
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });


                //             Add a marker in Sydney and move the camera
                LatLng friendPosition = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(friendPosition).title(friendName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(friendPosition));



//        } else {
//
////             Add a marker in Sydney and move the camera
//            LatLng sydney = new LatLng(-34,151);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//            Toast.makeText(this, "no location to view", Toast.LENGTH_LONG).show();


//            Snackbar.make(view,"Replace with your own action",Snackbar.LENGTH_LONG)
//                    .setAction("Action",null).show();
//        }
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

}
