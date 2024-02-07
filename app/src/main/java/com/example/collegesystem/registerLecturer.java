package com.example.collegesystem;

public class registerLecturer {
    private String name,userID,department,email,lecturer_ID,role;

    public registerLecturer() {
        // Default constructor required for calls to DataSnapshot.getValue(registerLecturer.class)
    }

    public registerLecturer(String tempName, String tempLecturer_ID, String tempEmail, String tempDepartment, String tempUID,String tempRole) {
        this.name = tempName;
        this.lecturer_ID = tempLecturer_ID;
        this.email = tempEmail;
        this.department = tempDepartment;
        this.userID = tempUID;
        this.role = tempRole;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Add getters and setters as needed
}
