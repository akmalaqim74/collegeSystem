package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chooseSubject extends AppCompatActivity {
    AutoCompleteTextView subjectSpinner;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    ArrayList<String> testSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosesubject);
        dropBoxSubject();
        nextButton();
        exitButton();
    }
    private void exitButton(){
        ImageButton exit = findViewById(R.id.Exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseSubject.this, homePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void dropBoxSubject(){
        String UID = currentUser.getUid();

        DatabaseReference userRef = rootRef
                .getReference();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String department = snapshot
                        .child("Users")
                        .child(UID)
                        .child("department").getValue(String.class);
                String lecturerId = snapshot
                        .child("Users")
                        .child(UID)
                        .child("lecturer_ID")
                        .getValue(String.class);
                try{

                    if(department!= null && lecturerId!=null){
                        DatabaseReference lecturerRef = userRef
                                .child("Department")
                                .child(department)
                                .child("Lecturer")
                                .child(lecturerId)
                                .child("subject");
                        lecturerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int subjectCount =(int) dataSnapshot.getChildrenCount();
                                String[] allSubject = new String[subjectCount];
                                testSubject = new ArrayList<>();
                                int i = 0;
                                for(DataSnapshot tempSnapShot: dataSnapshot.getChildren()){
                                    String courseCode = tempSnapShot.getKey();
                                    String subjectName = dataSnapshot
                                            .child(courseCode)
                                            .child("subjectName")
                                            .getValue(String.class).trim();
                                    allSubject[i] = courseCode + " " + subjectName;
                                    testSubject.add(courseCode + " " + subjectName);


                                }
                                dropBoxFunction(testSubject);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }catch(Exception e){}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void dropBoxFunction(ArrayList<String> tempSubjectSpinner){
            //============ DROP BOX ITEM==========
            subjectSpinner = findViewById(R.id.subjectSpinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style,tempSubjectSpinner);
            // Set the adapter to the Spinner
            subjectSpinner.setAdapter(adapter);
            subjectSpinner.setThreshold(0);
            //========= END OF DROP BOX ITEM =========
    }
    public void nextButton(){
        TextView nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectSpinner.getText().toString().trim();
                if (!subject.isEmpty()) {
                    boolean exist = false;
                    for(int i = 0;i<testSubject.size();i++){
                        if(subject.equalsIgnoreCase(testSubject.get(i))){
                            Intent intent = new Intent(chooseSubject.this, studentAttendance.class);
                            intent.putExtra("subject", subject);
                            startActivity(intent);
                            exist = true;
                        }
                    }
                    if(exist == false){
                        Toast.makeText(chooseSubject.this, "Subject Not Exist", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(chooseSubject.this, "Please enter a subject", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
