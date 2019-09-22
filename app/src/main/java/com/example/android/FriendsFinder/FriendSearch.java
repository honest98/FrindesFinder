package com.example.android.FriendsFinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FriendSearch extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView txt1, txt2;

    SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Switch service;


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    double lat = 0, lan = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);

        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("userProfile");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        service = findViewById(R.id.sw_location);


        service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {
                    onConnected(savedInstanceState);
                    Toast.makeText(getApplicationContext(),"Location On",Toast.LENGTH_SHORT).show();

                }
                if (!isChecked) {
                    onConnected(savedInstanceState);
                    Toast.makeText(getApplicationContext(),"Location Off",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void find(View view) {
    }

    public void showAllFriends(View view) {

        startActivity(new Intent(this,ShowAllFriends.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_signout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.sign_out:
                signOut();
                startActivity(new Intent(this,SignIn.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signOut() {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear().commit();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Update location every second


        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (service.isChecked()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
            Toast.makeText(this,"Location On",Toast.LENGTH_SHORT).show();
        }
        if (!service.isChecked()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            Toast.makeText(this,"Location Off",Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onLocationChanged(Location location) {

        this.lat = location.getLatitude();
        this.lan = location.getLongitude();

        myRef.getRef().child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myRef.getRef().child(user.getUid()).child("Latitude").setValue(String.valueOf(lat));
                myRef.getRef().child(user.getUid()).child("Longitude").setValue(String.valueOf(lan));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

}
