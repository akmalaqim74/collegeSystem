package com.example.collegesystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class registerItem extends AppCompatActivity {
    EditText tempItemName,tempLocation;
    Spinner tempCategory;

    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_item);
        dropBoxItem();
        registerItem();

    }

    public void registerItem(){
        ImageButton register = findViewById(R.id.registerItem);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempItemName = findViewById(R.id.getItemName);
                tempLocation = findViewById(R.id.location);

                String itemName = tempItemName.getText().toString().trim();
                String location = tempLocation.getText().toString();
                String category = tempCategory.getSelectedItem().toString();

                if(!itemName.isEmpty() && !location.isEmpty() && !category.isEmpty()){

                    DatabaseReference itemRef = rootRef
                            .getReference()
                            .child("Borrowed Item")
                            .child(category)
                            .child(itemName);
                    itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(registerItem.this, "Item Existed", Toast.LENGTH_SHORT).show();
                            }else{
                                item newItem = new item(itemName,location,"available",category);
                                itemRef.setValue(newItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(registerItem.this, "Succeful add an item", Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    Toast.makeText(registerItem.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
    public void dropBoxItem(){
        //============ DROP BOX ITEM==========
        tempCategory = findViewById(R.id.category);
        // Define the options for the drop-down menu in an array
        String[] options = {"Computers and Accessories","Electronics","Audiovisual Equipment","Others"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        tempCategory.setAdapter(adapter);
        //========= END OF DROP BOX ITEM =========

    }
}
