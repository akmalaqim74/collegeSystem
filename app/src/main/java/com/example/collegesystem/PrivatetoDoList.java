package com.example.collegesystem;


public class PrivatetoDoList {
    private String title,detail,date;

    public PrivatetoDoList(String title, String detail, String date){
        this.title = title;

        this.detail = detail;
        this.date = date;
    }
    public PrivatetoDoList(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }


    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }
}
