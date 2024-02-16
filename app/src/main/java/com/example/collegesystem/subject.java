package com.example.collegesystem;

public class subject {
    private String subjectName,courseCode,section,venue,classStart,classEnd,lecturerId,studentName,studentMatricNo;
    private boolean isAttend,isAbsent,isExcuse;
    public subject(){

    }
    public subject(String tempSubjectName, String tempCourseCode,String tempSection,String tempVenue,String tempClassStart,String tempClassEnd,String tempLecturerId){
        this.subjectName = tempSubjectName;
        this.courseCode = tempCourseCode;
        this.section = tempSection;
        this.venue = tempVenue;
        this.classStart = tempClassStart;
        this.classEnd = tempClassEnd;
        this.lecturerId = tempLecturerId;
    }
    public subject(String tempSubjectName,String tempCourseCode,String tempSection){
        this.subjectName = tempSubjectName;
        this.courseCode = tempCourseCode;
        this.section = tempSection;

    }

    public subject(String tempName,String tempMatricNo){
        this.studentName = tempName;
        this.studentMatricNo = tempMatricNo;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSection() {
        return section;
    }

    public String getVenue() {
        return venue;
    }

    public String getClassStart() {
        return classStart;
    }

    public String getClassEnd() {
        return classEnd;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setClassStart(String classStart) {
        this.classStart = classStart;
    }

    public void setClassEnd(String classEnd) {
        this.classEnd = classEnd;
    }

    public void setLecturerId(String lecturerId) {
        lecturerId = lecturerId;
    }

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
