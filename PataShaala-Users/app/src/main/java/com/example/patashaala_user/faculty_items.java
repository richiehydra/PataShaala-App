package com.example.patashaala_user;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class faculty_items extends AppCompatActivity {

    private FacultyAdapter adapter;
    private ArrayList<FacultyData> facultyList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        facultyList = new ArrayList<>();
        adapter = new FacultyAdapter(this, facultyList);
        recyclerView.setAdapter(adapter);

        // Call a method to fetch data from Firebase and populate the facultyList
        fetchDataFromFirebase();
    }

    // Method to fetch data from Firebase and populate the facultyList
    private void fetchDataFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("faculty");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                facultyList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FacultyData facultyData = snapshot.getValue(FacultyData.class);
                    facultyList.add(facultyData);
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                // Handle the error here if you want to show a message
            }
        });
    }
}
