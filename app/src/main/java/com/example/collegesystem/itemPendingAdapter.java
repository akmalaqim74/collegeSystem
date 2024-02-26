package com.example.collegesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itemPendingAdapter extends RecyclerView.Adapter<itemPendingAdapter.myViewHolder>{
    Context context;
    ArrayList<item> list;

    public itemPendingAdapter(Context context, ArrayList<item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public itemPendingAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_cardview, parent, false);
        return new itemPendingAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemPendingAdapter.myViewHolder holder, int position) {
        item currentItem = list.get(position);
        holder.itemName.setText("Item Name: " + currentItem.getItemName());
        holder.time.setText(currentItem.getBorrowDate() + " until " + currentItem.getReturnDate());
        holder.borrowerName.setText("Borrower Name: "+ currentItem.getBorrowName());
        holder.returned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    item itemToDelete = list.get(adapterPosition);
                    // Call a method in your activity to delete the item
                    ((itemPending)context).deleteItemFromDatabase(itemToDelete);
                }
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    item itemToConfirm = list.get(adapterPosition);
                    // Call a method in your activity to delete the item
                    ((itemPending)context).confirmStatusFromDatabase(itemToConfirm);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,time,borrowerName;
        ImageButton returned,confirm;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            borrowerName = itemView.findViewById(R.id.borrowerName);
            time = itemView.findViewById(R.id.date);
            returned = itemView.findViewById(R.id.delete);
            confirm = itemView.findViewById(R.id.confirm);
        }
    }

}
