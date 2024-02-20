package com.example.collegesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Calendar;
import java.util.List;

public class studentAttendance extends AppCompatActivity {
    Intent subjectIntent;
    TextView TVsubjectName,submitButton,displayDate;
    String subjectName,courseCode,date,matricNo,name;
    RecyclerView recyclerView;
    ArrayList<subject> list = new ArrayList<>();
    studentAttendanceAdapter listAdapter;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_attendance);

        //======set subject Name=====
        subjectIntent = getIntent();
        subjectName = subjectIntent.getStringExtra("subject");
        setSubjectName(subjectName);
        recyclerView();
        showDatePickerDialog();
        submitButtonFunction();


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
                        studentAttendance.this,
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
    public void recyclerView(){

        recyclerViewCalling();
        DatabaseReference subjectRef = rootRef.getReference()
                .child("Subject")
                .child(courseCode)
                .child("Student");
        subjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("test","Data triggered");
                list.clear();
                for(DataSnapshot tempSnapShot: snapshot.getChildren()){
                    matricNo = tempSnapShot.getKey();
                     name = snapshot.child(matricNo).getValue(String.class);
                    if(matricNo!=null && name!= null){
                        subject subjectStudent = new subject(name,matricNo);
                        list.add(subjectStudent);


                    }
                    Log.d("Name",name);
                    Log.d("Matric No",matricNo);

                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void recyclerViewCalling(){
        //=====recycler View =====
        recyclerView = findViewById(R.id.studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(studentAttendance.this));
        listAdapter = new studentAttendanceAdapter(studentAttendance.this,list);
        recyclerView.setAdapter(listAdapter);

    }
    public void setSubjectName(String subject){
        int tempCourseCode = subject.indexOf(' ');
        courseCode = subject.substring(0,tempCourseCode);
        String subjectName = subject.substring(tempCourseCode).toUpperCase();
        TVsubjectName = findViewById(R.id.subjectTextView);
        TVsubjectName.setText(courseCode + " \n" + subjectName);
    }
    public void submitButtonFunction(){
        submitButton = findViewById(R.id.submitButton);
            submitButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(date!=null ) {


                        DatabaseReference dateRef = rootRef.getReference()
                                .child("Subject")
                                .child(courseCode)
                                .child("attendance")
                                .child(date);
                        List<String> selectedCheckBoxes = new ArrayList<>();
                        boolean anyCheckBoxChecked = false;
                        boolean checkBoxNotchecked = false;
                        Log.d("Date", date);
                        //===== Loop to checked whether all of the checkbox is checked=====
                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            View view = recyclerView.getChildAt(i);
                            CheckBox attendCheckBox = view.findViewById(R.id.attendCheckBox);
                            CheckBox absentCheckBox = view.findViewById(R.id.absentCheckBox);
                            CheckBox excuseCheckBox = view.findViewById(R.id.excuseCheckBox);
                            if (attendCheckBox.isChecked()) {
                            } else if (absentCheckBox.isChecked()) {
                            } else if (excuseCheckBox.isChecked()) {
                            } else {checkBoxNotchecked = true;

                            }
                        }
                        if(!checkBoxNotchecked){
                            for (int i = 0; i < list.size(); i++) {
                                subject currentSubject = list.get(i);
                                if (currentSubject.isAttend()) {
                                    selectedCheckBoxes.add("Attend");
                                    anyCheckBoxChecked = true;

                                } else if (currentSubject.isAbsent()) {
                                    selectedCheckBoxes.add("Absent");
                                    anyCheckBoxChecked = true;
                                } else if (currentSubject.isExcuse()) {
                                    selectedCheckBoxes.add("Excuse");
                                    anyCheckBoxChecked = true;
                                } else {
                                    selectedCheckBoxes.add("Not Checked");
                                }
                                final int finalI = i;

                                String tempNameMatric = list.get(i).getStudentMatricNo() + " " + list.get(i).getStudentName();
                                dateRef.child(tempNameMatric).setValue(selectedCheckBoxes.get(i))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(finalI == list.size() - 1){
                                                    Toast.makeText(studentAttendance.this, "Sucessful Submit", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }else{
                            Toast.makeText(studentAttendance.this, "Please Check all the student's Attendance", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(studentAttendance.this, "Please Enter Date Of Attendance", Toast.LENGTH_SHORT).show();

                    }
                }
            });


    }
}
/*if(!isSuccessful.isSuccessful()){
                            Toast.makeText(studentAttendance.this, "Some Error Occur, please Try again \n Please inform admin if happens again", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(studentAttendance.this, "Succesfully submit", Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(studentAttendance.this, "Please Enter Date Of Attendance", Toast.LENGTH_SHORT).show();

                    }*/