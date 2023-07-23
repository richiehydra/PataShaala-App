package com.example.patashaala_user;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;



import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.LinearLayoutManager;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.google.firebase.database.ValueEventListener;

import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;



import java.util.List;



public class GalleryFragment extends AppCompatActivity {

    RecyclerView convorecyclerView, collegerecyclerView, othersrecyclerView;
    GalleryAdapter convoAdapter, collegeAdapter, othersAdapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);

        convorecyclerView = findViewById(R.id.convorecyclerView);
        collegerecyclerView = findViewById(R.id.collegerecyclerView);
        othersrecyclerView = findViewById(R.id.othersrecyclerView);

        convorecyclerView.setLayoutManager(new LinearLayoutManager(this));
        collegerecyclerView.setLayoutManager(new LinearLayoutManager(this));
        othersrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> convoList = new ArrayList<>();
        List<String> collegeList = new ArrayList<>();
        List<String> othersList = new ArrayList<>();

        convoAdapter = new GalleryAdapter(this, convoList);
        collegeAdapter = new GalleryAdapter(this, collegeList);
        othersAdapter = new GalleryAdapter(this, othersList);

        convorecyclerView.setAdapter(convoAdapter);
        collegerecyclerView.setAdapter(collegeAdapter);
        othersrecyclerView.setAdapter(othersAdapter);

        getConvoImage();
        getCollegeImage();
        getOtherImage();
    }


    // Log.d("GalleryFragment", "Other Image List Size: " + imageList.size());
    //
    private void getConvoImage() {
        reference = FirebaseDatabase.getInstance().getReference().child("Gallery").child(" Convocations");

        reference.keepSynced(true);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("GalleryFragment", "Other Image List Size: " + dataSnapshot);
                if (dataSnapshot.exists()) {
                    List<String> imageList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String imageUrl = snapshot.getValue(String.class);
                        imageList.add(imageUrl);
                    }
                    Log.d("GalleryFragment", "Other Image List Size: " + imageList.size());
                    convoAdapter.setImageList(imageList);
                } else {
                    Log.d("GalleryFragment", "No data found under 'Convocations' node.");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GalleryFragment.this, "Can't Fetch Convocations", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCollegeImage() {
        reference = FirebaseDatabase.getInstance().getReference("Gallery");
        reference.child("College Day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> imageList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageUrl = snapshot.getValue(String.class);
                    imageList.add(imageUrl);
                }
                collegeAdapter.setImageList(imageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GalleryFragment.this, "Can't Fetch College Day", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOtherImage() {
        reference = FirebaseDatabase.getInstance().getReference("Gallery");
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> imageList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String imageUrl = snapshot.getValue(String.class);
                    imageList.add(imageUrl);
                }
                othersAdapter.setImageList(imageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GalleryFragment.this, "Can't Fetch Other Events", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
