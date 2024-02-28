package com.example.collegesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.UUID;

public class updateToDo extends AppCompatActivity {
    String date,toDoID,title,detail;
    EditText tempToDoDetails,tempTitle;
    TextView displayDate;
    Intent toDoIDIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtodolist);
        showDatePickerDialog();
        exitButton();
        toDoIDIntent = getIntent();
        tempTitle = findViewById(R.id.title);
        tempToDoDetails = findViewById(R.id.taskDetails);
        displayDate = findViewById(R.id.dateDisplay);

        title = toDoIDIntent.getStringExtra("title");
        detail = toDoIDIntent.getStringExtra("detail");
        date = toDoIDIntent.getStringExtra("date");

        tempToDoDetails.setText(detail);

        tempTitle.setText(title);

        displayDate.setText(date);
        toDoID = toDoIDIntent.getStringExtra("toDoIDKey");
        saveToDoList();

    }
    private void exitButton(){
        ImageButton exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateToDo.this, toDoList.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void saveToDoList() {
        ImageButton saveToDo = findViewById(R.id.saveButton);
        saveToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toDoID!=null){
                    title = getTitles();
                    detail = getDetails();

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    if (currentUser != null) {
                        String uid = currentUser.getUid();

                        FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");
                        DatabaseReference userRef = rootRef.getReference()
                                .child("Users")
                                .child(uid);

                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Generate a random ID for the to-do item
                                String randomId = UUID.randomUUID().toString().substring(0, 5);

                                // Retrieve department and lecturer ID from the registerLecturer data
                                String department = dataSnapshot
                                        .child("department")
                                        .getValue(String.class);
                                String lecturerId = dataSnapshot
                                        .child("lecturer_ID")
                                        .getValue(String.class);

                                // Create a to-do object with the provided details
                                PrivatetoDoList tempToDo = new PrivatetoDoList(title, detail, date);

                                // Reference to the location where the to-do item will be stored
                                DatabaseReference toDoRef = rootRef.getReference()
                                        .child("Department")
                                        .child(department)
                                        .child("Lecturer")
                                        .child(lecturerId)
                                        .child("toDoList")
                                        .child(toDoID);

                                // Set the value of the to-do item
                                toDoRef.setValue(tempToDo)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(updateToDo.this, "Updated", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(updateToDo.this,toDoList.class);
                                                    startActivity(intent);
                                                    finish();
                                                    // The data was successfully written to the database
                                                } else {
                                                    Toast.makeText(updateToDo.this, "Failed to add, please submit a report", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("Firebase", "Error fetching data: " + databaseError.getMessage(), databaseError.toException());
                                // Handle errors
                            }
                        });
                    }

                }else{
                    Toast.makeText(updateToDo.this, "Error occur Please Try again", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public String getDetails() {
        tempToDoDetails = findViewById(R.id.taskDetails);
        return tempToDoDetails.getText().toString();
    }

    public String getTitles() {
        tempTitle = findViewById(R.id.title);
        return tempTitle.getText().toString();
    }

    private void showDatePickerDialog() {
        ImageButton datePickerButton = findViewById(R.id.datePicker);
        displayDate = findViewById(R.id.dateDisplay);
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateToDo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Handle the selected date (e.g., update a TextView)
                                // For example, you can update a TextView with the selected date
                                date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                displayDate.setText(date);
                            }
                        },
                        year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();

            }
        });
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateToDo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Handle the selected date (e.g., update a TextView)
                                // For example, you can update a TextView with the selected date
                                date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                displayDate.setText(date);
                            }
                        },
                        year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();

            }
        });


    }

}
