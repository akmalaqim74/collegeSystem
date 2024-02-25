package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    ImageButton login;
    EditText tempEmail,tempPassword;
    String email,password;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if registerLecturer is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(login.this,homePage.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        loginUser();

    }

    public void loginUser(){
        tempEmail = findViewById(R.id.getEmailStudent);
        tempPassword = findViewById(R.id.getPasswordStudent);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = tempEmail.getText().toString();
                password = tempPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(login.this, "succeful Login",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(login.this,homePage.class);
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the registerLecturer.

                                        Toast.makeText(login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }
}
