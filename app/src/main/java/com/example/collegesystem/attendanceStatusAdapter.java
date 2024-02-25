package com.example.collegesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class attendanceStatusAdapter extends RecyclerView.Adapter<attendanceStatusAdapter.myViewHolder>{
    Context context;
    ArrayList<String> studentMatricNoAndName;
    ArrayList<String> attendanceStatuses;
    public attendanceStatusAdapter(Context context) {
        this.context = context;
        studentMatricNoAndName = new ArrayList<>();
        attendanceStatuses = new ArrayList<>();;
    }

    @NonNull
    @Override
    public attendanceStatusAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_status_card_view, parent, false);
        return new attendanceStatusAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull attendanceStatusAdapter.myViewHolder holder, int position) {
        holder.matricAndName.setText(studentMatricNoAndName.get(position));
        holder.status.setText(attendanceStatuses.get(position));
    }
    @Override
    public int getItemCount() {
        return studentMatricNoAndName.size(); // Return the size of the studentMatricNoAndName list
    }

    public void addItem(String tempStudentMatricNoAndName, String attendanceStatus) {
        studentMatricNoAndName.add(tempStudentMatricNoAndName);
        attendanceStatuses.add(attendanceStatus);
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }
    public void clearItems() {
        studentMatricNoAndName.clear();
        attendanceStatuses.clear();
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView matricAndName,status;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            matricAndName = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);

        }
    }


}
