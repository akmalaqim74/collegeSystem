package com.example.collegesystem;

public class tempSubject {
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMatricNo() {
        return studentMatricNo;
    }

    public void setStudentMatricNo(String studentMatricNo) {
        this.studentMatricNo = studentMatricNo;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    String studentName,studentMatricNo,attendanceStatus;
    public tempSubject(String tempName,String tempMatricNo,String tempAttendanceStatus){
        this.studentName = tempName;
        this.studentMatricNo = tempMatricNo;
        this.attendanceStatus = tempAttendanceStatus;
    }

}
