package com.example.patashaala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    CardView uploadNotice, UploadImage,UploadPDF,UpdateFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadNotice = findViewById(R.id.addNotice);
        UploadImage = findViewById(R.id.UploadImage);
        UploadPDF=findViewById(R.id.uploadEbook);
        UpdateFaculty=findViewById(R.id.UpdateFaculty);

        uploadNotice.setOnClickListener(this);
        UploadImage.setOnClickListener(this);
        UploadPDF.setOnClickListener(this);
        UpdateFaculty.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.addNotice) {
            Intent intent = new Intent(MainActivity.this, UploadNotice.class);
            startActivity(intent);
        } else if (id == R.id.UploadImage) {
            Intent intent = new Intent(MainActivity.this, UploadImage.class);
            startActivity(intent);
        }else if(id== R.id.uploadEbook)
        {
            Intent intent = new Intent(MainActivity.this, uploadPDF.class);
            startActivity(intent);
        }else if(id==R.id.UpdateFaculty)
        {
            Intent intent = new Intent(MainActivity.this, Faculty.class);
            startActivity(intent);
        }

    }

}