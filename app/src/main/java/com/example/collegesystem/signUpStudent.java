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

public class signUpStudent extends AppCompatActivity {
    String nameStudent, matricNo, email,IcNUmber, password, department,role;
    EditText tempNameStudent, tempMatricNo, tempIcNumber, tempEmail,tempPassword;
    Spinner tempDepartment,tempRole;
    ImageButton signUpStudent;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupstudent);
        mAuth = FirebaseAuth.getInstance();

        dropBoxItem();//method  to add item in dropBox
        backButtonFunction();//method for back to the main page
        signUpButtonFunction();//method for sign up
        dropBoxRoleItem();




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


    public void dropBoxItem(){
        //============ DROP BOX ITEM==========
        tempDepartment = findViewById(R.id.dropBox);
        // Define the options for the drop-down menu in an array
        String[] options = { "FICT","BUSINESS AND MANAGEMENT","SOCIAL SCIENCE"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        tempDepartment.setAdapter(adapter);
        //========= END OF DROP BOX ITEM =========

    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        ImageButton back = findViewById(R.id.backButtonSignUpStudent);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(signUpStudent.this,adminPage.class);
                startActivity(intent);
                finish();
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void signUpButtonFunction(){
        tempNameStudent = findViewById(R.id.getNameStudent);
        tempMatricNo = findViewById(R.id.getMatricNumber);
        tempPassword = findViewById(R.id.getPasswordStudent);
        tempEmail = findViewById(R.id.getEmailStudent);
        tempIcNumber = findViewById(R.id.getICNumberStudent);
        signUpStudent = findViewById(R.id.signUpStudent);
        signUpStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //========== GET VALUE FROM USER==========
                nameStudent = tempNameStudent.getText().toString();
                matricNo = tempMatricNo.getText().toString().toUpperCase();
                IcNUmber = tempIcNumber.getText().toString();
                email = tempEmail.getText().toString();
                password = tempPassword.getText().toString();
                department = tempDepartment.getSelectedItem().toString();
                role = tempRole.getSelectedItem().toString();
                //==========END OF GET VALUE FROM USER==========

                if(!TextUtils.isEmpty(matricNo) && !TextUtils.isEmpty(IcNUmber) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(department) && !TextUtils.isEmpty(role)) {
                    //========= linked to fireBase==========
                    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference courseRef = rootRef.getReference("Department");
                    // Check if the course node exists
                    DatabaseReference currentCourseRef = courseRef.child(department).child("Student");
                    DatabaseReference matricNumbersRef = currentCourseRef.child("Matric Number");
                    /*Explain:  Basically every matricNumber will be store inside a specific course node
                    there will be 3 nodes,
                    parent: Course
                    child: nameStudent of Course
                    grandchild: matric NUmber
                    son of grandchild: the JSON data
                     */




                    // Check if matricNo already exists
                    matricNumbersRef.child(matricNo).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // The matricNo already exists, handle the situation (e.g., show an error message)
                                Toast.makeText(signUpStudent.this, "Matric Number already exists.", Toast.LENGTH_SHORT).show();
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
                                                    //registerStudent(String name, String userID, String department, String email, String icNumber, String matricNo)
                                                    registerStudent newRegisterStudent = new registerStudent(nameStudent,UID,department,email,IcNUmber, matricNo,role);
                                                    // STORE THE USER DATA INSIDE THE NODE OF MATRIC NUMBER
                                                    matricNumbersRef.child(matricNo).setValue(newRegisterStudent);

                                                    DatabaseReference userRef = rootRef.getReference("Users");
                                                    userRef.child(UID).setValue(newRegisterStudent);
                                                    Toast.makeText(signUpStudent.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();


                                                    tempNameStudent.setText("");
                                                    tempMatricNo.setText("");
                                                    tempIcNumber.setText("");
                                                    tempEmail.setText("");
                                                    tempPassword.setText("");

                                                } else {
                                                    // If sign up fails, display a message to the registerLecturer.
                                                    Toast.makeText(signUpStudent.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle the error
                            Toast.makeText(signUpStudent.this, "Database error.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(signUpStudent.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseError", "Database error", databaseError.toException());
                        }
                    });
                }
                else{
                    Toast.makeText(signUpStudent.this,"Please enter all fields", Toast.LENGTH_SHORT).show();



                }



            }
        });


    }
}
