package com.example.android.FriendsFinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Toast;


import com.example.android.FriendsFinder.model.FriendInfo;
import com.example.android.FriendsFinder.roomDb.Friends;
import com.example.android.FriendsFinder.roomDb.FriendsDatabase;
import com.example.android.FriendsFinder.view.FriendsAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAllFriends extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<FriendInfo> arrayList;
    RecyclerView recyclerView;
    FriendsAdapter adapter;

    ArrayList<String> mKey;

    FriendsDatabase friendsDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_friends);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("userProfile");

        arrayList = new ArrayList<>();
        mKey = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_show);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        friendsDatabase = Room.databaseBuilder(this,
                FriendsDatabase.class,"database2").allowMainThreadQueries().build();

        getLive();


    }


    public void getData() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("userName").getValue().toString();
                    String email = ds.child("email").getValue().toString();
                    String imageUrl = ds.child("imageUrl").getValue().toString();
                    Double lat = Double.valueOf(ds.child("Latitude").getValue().toString());
                    Double lng = Double.valueOf(ds.child("Longitude").getValue().toString());

                    arrayList.add(new FriendInfo(name,lat,lng,email,imageUrl));
                }


                adapter = new FriendsAdapter(getApplicationContext(),arrayList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void getLive() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot,@Nullable String s) {
                String name = dataSnapshot.child("userName").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                Double lat = Double.valueOf(dataSnapshot.child("Latitude").getValue().toString());
                Double lng = Double.valueOf(dataSnapshot.child("Longitude").getValue().toString());

                String key = dataSnapshot.getKey();
                mKey.add(key);

                FriendInfo friendInfo = new FriendInfo(name,lat,lng,email,imageUrl);
                Friends friends = new Friends(name,lat,lng,email,imageUrl);

                try {
                    friendsDatabase.friendsDao().insertAll(friends);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
                arrayList.add(friendInfo);

                adapter = new FriendsAdapter(ShowAllFriends.this,arrayList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot,@Nullable String s) {

                String key = dataSnapshot.getKey();
                int index = mKey.indexOf(key);


                String name = dataSnapshot.child("userName").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                Double lat = Double.valueOf(dataSnapshot.child("Latitude").getValue(String.class));
                Double lng = Double.valueOf(dataSnapshot.child("Longitude").getValue(String.class));

                arrayList.remove(index);
                arrayList.add(index,new FriendInfo(name,lat,lng,email,imageUrl));


                adapter = new FriendsAdapter(ShowAllFriends.this,arrayList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String key = dataSnapshot.getKey();
                int index = mKey.indexOf(key);
                arrayList.remove(index);
                adapter = new FriendsAdapter(ShowAllFriends.this,arrayList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot,@Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(),"application offline" + databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }


}
