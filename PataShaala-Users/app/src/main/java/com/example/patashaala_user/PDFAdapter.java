package com.example.patashaala_user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

    private List<String> pdfList;
    private OnPDFClickListener pdfClickListener;

    public PDFAdapter(List<String> pdfList, OnPDFClickListener pdfClickListener) {
        this.pdfList = pdfList;
        this.pdfClickListener = pdfClickListener;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
        return new PDFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        String pdfName = pdfList.get(position);
        holder.pdfNameTextView.setText(pdfName);
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public class PDFViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pdfNameTextView;

        public PDFViewHolder(@NonNull View itemView) {
            super(itemView);
            pdfNameTextView = itemView.findViewById(R.id.pdfNameTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            pdfClickListener.onPDFClick(getAdapterPosition());
        }
    }

    public interface OnPDFClickListener {
        void onPDFClick(int position);
    }
}
