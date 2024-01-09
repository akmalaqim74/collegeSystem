package com.example.collegesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class toDoListAdapter extends RecyclerView.Adapter<toDoListAdapter.myViewHolder>{
    Context context;
    ArrayList<PrivatetoDoList> list;

    public toDoListAdapter(Context context, ArrayList<PrivatetoDoList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        PrivatetoDoList currentItem = list.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.detail.setText(currentItem.getDetail());
        holder.date.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView title,detail,date;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleToDo);
            detail = itemView.findViewById(R.id.detailToDo);
            date = itemView.findViewById(R.id.dateToDo);

        }
    }

}
