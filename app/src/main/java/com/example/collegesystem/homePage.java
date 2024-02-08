package com.example.collegesystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
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
    ImageButton addSubject,profilePicture;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        addSubject();
        addLecturerMethod();
        addStudentMethod();
        toDoList();
        logOut();
        studentAttendance();
        setName();
        addStudentToSubject();
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

    public void addLecturerMethod(){
        ImageButton addLecturerButton = findViewById(R.id.addLecturer);
        //addLecturerButton.setVisibility(View.INVISIBLE);

        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference()
                    .child("Users")
                    .child(uid);

            /*userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String Id = dataSnapshot.child("lecturer_ID").getValue(String.class);
                        String extractedId = Id.substring(0, 5);
                        if(extractedId.equals("ADMIN")){
                            addLecturerButton.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });*/
        }


        addLecturerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.this, signUpLecturer.class);
                startActivity(intent);

            }
        });

    }
    public void addStudentMethod(){
        ImageButton addStudentButton = findViewById(R.id.addStudent);

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
                        /*String Id = dataSnapshot.child("lecturer_ID").getValue(String.class);
                        String extractedId = Id.substring(0, 5);
                        Log.d("ID",extractedId);

                        // Now you can use the retrieved values as needed for the current registerLecturer
                        addStudentButton.setVisibility(View.INVISIBLE);
                        if(extractedId.equals("ADMIN")){
                            addStudentButton.setVisibility(View.VISIBLE);
                        }*/

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }


        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.this, signUpStudent.class);
                startActivity(intent);
                finish();

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
    public void addSubject(){
        ImageButton addSubject = findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this,subjectRegistration.class);
                startActivity(intent);
            }
        });
    }
    public void addStudentToSubject(){
        ImageButton addStudentSubject = findViewById(R.id.addStudentToSubject);
        addStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homePage.this,addStudentToSubject.class);
                startActivity(intent);
            }
        });
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
}
