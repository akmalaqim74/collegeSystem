package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemCategory extends AppCompatActivity {
    Spinner itemCategory;
    FirebaseDatabase rootRef = FirebaseDatabase.getInstance("https://college-system-dcs212004-default-rtdb.asia-southeast1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category);
        dropBoxItem();
        nextButton();
        exitButton();

    }
    private void exitButton(){
        ImageButton exit = findViewById(R.id.Exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemCategory.this, homePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void nextButton(){
        TextView nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = itemCategory.getSelectedItem().toString();
                Intent intent = new Intent(ItemCategory.this, requestItem.class);
                intent.putExtra("category", category);
                startActivity(intent);

            }
        });
    }

    public void dropBoxItem(){
        //============ DROP BOX ITEM==========

        itemCategory = findViewById(R.id.subjectSpinner);
        // Define the options for the drop-down menu in an array
        String[] options = {"Computers and Accessories","Electronics","Audiovisual Equipment","Others"};
        // Create an ArrayAdapter to set the options to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.spinner_item_style, options);
        // Set the adapter to the Spinner
        itemCategory.setAdapter(adapter);
        //========= END OF DROP BOX ITEM =========

    }
}
