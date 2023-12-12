package com.example.collegesystem;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class addSubject extends AppCompatActivity {
    String selectedTimeStart,selectedTimeEnded,email;
    ImageButton back,classStartButton;
    AutoCompleteTextView tempSubject;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsubject);
        mAuth = FirebaseAuth.getInstance();

        addSubjectSuggestion();
        backButtonFunction();
        classStartTimePickerFunction();
        classEndedTimePickerFunction();
        addButtonFunction();


    }

    public void addButtonFunction(){
        ImageButton addSubject = findViewById(R.id.addSubjectButton);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String venue = setGetVenue();
                tempSubject = findViewById(R.id.dropBox);
                String subject = tempSubject.getText().toString();

            }
        });

    }


    public void addSubjectSuggestion(){
        //============ DROP BOX ITEM==========
        tempSubject = findViewById(R.id.dropBox);
        // Define the options for the drop-down menu in an array
        String[] options = {"Mobile App Development","Data Structure Algorithm"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        tempSubject.setAdapter(adapter);
        tempSubject.setThreshold(0);

        //========= END OF DROP BOX ITEM =========

    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        back = findViewById(R.id.backButtonAddSubject);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(addSubject.this,homePage.class);
                startActivity(intent);
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void classStartTimePickerFunction(){
        TextView classStart = findViewById(R.id.classStartShowed);
        classStartButton = findViewById(R.id.timePickerClassStart);
        classStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the default time to 00:00
                int defaultHour = 0;
                int defaultMinute = 0;

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        addSubject.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // This method is called when the user selects a time
                                // can use selectedHour and selectedMinute as needed
                                // For example, can update a TextView with the selected time
                                selectedTimeStart = String.format(Locale.getDefault(),"%02d:%02d", selectedHour, selectedMinute);
                                classStart.setText(selectedTimeStart);
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
    public void classEndedTimePickerFunction(){
        TextView classEnded = findViewById(R.id.classEndedShowed);
        classStartButton = findViewById(R.id.timePickerClassEnded);
        classStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the default time to 00:00
                int defaultHour = 0;
                int defaultMinute = 0;

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        addSubject.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // This method is called when the user selects a time
                                // can use selectedHour and selectedMinute as needed
                                // For example, can update a TextView with the selected time
                                selectedTimeEnded = String.format(Locale.getDefault(),"%02d:%02d", selectedHour, selectedMinute);
                                classEnded.setText(selectedTimeEnded);
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
    public String setGetVenue(){
        EditText tempGetVenue = findViewById(R.id.venue);
        String getVenue = tempGetVenue.getText().toString();

        return getVenue;
    }


}
