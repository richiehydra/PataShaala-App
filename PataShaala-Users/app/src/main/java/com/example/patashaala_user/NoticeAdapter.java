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
import android.widget.Toast;


import com.bumptech.glide.Glide;



public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    final private Context context;
   final  private ArrayList<NoticeData> noticeList;

    public NoticeAdapter(Context context, ArrayList<NoticeData> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_notice_items, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        final NoticeData currentNotice = noticeList.get(position);

        // Set the title and other data
        holder.noticeTitle.setText(currentNotice.getTitle());
        holder.noticeDate.setText(currentNotice.getDate());
        holder.noticeTime.setText(currentNotice.getTime());



        try {
            if (currentNotice.getImage() != null) {
                Glide.with(context).load(currentNotice.getImage()).into(holder.imageNotice);
            }
        } catch (Exception c) {
            c.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    // ViewHolder class for caching the views
    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView noticeTitle;
        TextView noticeDate;
        TextView noticeTime;
        ImageView imageNotice;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeDate = itemView.findViewById(R.id.date);
            noticeTime = itemView.findViewById(R.id.time);
            imageNotice = itemView.findViewById(R.id.imageNotice);
        }
    }
}



