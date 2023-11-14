package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class mainActivity extends AppCompatActivity {

    Button login,signUp;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(mainActivity.this, homePage.class);
            startActivity(intent);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        loginButtonFunction();
        signUpButtonFunction();



    }
    public void loginButtonFunction(){
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define the intent to navigate to the menu page
                Intent intent = new Intent(mainActivity.this, login.class);
                //start the homepage activity
                startActivity(intent);
            }
        });
    }
    public void signUpButtonFunction(){
        signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define the intent to navigate to the menu page
                Intent intent = new Intent(mainActivity.this, signUp.class);
                //start the homepage activity
                startActivity(intent);
            }
        });
    }
}
