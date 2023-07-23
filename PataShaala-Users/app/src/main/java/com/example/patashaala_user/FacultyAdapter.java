package com.example.patashaala_user;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.content.Context;

import android.widget.ImageView;
import android.widget.TextView;



import com.bumptech.glide.Glide;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {

    private Context context;
    private ArrayList<FacultyData> facultyList;

    public FacultyAdapter(Context context, ArrayList<FacultyData> facultyList) {
        this.context = context;
        this.facultyList = facultyList;
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_faculty_items, parent, false);
        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {
        FacultyData facultyData = facultyList.get(position);

        holder.facultyNameTextView.setText(facultyData.getName());
        holder.facultyDesignationTextView.setText(facultyData.getDepartment());
        holder.facultyEmailTextView.setText(facultyData.getEmail());
    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    public static class FacultyViewHolder extends RecyclerView.ViewHolder {
        TextView facultyNameTextView;
        TextView facultyDesignationTextView;
        TextView facultyEmailTextView;

        public FacultyViewHolder(View itemView) {
            super(itemView);

            facultyNameTextView = itemView.findViewById(R.id.facultyNameTextView);
            facultyDesignationTextView = itemView.findViewById(R.id.facultyDesignationTextView);
            facultyEmailTextView = itemView.findViewById(R.id.facultyEmailTextView);
        }
    }
}
