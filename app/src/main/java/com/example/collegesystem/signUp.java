package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUp extends AppCompatActivity {
    String name,matricNo, email,password,course;
    EditText tempName,tempMatricNo, tempEmail,tempPassword;
    Spinner tempCourse;
    ImageButton signUp;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(signUp.this, homePage.class);
            startActivity(intent);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();

        dropBoxItem();//method  to add item in dropBox
        backButtonFunction();//method for back to the main page
        signUpButtonFunction();//method for sign up




    }


    public void dropBoxItem(){
        //============ DROP BOX ITEM==========
        tempCourse = findViewById(R.id.dropBox);
        // Define the options for the drop-down menu in an array
        String[] options = {"Course","Diploma Computer Science", "Diploma Information Technology", "Diploma Halal Management"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        tempCourse.setAdapter(adapter);
        //========= END OF DROP BOX ITEM =========

    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        ImageButton back = findViewById(R.id.backButtonSignUp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(signUp.this,mainActivity.class);
                startActivity(intent);
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void signUpButtonFunction(){
        tempName = findViewById(R.id.getName);
        tempMatricNo = findViewById(R.id.getMatricNo);
        tempPassword = findViewById(R.id.getPassword);
        tempEmail = findViewById(R.id.getEmail);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //========== GET VALUE FROM USER==========
                name = tempName.getText().toString();
                matricNo = tempMatricNo.getText().toString().toUpperCase();
                email = tempEmail.getText().toString();
                password = tempPassword.getText().toString();
                course = tempCourse.getSelectedItem().toString();
                //==========END OF GET VALUE FROM USER==========

                if(TextUtils.isEmpty(matricNo)||TextUtils.isEmpty(password)||TextUtils.isEmpty(email)){
                    Toast.makeText(signUp.this,"Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    //========= linked to fireBase==========
                    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference courseRef = rootRef.getReference("Course");
                    // Check if the course node exists
                    DatabaseReference currentCourseRef = courseRef.child(course);
                    DatabaseReference matricNumbersRef = currentCourseRef.child("Matric_Numbers");
                    /*Explain:  Basically every matricNumber will be store inside a specific course node
                    there will be 3 nodes,
                    parent: Course
                    child: name of Course
                    grandchild: matric NUmber
                    son of grandchild: the JSON data
                     */




                    // Check if matricNo already exists
                    matricNumbersRef.child(matricNo).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // The matricNo already exists, handle the situation (e.g., show an error message)
                                Toast.makeText(signUp.this, "Matric Number already exists.", Toast.LENGTH_SHORT).show();
                            } else {
                                mAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    //==========GET USER UID==========
                                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                                    String UID = firebaseUser.getUid();

                                                    // Create a User object
                                                    user newUser = new user(name, matricNo, email, course,UID);
                                                    // STORE THE USER DATA INSIDE THE NODE OF MATRIC NUMBER
                                                    matricNumbersRef.child(matricNo).setValue(newUser);
                                                    Toast.makeText(signUp.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(signUp.this, login.class);
                                                    startActivity(intent);

                                                } else {
                                                    // If sign up fails, display a message to the user.
                                                    Toast.makeText(signUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle the error
                            Toast.makeText(signUp.this, "Database error.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(signUp.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseError", "Database error", databaseError.toException());
                        }
                    });

                }



            }
        });


    }
}
