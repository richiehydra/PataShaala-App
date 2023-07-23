package com.example.patashaala_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    CardView AboutUs, GetNotice, GetImages, GetEbook, GetFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AboutUs = findViewById(R.id.AboutUs);
        GetNotice = findViewById(R.id.GetNotice);
        GetImages = findViewById(R.id.GetImages);
        GetEbook = findViewById(R.id.GetEbook);
        GetFaculty = findViewById(R.id.GetFaculty);

        AboutUs.setOnClickListener(this);
        GetNotice.setOnClickListener(this);
        GetFaculty.setOnClickListener(this);
        GetImages.setOnClickListener(this);
        GetEbook.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.AboutUs) {
            Intent intent = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent);
        }else if(id==R.id.GetNotice)
        {
            Intent intent = new Intent(MainActivity.this, notice_items.class);
            startActivity(intent);
        }else if(id==R.id.GetFaculty)
        {
            Intent intent = new Intent(MainActivity.this, faculty_items.class);
            startActivity(intent);
        }else if(id==R.id.GetImages)
        {
            Intent intent = new Intent(MainActivity.this, GalleryFragment.class);
            startActivity(intent);
        }else if(id==R.id.GetEbook)
        {
            Intent intent = new Intent(MainActivity.this, DownloadPDFActivity.class);
            startActivity(intent);
        }

    }
}