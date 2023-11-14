package com.example.collegesystem;

public class user {
    private String name;
    private String matricNo;
    private String email;
    private String course;
    private String userID;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(user.class)
    }

    public user(String name, String matricNo, String email, String course,String UID) {
        this.name = name;
        this.matricNo = matricNo;
        this.email = email;
        this.course = course;
        this.userID = UID;
    }
    public String getName() {
        return name;
    }

    public String getMatricNo() {
        return matricNo;
    }

    public String getEmail() {
        return email;
    }

    public String getCourse() {
        return course;
    }
    public String getUserID(){return userID;}

    // Add getters and setters as needed
}

