package com.example.collegesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class lecturerSubjectAdapter extends RecyclerView.Adapter<lecturerSubjectAdapter.myViewHolder>{
    Context context;
    ArrayList<subject> list;

    public lecturerSubjectAdapter(Context context, ArrayList<subject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public lecturerSubjectAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecturer_subject_card_view, parent, false);
        return new lecturerSubjectAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull lecturerSubjectAdapter.myViewHolder holder, int position) {
        subject currentItem = list.get(position);
        holder.courseCodeSubjectName.setText(currentItem.getCourseCode() + " " + currentItem.getSubjectName());
        holder.section.setText("Section " + currentItem.getSection());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView courseCodeSubjectName,section;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            courseCodeSubjectName = itemView.findViewById(R.id.subjectInfo);
            section = itemView.findViewById(R.id.section);

        }
    }

}
