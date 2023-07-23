package com.example.patashaala;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadImage extends AppCompatActivity {

  private final int REQ=1;

    private Bitmap bitmap;
    private CardView addImageGallery;

    private Spinner imageCategory;

    private Button SubmitImageButton;

    private ImageView GalleryImageView;

    private DatabaseReference dbreference;
    private StorageReference storageref;
    private String category;

    String downloadUri="";
   ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        addImageGallery=findViewById(R.id.addImageGallery);
        imageCategory=findViewById(R.id.imageCategory);
        SubmitImageButton=findViewById(R.id.SubmitImageButton);
        GalleryImageView=findViewById(R.id.GalleryImageView);

        dbreference= FirebaseDatabase.getInstance().getReference().child("Gallery");
        storageref= FirebaseStorage.getInstance().getReference().child("Gallery");

        pd=new ProgressDialog(this);
        String[] items=new String[]{"Select Category" ," Convocations" ,"College Day" ,"Other Events"};

        imageCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

     imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             category=imageCategory.getSelectedItem().toString();
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {
             Toast.makeText(UploadImage.this, "Please Select the Item", Toast.LENGTH_SHORT).show();

         }
     });


        addImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        SubmitImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                           if(bitmap==null)
                           {
                               Toast.makeText(UploadImage.this, "Please Pick Image", Toast.LENGTH_SHORT).show();
                           }else if(category.equals("Select Category"))
                           {
                               Toast.makeText(UploadImage.this, "Please Select Image Category", Toast.LENGTH_SHORT).show();
                           }else {
                                       pd.setMessage("Uploading");
                                       pd.show();
                                       uploadImage();
                           }
            }
        });

    }

    private void openGallery()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQ);
    }

    private void uploadImage()
    {





        ByteArrayOutputStream bios=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bios);
        byte[] finalimage=bios.toByteArray();

        final StorageReference filepath;

        filepath=storageref.child(finalimage + ".jpg");

        final UploadTask uploadtask=filepath.putBytes(finalimage);
        uploadtask.addOnCompleteListener(UploadImage.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUri = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UploadImage.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void uploadData()
    {
        dbreference =dbreference.child(category);
        final String uniqueKey=dbreference.push().getKey();
        dbreference.child(uniqueKey).setValue(downloadUri).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Image uploaded Succesffully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadImage.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GalleryImageView.setImageBitmap(bitmap);
        }
    }
}