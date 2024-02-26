package com.example.collegesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class studentAttendanceAdapter extends RecyclerView.Adapter<studentAttendanceAdapter.myViewHolder>{
    Context context;
    ArrayList<subject> list;

    public studentAttendanceAdapter(Context context, ArrayList<subject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public studentAttendanceAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_card_view, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        subject currentItem = list.get(position);
        holder.studentName.setText(currentItem.getStudentName());
        holder.matricNo.setText(currentItem.getStudentMatricNo());
        // Set checkbox states based on subject object
        holder.checkBoxFunction(currentItem);


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,matricNo;
        CheckBox attendCheckBox,excuseCheckBox,absentCheckBox;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            matricNo = itemView.findViewById(R.id.matricNo);
            attendCheckBox = itemView.findViewById(R.id.attendCheckBox);
            absentCheckBox = itemView.findViewById(R.id.absentCheckBox);
            excuseCheckBox = itemView.findViewById(R.id.excuseCheckBox);
            //checkBoxFunction();



        }

        public  void checkBoxFunction(subject currentItem){
            attendCheckBox = itemView.findViewById(R.id.attendCheckBox);
            absentCheckBox = itemView.findViewById(R.id.absentCheckBox);
            excuseCheckBox = itemView.findViewById(R.id.excuseCheckBox);

                attendCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        absentCheckBox.setChecked(false);
                        excuseCheckBox.setChecked(false);
                        currentItem.setAttend(true);
                        currentItem.setAbsent(false);
                        currentItem.setExcuse(false);
                    }
                });

                absentCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        attendCheckBox.setChecked(false);
                        excuseCheckBox.setChecked(false);
                        currentItem.setAbsent(true);
                        currentItem.setAttend(false);
                        currentItem.setExcuse(false);
                    }
                });

                excuseCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        attendCheckBox.setChecked(false);
                        absentCheckBox.setChecked(false);
                        currentItem.setExcuse(true);
                        currentItem.setAttend(false);
                        currentItem.setAbsent(false);
                    }
                });

            /*attendCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle attend checkbox state change
                    // Ensure only one checkbox is checked
                    absentCheckBox.setChecked(false);
                    excuseCheckBox.setChecked(false);
                    list.get(getAdapterPosition()).setAttend(true);
                    list.get(getAdapterPosition()).setAbsent(false);
                    list.get(getAdapterPosition()).setExcuse(false);
                }
            });

            absentCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle absent checkbox state change
                    // Ensure only one checkbox is checked
                    attendCheckBox.setChecked(false);
                    excuseCheckBox.setChecked(false);
                    list.get(getAdapterPosition()).setAbsent(true);
                    list.get(getAdapterPosition()).setAttend(false);
                    list.get(getAdapterPosition()).setExcuse(false);
                }
            });

            excuseCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle excuse checkbox state change
                    // Ensure only one checkbox is checked
                    attendCheckBox.setChecked(false);
                    absentCheckBox.setChecked(false);
                    list.get(getAdapterPosition()).setExcuse(true);
                    list.get(getAdapterPosition()).setAttend(false);
                    list.get(getAdapterPosition()).setAbsent(false);
                }
            });

             */
        }

    }

}