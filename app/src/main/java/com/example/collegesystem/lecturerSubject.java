package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class lecturerSubject extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<subject> list = new ArrayList<>();
    lecturerSubjectAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturer_subject);
        display();
        backButtonFunction();


    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(lecturerSubject.this,homePage.class);
                startActivity(intent);
                finish();
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void display(){
        recyclerView = findViewById(R.id.lecturerSubject);
        recyclerView.setLayoutManager(new LinearLayoutManager(lecturerSubject.this));
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        listAdapter = new lecturerSubjectAdapter(lecturerSubject.this,list);
        recyclerView.setAdapter(listAdapter);

        if (currentUser != null) {
            String uid = currentUser.getUid();

            FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference userRef = rootRef.getReference().child("Users")
                    .child(uid);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String lecturerId = dataSnapshot
                                .child("lecturer_ID")
                                .getValue(String.class);
                        String department = dataSnapshot
                                .child("department")
                                .getValue(String.class);
                        DatabaseReference toDoRef = rootRef.getReference()
                                .child("Department")
                                .child(department)
                                .child("Lecturer")
                                .child(lecturerId)
                                .child("subject");


                        // Now you can use the retrieved values as needed for the current registerLecturer
                        toDoRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("FirebaseData", "onDataChange triggered");
                                list.clear();
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                                    String courseCode = dataSnapshot.child("courseCode").getValue(String.class);
                                    String subjectName = dataSnapshot.child("subjectName").getValue(String.class);
                                    String section = dataSnapshot.child("section").getValue(String.class);
                                    if (courseCode != null && subjectName != null && section != null) {
                                        subject lecturerSubject = new subject(subjectName, courseCode,section);
                                        list.add(lecturerSubject);}
                                }
                                listAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("FirebaseData", "Error retrieving data from Firebase: " + databaseError.getMessage());
                    // Handle errors
                }
            });
        }
    }

}
