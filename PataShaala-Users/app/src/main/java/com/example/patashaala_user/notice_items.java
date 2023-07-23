package com.example.patashaala_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;




public class notice_items extends AppCompatActivity {

    private NoticeAdapter adapter;
    private ArrayList<NoticeData> noticeList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notices);

        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noticeList = new ArrayList<>();
        adapter = new NoticeAdapter(notice_items.this, noticeList);
        recyclerView.setAdapter(adapter);

        // Call a method to fetch data from Firebase and populate the noticeList
        fetchDataFromFirebase();
    }

    // Method to fetch data from Firebase and populate the noticeList
    private void fetchDataFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Notice");



        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noticeList.clear();

                Log.d("GalleryFragment", "Other Image List Size: " + dataSnapshot);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    NoticeData noticeData = snapshot.getValue(NoticeData.class);

                    noticeList.add(noticeData);
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                // Handle the error here if you want to show a message
                Toast.makeText(notice_items.this, "Cant Fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




