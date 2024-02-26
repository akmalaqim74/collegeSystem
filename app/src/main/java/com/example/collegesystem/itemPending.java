package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class itemPending extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<item> list = new ArrayList<>();
    itemPendingAdapter listAdapter;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pending);
        display();
        backButtonMethod();


    }
    public void backButtonMethod(){
        ImageButton addStudentButton = findViewById(R.id.backButton);


        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemPending.this, itemBorrowed.class);
                startActivity(intent);
                finish();

            }
        });

    }
    public void display(){
        recyclerView = findViewById(R.id.itemPending);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemPending.this));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        listAdapter = new itemPendingAdapter(itemPending.this,list);
        recyclerView.setAdapter(listAdapter);
        String[] options = {"Computers and Accessories","Electronics","Audiovisual Equipment","Others"};
        list.clear();

        for(int i = 0; i < options.length;i++){
            DatabaseReference itemRef = rootRef
                    .getReference()
                    .child("Borrowed Item")
                    .child(options[i]);
            itemRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot tempSnapShot: snapshot.getChildren()){
                            String itemName = tempSnapShot.getKey();
                            String status = snapshot.child(itemName).child("status").getValue(String.class);
                            if(status != null && status.equalsIgnoreCase("pending")){
                                item borrowedItem = snapshot.child(itemName).getValue(item.class);
                                list.add(borrowedItem);
                            }

                        }
                        listAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
    public void deleteItemFromDatabase(item itemToDelete) {
        DatabaseReference itemPendingRef = rootRef.getReference()
                .child("Borrowed Item")
                .child(itemToDelete.getCategory());

        itemPendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    item returnedItem = snapshot.getValue(item.class);
                    String category = returnedItem.getCategory();
                    String itemName = returnedItem.getItemName();
                    String location = returnedItem.getLocation();
                    String status = "available";


                    if (returnedItem != null &&
                            returnedItem.getItemName().equals(itemToDelete.getItemName()) &&
                            returnedItem.getBorrowName().equals(itemToDelete.getBorrowName()) &&
                            returnedItem.getStatus().equals(itemToDelete.getStatus())
                    ){
                        snapshot.getRef().removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        item updateStatus = new item(itemName,location,status,category);

                                        itemPendingRef.child(itemName).setValue(updateStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(itemPending.this, "The borrowing request has been denied.", Toast.LENGTH_SHORT).show();
                                                    listAdapter.notifyDataSetChanged();
                                                    recreate();
                                                }
                                            }
                                        });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to delete item
                                        // Handle the error accordingly
                                        Toast.makeText(itemPending.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(itemPending.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void confirmStatusFromDatabase(item itemToDelete) {
        DatabaseReference confirmRef = rootRef.getReference()
                .child("Borrowed Item")
                .child(itemToDelete.getCategory());

        confirmRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    item returnedItem = snapshot.getValue(item.class);
                    String category = returnedItem.getCategory();
                    String itemName = returnedItem.getItemName();
                    String location = returnedItem.getLocation();
                    String status = "borrowed";


                    if (returnedItem != null &&
                            returnedItem.getItemName().equals(itemToDelete.getItemName()) &&
                            returnedItem.getBorrowName().equals(itemToDelete.getBorrowName()) &&
                            returnedItem.getStatus().equals(itemToDelete.getStatus())
                    ){
                        confirmRef.child(itemName).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(itemPending.this, "The item has been successfully borrowed.", Toast.LENGTH_SHORT).show();
                                    recreate();
                                    listAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(itemPending.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
