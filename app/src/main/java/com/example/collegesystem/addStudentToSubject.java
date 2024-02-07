package com.example.collegesystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addStudentToSubject extends AppCompatActivity {
    AutoCompleteTextView subjectSpinner,studentSpinner;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_to_subject);
        dropBoxSubject();
        dropBoxStudent();
        addButton();
    }
    private void dropBoxSubject(){

        DatabaseReference subjectRef = rootRef
                .getReference()
                .child("Subject");
        subjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int subjectCount =(int) snapshot.getChildrenCount();
                String subjectCourseCode = null;
                String[] allSubject = new String[subjectCount];
                ArrayList<String> testSubject = new ArrayList<>();
                int i = 0;

                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    subjectCourseCode = childSnapshot.getKey();
                    String courseCode = snapshot
                            .child(subjectCourseCode)
                            .child("courseCode")
                            .getValue(String.class);
                    String subjectName = snapshot
                            .child(subjectCourseCode)
                            .child("subjectName")
                            .getValue(String.class);
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


    private void dropBoxFunction(ArrayList<String> tempSubjectSpinner){
        //============ DROP BOX ITEM==========
        subjectSpinner = findViewById(R.id.subjectSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style,tempSubjectSpinner);
        // Set the adapter to the Spinner
        subjectSpinner.setAdapter(adapter);
        subjectSpinner.setThreshold(0);
        //========= END OF DROP BOX ITEM =========
    }
    //DropBox for student Name
    private void dropBoxStudent(){

        DatabaseReference subjectRef = rootRef
                .getReference()
                .child("Users");
        subjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int studentCount =(int) snapshot.getChildrenCount();
                String UID = null;
                String[] allStudent = new String[studentCount];
                ArrayList<String> tempStudent = new ArrayList<>();
                int i = 0;
                try{
                    for(DataSnapshot childSnapshot:snapshot.getChildren()){
                        UID = childSnapshot.getKey();
                        String userRole = snapshot
                                .child(UID)
                                .child("role")
                                .getValue(String.class);
                        if(userRole.equalsIgnoreCase("Student")){
                            //======get student matric No
                            String matricNo = snapshot
                                    .child(UID)
                                    .child("matricNo")
                                    .getValue(String.class);

                            //======get student Name
                            String studentName = snapshot
                                    .child(UID)
                                    .child("name")
                                    .getValue(String.class);

                            allStudent[i] = matricNo + " " + studentName;
                            tempStudent.add(matricNo+ " " + studentName);

                        }
                    }
                    dropBoxStudentFunction(tempStudent);

                }
                catch(Exception e){
                    Toast.makeText(addStudentToSubject.this, "Something went Wrong", Toast.LENGTH_SHORT).show();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void dropBoxStudentFunction(ArrayList<String> tempStudentSpinner){
        //============ DROP BOX ITEM==========
        studentSpinner = findViewById(R.id.studentSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style,tempStudentSpinner);
        // Set the adapter to the Spinner
        studentSpinner.setAdapter(adapter);
        studentSpinner.setThreshold(0);
        //========= END OF DROP BOX ITEM =========
    }
    private void addButton(){
        TextView add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = subjectSpinner.getText().toString();
                String studentMatric = studentSpinner.getText().toString();
                // Find the index of the first space
                int indexOfSpaceSubject = subject.indexOf(' ');
                int indexOfSpaceStudent = studentMatric.indexOf(' ');

                // Extract the substring before the first space
                String courseCode =subject.substring(0, indexOfSpaceSubject);
                String matricNo = studentMatric.substring(0,indexOfSpaceStudent);

                try{
                    DatabaseReference subjectRef = rootRef
                            .getReference()
                            .child("Subject")
                            .child(courseCode)
                            .child("Student")
                            .child(matricNo);
                    //setName
                    subjectRef.setValue(studentMatric.substring(indexOfSpaceStudent))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(addStudentToSubject.this, "Student Added to the Subject", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(addStudentToSubject.this, "Failed to add student to Subject", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }catch(Exception e){

                }
            }
        });

    }
}
