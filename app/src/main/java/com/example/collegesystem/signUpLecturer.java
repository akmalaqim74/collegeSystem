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

public class signUpLecturer extends AppCompatActivity {
    String name, lecturer_ID, email,password, department,role;
    EditText tempName, tempLecturerID, tempEmail,tempPassword;
    Spinner tempDepartment ,tempRole;
    ImageButton signUpLecturer;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplecutrer);
        mAuth = FirebaseAuth.getInstance();

        dropBoxItem();//method  to add item in dropBox
        dropBoxRoleItem();
        backButtonFunction();//method for back to the main page
        signUpButtonFunction();//method for sign up




    }


    public void dropBoxItem(){
        //============ DROP BOX ITEM==========
        tempDepartment = findViewById(R.id.dropBox);
        // Define the options for the drop-down menu in an array
        String[] options = { "FICT","Halal Management"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        tempDepartment.setAdapter(adapter);
        //========= END OF DROP BOX ITEM =========

    }
    public void dropBoxRoleItem(){
        //============ DROP BOX ITEM==========
        tempRole = findViewById(R.id.dropBoxRole);
        // Define the options for the drop-down menu in an array
        String[] options = {"Staff","Admin","Lecturer","Student"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        tempRole.setAdapter(adapter);
        //========= END OF DROP BOX ITEM =========

    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        ImageButton back = findViewById(R.id.backButtonSignUp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(signUpLecturer.this,homePage.class);
                startActivity(intent);
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void signUpButtonFunction(){
        tempName = findViewById(R.id.getName);
        tempLecturerID = findViewById(R.id.getLecturerID);
        tempPassword = findViewById(R.id.getPasswordStudent);
        tempEmail = findViewById(R.id.getEmailStudent);
        signUpLecturer = findViewById(R.id.signUp);

        signUpLecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //========== GET VALUE FROM USER==========
                name = tempName.getText().toString();
                lecturer_ID = tempLecturerID.getText().toString().toUpperCase();
                email = tempEmail.getText().toString();
                password = tempPassword.getText().toString();
                department = tempDepartment.getSelectedItem().toString();
                role = tempRole.getSelectedItem().toString();

                //==========END OF GET VALUE FROM USER==========

                if(TextUtils.isEmpty(lecturer_ID)||TextUtils.isEmpty(password)||TextUtils.isEmpty(email)){
                    Toast.makeText(signUpLecturer.this,"Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    //========= linked to fireBase==========
                    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference departmentRef = rootRef.getReference("Department");
                    // Check if the course node exists
                    DatabaseReference currentDepartmentRef = departmentRef.child(department);
                    DatabaseReference lecturerIdRef = currentDepartmentRef.child("Lecturer");
                    /*Explain:  Basically every matricNumber will be store inside a specific course node
                    there will be 3 nodes,
                    parent: Course
                    child: name of Course
                    grandchild: matric NUmber
                    son of grandchild: the JSON data
                     */




                    // Check if matricNo already exists
                    lecturerIdRef.child(lecturer_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // The matricNo already exists, handle the situation (e.g., show an error message)
                                Toast.makeText(signUpLecturer.this, "Lecturer ID already exists.", Toast.LENGTH_SHORT).show();
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
                                                    registerLecturer newRegisterLecturer = new registerLecturer(name, lecturer_ID, email, department,UID,role);
                                                    // STORE THE USER DATA INSIDE THE NODE OF MATRIC NUMBER
                                                    lecturerIdRef.child(lecturer_ID).setValue(newRegisterLecturer);
                                                    DatabaseReference userRef = rootRef.getReference("Users");
                                                    userRef.child(UID).setValue(newRegisterLecturer);
                                                    Toast.makeText(signUpLecturer.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();
                                                    tempName.setText("");
                                                    tempLecturerID.setText("");
                                                    tempEmail.setText("");
                                                    tempPassword.setText("");
                                                } else {
                                                    // If sign up fails, display a message to the registerLecturer.
                                                    Toast.makeText(signUpLecturer.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle the error
                            Toast.makeText(signUpLecturer.this, "Database error.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(signUpLecturer.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseError", "Database error", databaseError.toException());
                        }
                    });

                }



            }
        });


    }
}
