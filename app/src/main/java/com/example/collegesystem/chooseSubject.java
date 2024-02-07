package com.example.collegesystem;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chooseSubject extends AppCompatActivity {
    AutoCompleteTextView subjectSpinner;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosesubject);
        dropBoxSubject();
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

}
