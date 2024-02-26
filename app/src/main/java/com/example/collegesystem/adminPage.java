package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);
        addSubject();
        addLecturerMethod();
        addStudentMethod();
        addStudentToSubject();
        backButtonMethod();
        addItem();
        checkBorrowItem();
    }
    public void backButtonMethod(){
        ImageButton addStudentButton = findViewById(R.id.backButtonAdmin);


        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminPage.this, homePage.class);
                startActivity(intent);
                finish();

            }
        });

    }
    public void checkBorrowItem(){
        Button checkBorrowItem = findViewById(R.id.checkBorrowItem);
        checkBorrowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPage.this,itemBorrowed.class);
                startActivity(intent);
            }
        });
    }
    public void addItem(){
        ImageButton addItem = findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPage.this,registerItem.class);
                startActivity(intent);
            }
        });
    }
    public void addSubject(){
        ImageButton addSubject = findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPage.this,subjectRegistration.class);
                startActivity(intent);
            }
        });
    }
    public void addLecturerMethod(){
        ImageButton addLecturerButton = findViewById(R.id.addLecturer);
        //addLecturerButton.setVisibility(View.INVISIBLE);

        /*if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference()
                    .child("Users")
                    .child(uid);

        }*/


        addLecturerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminPage.this, signUpLecturer.class);
                startActivity(intent);

            }
        });

    }
    public void addStudentMethod(){
        ImageButton addStudentButton = findViewById(R.id.addStudent);


        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminPage.this, signUpStudent.class);
                startActivity(intent);

            }
        });

    }
    public void addStudentToSubject(){
        ImageButton addStudentSubject = findViewById(R.id.addStudentToSubject);
        addStudentSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminPage.this,addStudentToSubject.class);
                startActivity(intent);
            }
        });
    }
}
