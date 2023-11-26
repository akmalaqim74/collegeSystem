package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class homePage extends AppCompatActivity {
    ImageButton addSubject,profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        addSubjectButton();
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
        profilePicture = findViewById(R.id.profilePic);
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
}
