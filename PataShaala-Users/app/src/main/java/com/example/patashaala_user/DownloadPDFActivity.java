package com.example.patashaala_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DownloadPDFActivity extends AppCompatActivity implements PDFAdapter.OnPDFClickListener {

    private RecyclerView recyclerView;
    private PDFAdapter pdfAdapter;
    private List<String> pdfList = new ArrayList<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private Button downloadButton;
    private String selectedPdfUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_pdf);

        recyclerView = findViewById(R.id.recyclerView);
        downloadButton = findViewById(R.id.downloadButton);

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfAdapter = new PDFAdapter(pdfList, this);
        recyclerView.setAdapter(pdfAdapter);

        // Retrieve list of PDFs from Firebase Storage
        getPdfList();

        // Implement a download button to download the selected PDF
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPdfUrl != null) {
                    downloadPdf(selectedPdfUrl);
                } else {
                    Toast.makeText(DownloadPDFActivity.this, "Please select a PDF first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getPdfList() {
        // Here, you need to fetch the list of PDFs stored in Firebase Storage.
        // Replace "your_folder_name" with the actual folder name where your PDFs are stored.
        StorageReference pdfFolderRef = storageRef.child("PDFs");
        pdfFolderRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<StorageReference> items = listResult.getItems();
                for (StorageReference item : items) {
                    pdfList.add(item.getName());
                }
                pdfAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DownloadPDFActivity.this, "Failed to retrieve PDF list", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void downloadPdf(String pdfUrl) {
        // Implement the PDF download logic here
        // You can use a library like PdfViewer to open the downloaded PDF
        // For simplicity, we will just show a toast message here
        Toast.makeText(this, "Downloading PDF: " + pdfUrl, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPDFClick(int position) {
        // Handle the click event of the PDF item in the RecyclerView
        // In this example, we will just store the selected PDF URL
        selectedPdfUrl = pdfList.get(position);
    }
}
