package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/* I think it because i put it under note id thats why it cant be */

public class toDoList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList <PrivatetoDoList> list = new ArrayList<>();
    String department,lecturerId,uid;
    toDoListAdapter listAdapter;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);
        backButtonFunction();
        addToDoList();
        display();


    }
    public void display(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(toDoList.this));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        listAdapter = new toDoListAdapter(toDoList.this,list);
        recyclerView.setAdapter(listAdapter);

        if (currentUser != null) {
            uid = currentUser.getUid();

            DatabaseReference userRef = rootRef.getReference().child("Users")
                    .child(uid);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        lecturerId = dataSnapshot
                                .child("lecturer_ID")
                                .getValue(String.class);
                        department = dataSnapshot
                                .child("department")
                                .getValue(String.class);
                        DatabaseReference toDoRef = rootRef.getReference()
                                .child("Department")
                                .child(department)
                                .child("Lecturer")
                                .child(lecturerId)
                                .child("toDoList");


                        // Now you can use the retrieved values as needed for the current registerLecturer
                        toDoRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("FirebaseData", "onDataChange triggered");
                                list.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                                    String date = dataSnapshot.child("date").getValue(String.class);
                                    String detail = dataSnapshot.child("detail").getValue(String.class);
                                    String title = dataSnapshot.child("title").getValue(String.class);
                                    if (date != null && detail != null && title != null) {
                                        PrivatetoDoList toDoItem = new PrivatetoDoList(title, detail, date);
                                        list.add(toDoItem);
                                        Log.d("FirebaseData", "Title: " + toDoItem.getTitle() + ", Detail: " + toDoItem.getDetail() + ", Date: " + toDoItem.getDate());
                                    }
                                }
                                listAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("FirebaseData", "Error retrieving data from Firebase: " + databaseError.getMessage());
                    // Handle errors
                }
            });
        }
    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(toDoList.this,homePage.class);
                startActivity(intent);
                finish();
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void addToDoList(){
        ImageButton addtoDoList = findViewById(R.id.addTodOList);
        addtoDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define the intent to navigate to the menu page
                Intent intent = new Intent(toDoList.this,addToDoList.class);
                //start the homepage activity
                startActivity(intent);
            }
        });
    }
    public void deleteItemFromDatabase(PrivatetoDoList itemToDelete) {
        DatabaseReference toDoRef = rootRef.getReference()
                .child("Department")
                .child(department)
                .child("Lecturer")
                .child(lecturerId)
                .child("toDoList");

        toDoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PrivatetoDoList toDoItem = snapshot.getValue(PrivatetoDoList.class);
                    if (toDoItem != null &&
                            toDoItem.getTitle().equals(itemToDelete.getTitle()) &&
                            toDoItem.getDetail().equals(itemToDelete.getDetail()) &&
                            toDoItem.getDate().equals(itemToDelete.getDate())
                    ){
                        snapshot.getRef().removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Item deleted successfully
                                        // You can add any UI update or notification here
                                        Toast.makeText(toDoList.this, "Item deleted", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to delete item
                                        // Handle the error accordingly
                                        Toast.makeText(toDoList.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(toDoList.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void updateToDo(PrivatetoDoList itemToUpdate){
        DatabaseReference toDoRef = rootRef.getReference()
                .child("Department")
                .child(department)
                .child("Lecturer")
                .child(lecturerId)
                .child("toDoList");

        toDoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PrivatetoDoList toDoItem = snapshot.getValue(PrivatetoDoList.class);
                    if (toDoItem != null &&
                            toDoItem.getTitle().equals(itemToUpdate.getTitle()) &&
                            toDoItem.getDetail().equals(itemToUpdate.getDetail()) &&
                            toDoItem.getDate().equals(itemToUpdate.getDate())
                    ){
                        String toDoID = snapshot.getKey();
                        Intent intent = new Intent(toDoList.this, updateToDo.class);
                        intent.putExtra("toDoIDKey", toDoID);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(toDoList.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
