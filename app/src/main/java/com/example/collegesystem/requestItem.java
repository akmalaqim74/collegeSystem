package com.example.collegesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;

public class requestItem extends AppCompatActivity {
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    Intent itemCategory;
    Spinner tempItem;
    TextView displayStartBorrowDate, showReturnDate;
    String category,dateBorrow = null,returnDate = null,itemName,nameAndID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_item);

        //======set subject Name=====
        itemCategory = getIntent();
        category = itemCategory.getStringExtra("category");
        Log.d("Category", "Category received: " + category); // Add this line to log the category value
        showStartBorrowDate();
        showReturnDate();
        dropBoxItemName();
        requestButton();
        backButtonFunction();



    }
    public void backButtonFunction(){
        //========== BACK BUTTON FUNCTION ==========
        ImageButton back = findViewById(R.id.backButtonSignUp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event here, e.g., open SignUpActivity
                Intent intent = new Intent(requestItem.this,ItemCategory.class);
                startActivity(intent);
                finish();
            }
        });
        //========== END OF BACK BUTTON FUNCTION==========
    }
    public void requestButton(){
        ImageButton request = findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UID = currentUser.getUid();
                DatabaseReference userRef = rootRef
                        .getReference()
                        .child("Users")
                        .child(UID);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        nameAndID = snapshot.child("lecturer_ID").getValue(String.class) + " "
                                + snapshot.child("name").getValue(String.class);
                        itemName = tempItem.getSelectedItem().toString();
                        if(!itemName.isEmpty() && returnDate !=null && dateBorrow != null && !nameAndID.isEmpty()){
                            Log.d("Item Name", itemName);
                            Log.d("Return Date", returnDate);
                            Log.d("Date Borrow", dateBorrow);
                            Log.d("Name and ID", nameAndID);
                            DatabaseReference itemRef = rootRef
                                    .getReference()
                                    .child("Borrowed Item")
                                    .child(category)
                                    .child(itemName);
                            itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot tempSnapshot) {
                                    if(tempSnapshot.exists()){
                                        String status = tempSnapshot.child("status").getValue(String.class);
                                        String dummyLocation = tempSnapshot.child("location").getValue(String.class);
                                        if(status.equalsIgnoreCase("available")){
                                            status = "pending";
                                            item newBorrower = new item(itemName,dummyLocation,status,nameAndID,dateBorrow,returnDate,category);
                                            itemRef.setValue(newBorrower).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(requestItem.this, "Request Has been Made,\n Please wait atleast 2 days", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(requestItem.this, homePage.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    else{
                                        Toast.makeText(requestItem.this, "Item Not Available for borrowing", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(requestItem.this, "Please Enter All field", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }



    private void showStartBorrowDate() {
        displayStartBorrowDate = findViewById(R.id.dateBorrow);
        // Get current returnDate
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        displayStartBorrowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requestItem.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Handle the selected returnDate (e.g., update a TextView)
                                // For example, you can update a TextView with the selected returnDate
                                dateBorrow = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                                displayStartBorrowDate.setText(dateBorrow);
                            }
                        },
                        year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();

            }
        });


    }
    private void showReturnDate() {
        showReturnDate = findViewById(R.id.returnDate);
        // Get current returnDate
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        showReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requestItem.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Handle the selected returnDate (e.g., update a TextView)
                                // For example, you can update a TextView with the selected returnDate
                                returnDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                                showReturnDate.setText(returnDate);
                            }
                        },
                        year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();

            }
        });


    }
    public void dropBoxItemName(){
        if(!category.isEmpty()){
            DatabaseReference itemRef = rootRef.getReference()
                    .child("Borrowed Item")
                    .child(category);

            // Now you can use the retrieved values as needed for the current registerLecturer
            itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        ArrayList<String> itemName = new ArrayList<String>();

                        for(DataSnapshot tempSnapShot: snapshot.getChildren()){
                            String tempItemName = tempSnapShot.getKey();
                            String status = snapshot.child(tempItemName).child("status").getValue(String.class);
                            if(!status.isEmpty() && status.equalsIgnoreCase("available")){
                                itemName.add(tempSnapShot.getKey());
                            }else{
                                itemName.add(tempItemName + " (Not Available)");
                            }
                        }

                        dropBoxFunction(itemName);
                    }


                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




    }
    private void dropBoxFunction(ArrayList<String> itemName){
            //============ DROP BOX ITEM==========
            tempItem = findViewById(R.id.itemName);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_style, itemName);
            // Set the adapter to the Spinner
            tempItem.setAdapter(adapter);
            //========= END OF DROP BOX ITEM =========

        }
}
