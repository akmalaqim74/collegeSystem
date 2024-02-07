package com.example.collegesystem;

public class registerStudent {
    private String name,userID,department,email,IcNumber,matricNo,role;

    public registerStudent(){

    }

    public registerStudent(String name, String userID, String department, String email, String icNumber, String matricNo,String tempRole){
        this.name = name;
        this.userID = userID;
        this.department = department;
        this.email = email;
        this.IcNumber = icNumber;
        this.matricNo = matricNo;
        this.role = tempRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcNumber() {
        return IcNumber;
    }

    public void setIcNumber(String icNumber) {
        IcNumber = icNumber;
    }

    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public String getRole() {
        return role;
    }
}
