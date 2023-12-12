package com.example.collegesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class toDoList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);
        addToDoList();

    }
    public void addToDoList(){
        ImageButton addtoDoList = findViewById(R.id.addTodOList);
        addtoDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Define the intent to navigate to the menu page
                Intent intent = new Intent(toDoList.this,addToDoList.class);
                //start the homepage activity
                startActivity(intent);
            }
        });
    }

}
