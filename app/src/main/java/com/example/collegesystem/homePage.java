package com.example.collegesystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homePage extends AppCompatActivity {
    ImageButton profilePicture;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        toDoList();
        logOut();
        studentAttendance();
        setName();
        viewSubject();
        adminButtonFunction();
        checkAttendance();
        borrowItem();
    }
    public void borrowItem(){
        Button borrow = findViewById(R.id.borrowItem);
        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this, ItemCategory.class);
                startActivity(intent);

            }
        });
    }

    public void viewSubject(){
        profilePicture = findViewById(R.id.temp);

        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference()
                    .child("Users")
                    .child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        if(role.equals("Lecturer")){
                            profilePicture.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Define the intent to navigate to the menu page
                                    Intent intent = new Intent(homePage.this, lecturerSubject.class);
                                    //start the homepage activity
                                    startActivity(intent);

                                }
                            });


                        }else{
                            profilePicture.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(homePage.this, "Only Available for Lecturer", Toast.LENGTH_SHORT).show();


                                }
                            });

                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
    public void toDoList(){
        ImageButton toDoList = findViewById(R.id.toDoList);
        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference()
                    .child("Users")
                    .child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        if(role.equals("Lecturer")){
                            toDoList.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(homePage.this, toDoList.class);

                                    startActivity(intent);
                                }
                            });


                        }else{
                            toDoList.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(homePage.this, "Only Available for Lecturer", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }



    private FirebaseUser getCurrentUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }
    public void logOut(){
        ImageButton logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(homePage.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void studentAttendance(){
        ImageButton takingAttendance = findViewById(R.id.attendance);
        takingAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.this,chooseSubject.class);
                startActivity(intent);

            }
        });

    }
    public void adminButtonFunction() {
        Button admin = findViewById(R.id.adminButton);
        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference()
                    .child("Users")
                    .child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String role = dataSnapshot.child("role").getValue(String.class);
                        if (role.equals("Lecturer")) {
                            admin.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(homePage.this, adminPage.class);

                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });

        }
    }


    public void setName(){

        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference()
                    .child("Users")
                    .child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class);

                        TextView TVName = findViewById(R.id.Name);
                        TVName.setText(name);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }
    public void checkAttendance(){
        ImageButton check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this,checkAttendance.class);
                startActivity(intent);
            }
        });
    }
}
