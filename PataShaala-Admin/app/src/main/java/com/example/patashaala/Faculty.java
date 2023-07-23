package com.example.patashaala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Faculty extends AppCompatActivity {

    private EditText editTextName, editTextDepartment, editTextEmail;
    private Button buttonSave;

    private ProgressDialog pd;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("faculty");

        editTextName = findViewById(R.id.editTextName);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSave = findViewById(R.id.buttonSave);
        pd=new ProgressDialog(this);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFaculty();
            }
        });
    }

    private void saveFaculty() {
        pd.setMessage("Saving Facultys");
        pd.show();

        String name = editTextName.getText().toString().trim();
        String department = editTextDepartment.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(department) && !TextUtils.isEmpty(email)) {
            // Generate a new unique key for the faculty member
            databaseReference.child("faculty");

            final String uniqueKey = databaseReference.push().getKey();

            // Create a Faculty object with the provided information
            FacultyData faculty = new FacultyData(uniqueKey, name, department, email);

            // Save the faculty information to the Firebase database
            databaseReference.child(uniqueKey).setValue(faculty).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    pd.dismiss();
                    Toast.makeText(Faculty.this, "Done SuccessFully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(Faculty.this, "Error:Issue Emcountered", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }











    private void clearFields() {
        editTextName.setText("");
        editTextDepartment.setText("");
        editTextEmail.setText("");
    }
}
