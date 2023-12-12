package com.example.collegesystem;

public class user {
    private String name,userID,department,email,lecturer_ID;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(user.class)
    }

    public user(String name, String lecturer_ID, String email, String department,String UID) {
        this.name = name;
        this.lecturer_ID = lecturer_ID;
        this.email = email;
        this.department = department;
        this.userID = UID;
    }
    public String getName() {
        return name;
    }

    public String getlecturer_ID() {
        return lecturer_ID;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }
    public String getUserID(){return userID;}

    // Add getters and setters as needed
}
