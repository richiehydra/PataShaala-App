package com.example.patashaala;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class uploadPDF extends AppCompatActivity {

    private CardView SelectPDF;
    private final int REQ=1;

    private EditText InpTitle;

    private String pdfName;


    private Button SubmitButton;

    private ProgressDialog pd;
    private DatabaseReference dbreference;
    private StorageReference storageref;

    private TextView  pdfTextView;

    String downloadUri=" ";

    private Uri pdfdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        dbreference= FirebaseDatabase.getInstance().getReference();
        storageref= FirebaseStorage.getInstance().getReference();

        pd=new ProgressDialog(this);

        SelectPDF=findViewById(R.id.SelectPDF);


        InpTitle=findViewById(R.id.InpTitle);
        SubmitButton=findViewById(R.id.SubmitButton);

        pdfTextView=findViewById(R.id.pdfTextView);

        SelectPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openGallery();
            }
        });

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfdata==null)
                {
                    Toast.makeText(uploadPDF.this, "Pdf Data Not Available", Toast.LENGTH_SHORT).show();
                }else
                {
                    uploadPDF();
                }
            }
        });
    }

    private void uploadPDF()
    {
        if (pdfdata != null) {
            pd.setTitle("Uploading PDF");
            pd.show();

            StorageReference filePath = storageref.child("PDFs").child(pdfName);
            filePath.putFile(pdfdata).addOnSuccessListener(taskSnapshot -> {
                pd.dismiss();
                Toast.makeText(uploadPDF.this, "PDF Uploaded Successfully", Toast.LENGTH_SHORT).show();
                // Perform further actions after successful upload
            }).addOnFailureListener(e -> {
                pd.dismiss();
                Toast.makeText(uploadPDF.this, "Failed to upload PDF", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "No PDF selected", Toast.LENGTH_SHORT).show();
        }
    }




    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select PDF File"), REQ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            pdfdata=data.getData();
            if(pdfdata.toString().startsWith("content://"))
            {
                Cursor cursor;
                try {
                    cursor=uploadPDF.this.getContentResolver().query(pdfdata,null,null,null);
                    if(cursor!=null && cursor.moveToFirst())
                    {
                         int value=cursor.getColumnIndex((OpenableColumns.DISPLAY_NAME));
                         if(value>=0)
                          pdfName=cursor.getString(value);

                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }else if(pdfdata.toString().startsWith("file://"))
            {
              pdfName=new File(pdfdata.toString()).getName();
            }
            pdfTextView.setText(pdfName);
        }
    }
}