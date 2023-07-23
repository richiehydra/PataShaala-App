package com.example.patashaala;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity implements View.OnClickListener {

    private CardView selectImage;
    private final int REQ=1;

    private EditText InputTitle;
    private ImageView noticeImageView;

    private Button SubmitButton;

    private ProgressDialog pd;
    private DatabaseReference dbreference;
    private StorageReference storageref;

    String downloadUri=" ";

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        dbreference= FirebaseDatabase.getInstance().getReference();
        storageref= FirebaseStorage.getInstance().getReference();

        pd=new ProgressDialog(this);

        selectImage=findViewById(R.id.SelectImage);
        selectImage.setOnClickListener(this);
        noticeImageView=findViewById(R.id.ImageView);
        InputTitle=findViewById(R.id.InputTitle);
        SubmitButton=findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputTitle.getText().toString().isEmpty())
                {
                    InputTitle.setError("Empty Notice Title");
                    InputTitle.requestFocus();
                }else if(bitmap==null){
                    uploadData();

                }else {
                    uploadImage();
                }
            }
        });


    }


    private void uploadData()
    {
       dbreference =dbreference.child("Notice");
       final String uniqueKey=dbreference.push().getKey();



         String title=InputTitle.getText().toString();
        Calendar calender =Calendar.getInstance();
        SimpleDateFormat currdate=new SimpleDateFormat("dd-MM-yy");
        String date=currdate.format(calender.getTime());


        Calendar calendertime =Calendar.getInstance();
        SimpleDateFormat currtime=new SimpleDateFormat("hh:mm:ss");
        String time=currtime.format(calendertime.getTime());

        NoticeData noticedata=new NoticeData(date,time,title,downloadUri,uniqueKey);

        dbreference.child(uniqueKey).setValue(noticedata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Done SuccessFully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Error:Issue Emcountered", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage() {

        pd.setMessage("Uploading");
        pd.show();

        ByteArrayOutputStream bios=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bios);
        byte[] finalimage=bios.toByteArray();

        final StorageReference filepath;

        filepath=storageref.child("Notices").child(finalimage + ".jpg");

        final UploadTask uploadtask=filepath.putBytes(finalimage);
        uploadtask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(UploadNotice.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        openGallery();
    }
    private void openGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(intent,REQ);
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
            noticeImageView.setImageBitmap(bitmap);
        }
    }
}