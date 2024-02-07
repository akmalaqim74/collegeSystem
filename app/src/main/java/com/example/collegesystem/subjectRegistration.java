package com.example.collegesystem;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class subjectRegistration extends AppCompatActivity {
    String listLecturerId[],selectedTimeStart,selectedTimeEnded,subjectName,courseCode,section,venue,lecturerId = null;

    EditText tempSubjectName,tempCourseCode,tempSection,tempVenue;
    TextView tempClassStart,tempClassEnd;
    AutoCompleteTextView tempLecturerId;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    FirebaseUser currentUser = mAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjectregistration);
        dropBoxLecturerId();
        classStartFunction();
        classEndFunction();
        addSubjectButton();


}

    public void dropBoxLecturerId(){

        DatabaseReference userRef = rootRef.getReference()
                .child("Users");

        // Now you can use the retrieved values as needed for the current registerLecturer
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int dataSize = (int) snapshot.getChildrenCount();
                ArrayList<String> listLecturerId = new ArrayList<>();
                int i = 0;

                Log.d("FirebaseData", "onDataChange triggered");
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String UID = userSnapshot.getKey();
                    if (UID!= null) {
                        Log.d("UID", UID);
                        Log.d("UID", " " + dataSize + "HEYYYYOOOOOO");
                    } else {
                        Log.d("UID", "UID is null");
                    }
                    String role = null;
                    role = snapshot
                            .child(UID)
                            .child("role")
                            .getValue(String.class);
                    Log.d("ROLE", role + " INI ROLE");
                    if (role != null && role.equalsIgnoreCase("Lecturer")){
                        String lecturerId = snapshot
                                .child(UID)
                                .child("lecturer_ID")
                                .getValue(String.class);
                        listLecturerId.add(lecturerId);
                        Log.d("UID", lecturerId + " INI LECTURER ID");
                    }

                    i++;
                }
                Log.d("LecturerIds", "Contents of listLecturerId array:");
                for (String lecturerId : listLecturerId) {
                    if (lecturerId != null) {
                        Log.d("LecturerIds", lecturerId);
                    } else {
                        Log.d("LecturerIds", "Lecturer ID is null");
                    }
                }
                dropBoxFunction(listLecturerId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void dropBoxFunction(ArrayList<String> tempListLecturerId){
        int size = tempListLecturerId.size();
        String[] lecturerID = new String[size];
        if(tempListLecturerId!= null){
            for(int i = 0;i<size;i++){
                lecturerID[i] = tempListLecturerId.get(i);
            }
            //============ DROP BOX ITEM==========
            tempLecturerId = findViewById(R.id.getLecturerID);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style,lecturerID);
            // Set the adapter to the Spinner
            tempLecturerId.setAdapter(adapter);
            tempLecturerId.setThreshold(0);
            //========= END OF DROP BOX ITEM =========

        }
    }
    public void classStartFunction(){
        tempClassStart= findViewById(R.id.classStartShowed);
        tempClassStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the default time to 00:00
                int defaultHour = 0;
                int defaultMinute = 0;

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        subjectRegistration.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // This method is called when the registerLecturer selects a time
                                // can use selectedHour and selectedMinute as needed
                                // For example, can update a TextView with the selected time
                                selectedTimeStart = String.format(Locale.getDefault(),"%02d:%02d", selectedHour, selectedMinute);
                                tempClassStart.setText(selectedTimeStart);
                            }
                        },
                        defaultHour,  // initial hour (use the default hour)
                        defaultMinute,  // initial minute (use the default minute)
                        false  // use 24-hour format? (false for AM/PM, true for 24-hour format)
                );

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });
    }
    public void classEndFunction(){
        tempClassEnd= findViewById(R.id.classEndedShowed);
        tempClassEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the default time to 00:00
                int defaultHour = 0;
                int defaultMinute = 0;

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        subjectRegistration.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // This method is called when the registerLecturer selects a time
                                // can use selectedHour and selectedMinute as needed
                                // For example, can update a TextView with the selected time
                                selectedTimeEnded = String.format(Locale.getDefault(),"%02d:%02d", selectedHour, selectedMinute);
                                tempClassEnd.setText(selectedTimeEnded);
                            }
                        },
                        defaultHour,  // initial hour (use the default hour)
                        defaultMinute,  // initial minute (use the default minute)
                        false  // use 24-hour format? (false for AM/PM, true for 24-hour format)
                );

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });
    }
    public void addSubjectButton(){
        //==========Declare EditText==========
        tempSubjectName = findViewById(R.id.getSubjectName);
        tempCourseCode = findViewById(R.id.getCourseCode);
        tempSection = findViewById(R.id.getSection);
        tempVenue = findViewById(R.id.getVenue);

        ImageButton addSubject = findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //=========Store Value in String=========
                subjectName = tempSubjectName.getText().toString();
                courseCode = tempCourseCode.getText().toString();
                section = tempSection.getText().toString();
                venue = tempVenue.getText().toString();
                lecturerId = tempLecturerId.getText().toString();

                //selectedTimeStart,selectedTimeEnded
                if(TextUtils.isEmpty(lecturerId)||TextUtils.isEmpty(courseCode)||TextUtils.isEmpty(subjectName)){
                    Toast.makeText(subjectRegistration.this,"Please enter all fields", Toast.LENGTH_SHORT).show();
                    return ;
                }
                else {
                    //==========REFERENCE FOR SUBJECT===========
                    DatabaseReference subjectRef = rootRef.getReference()
                            .child("Subject")
                            .child(courseCode);
                    subject newSubject = new subject(subjectName,courseCode,section,venue,selectedTimeStart,selectedTimeEnded,lecturerId);
                    subjectRef.setValue(newSubject);
                    //==========REFERENCE FOR LECTURER==========
                    DatabaseReference lecturerRef = rootRef.getReference()
                            .child("Users");
                    lecturerRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String department = "null";
                            String tempLecturerIds = "null";
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    String UID = userSnapshot.getKey();
                                    Log.d("UID", UID);
                                // Iterate through the children of each user node
                                    tempLecturerIds = snapshot.child(UID).child("lecturer_ID").getValue(String.class);
                                    if(lecturerId!=null && lecturerId.equalsIgnoreCase(tempLecturerIds)){

                                            department = snapshot.child(UID).child("department").getValue(String.class);
                                    }

                            }

                            if(department!=null){
                                subject lectSubject = new subject(subjectName,courseCode,section);
                                DatabaseReference lecturerRef = rootRef.getReference()
                                        .child("Department")
                                        .child(department)
                                        .child("Lecturer ID")
                                        .child(lecturerId)
                                        .child("subject");
                                lecturerRef.setValue(lectSubject)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(subjectRegistration.this, "Added", Toast.LENGTH_SHORT).show();
                                                    // The data was successfully written to the database
                                                } else {
                                                    Toast.makeText(subjectRegistration.this, "Failed to add, please submit a report", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });




    }




}

