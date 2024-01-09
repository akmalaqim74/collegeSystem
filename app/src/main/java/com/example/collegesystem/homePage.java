package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
    ImageButton addSubject,profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        addSubjectButton();
        addLecturerMethod();
        toDoList();
        logOut();
    }

    public void addSubjectButton(){
        addSubject = findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define the intent to navigate to the menu page
                Intent intent = new Intent(homePage.this, addSubject.class);
                //start the homepage activity
                startActivity(intent);
            }
        });
    }
    public void viewProfilePic(){
        profilePicture = findViewById(R.id.exit);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define the intent to navigate to the menu page
                //Intent intent = new Intent(homePage.this, addSubject.class);
                //start the homepage activity
                //startActivity(intent);
            }
        });
    }
    public void toDoList(){
        ImageButton toDoList = findViewById(R.id.toDoList);
        toDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.this, toDoList.class);

                startActivity(intent);
            }
        });
    }

    public void addLecturerMethod(){
        ImageButton addLecturerButton = findViewById(R.id.addLecturer);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference().child("Users").child(uid);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String Id = dataSnapshot.child("lecturer_ID").getValue(String.class);
                        String extractedId = Id.substring(0, 4);
                        // Now you can use the retrieved values as needed for the current user
                        addLecturerButton.setVisibility(View.INVISIBLE);
                        if(extractedId.equals("ADMIN")){
                            addLecturerButton.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }


        addLecturerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.this, signUpLecturer.class);
                startActivity(intent);

            }
        });

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
}
