package com.example.collegesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class attendanceStatus extends AppCompatActivity {
    Intent subjectIntent;
    TextView TVsubjectName,submitButton,displayDate;
    String subjectName,courseCode,date,matricNo,name;
    RecyclerView recyclerView;
    attendanceStatusAdapter listAdapter;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_attendance);
        subjectIntent = getIntent();
        subjectName = subjectIntent.getStringExtra("subject");
        setSubjectName(subjectName);
        showDatePickerDialog();
        displayStatus();

    }
    public void recyclerViewCalling(){
        //=====recycler View =====
        recyclerView = findViewById(R.id.studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(attendanceStatus.this));
        listAdapter = new attendanceStatusAdapter(attendanceStatus.this);
        recyclerView.setAdapter(listAdapter);

    }
    private void showDatePickerDialog() {
        displayDate = findViewById(R.id.displayDate);
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        attendanceStatus.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Handle the selected date (e.g., update a TextView)
                                // For example, you can update a TextView with the selected date
                                date = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                                displayDate.setText(date);
                            }
                        },
                        year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();

            }
        });


    }
    public void setSubjectName(String subject){
        int tempCourseCode = subject.indexOf(' ');
        courseCode = subject.substring(0,tempCourseCode);
        String subjectName = subject.substring(tempCourseCode).toUpperCase();
        TVsubjectName = findViewById(R.id.subjectTextView);
        TVsubjectName.setText(courseCode + " \n" + subjectName);
    }
    public void displayStatus(){
        recyclerViewCalling();
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!date.isEmpty()){

                    DatabaseReference matricNo = rootRef.getReference()
                            .child("Subject")
                            .child(courseCode)
                            .child("attendance")
                            .child(date);
                    matricNo.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            listAdapter.clearItems();
                            if(snapshot.exists()){

                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    String matricAndName = dataSnapshot.getKey();
                                    String status = snapshot.child(matricAndName).getValue(String.class);

                                    listAdapter.addItem(matricAndName,status);
                                }

                                listAdapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(attendanceStatus.this, "No Data For this Date", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{
                    Toast.makeText(attendanceStatus.this, "Please choose date", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
