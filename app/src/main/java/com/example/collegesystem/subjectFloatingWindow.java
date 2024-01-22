package com.example.collegesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class subjectFloatingWindow extends PopupWindow {
    public subjectFloatingWindow(Context context){
        super(context);

        View contentView = LayoutInflater.from(context).inflate(R.layout.choosesubject, null);
        setContentView(contentView);
        ImageButton closeButton = contentView.findViewById(R.id.Exit);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

    }
}
