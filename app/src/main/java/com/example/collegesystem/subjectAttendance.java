package com.example.collegesystem;


public class subjectAttendance {
    private boolean isAttend,isAbsent,isExcuse;
    public subjectAttendance(){

    }
    public boolean isAttend() {
        return isAttend;
    }

    public void setAttend(boolean attend) {
        isAttend = attend;
    }

    public boolean isAbsent() {
        return isAbsent;
    }

    public void setAbsent(boolean absent) {
        isAbsent = absent;
    }

    public boolean isExcuse() {
        return isExcuse;
    }

    public void setExcuse(boolean excuse) {
        isExcuse = excuse;
    }
}
