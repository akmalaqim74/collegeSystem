package com.example.collegesystem;

public class timeTableSubject {
    private String subject,cStart,cEnded,venue;

    public timeTableSubject(){

    }

    public timeTableSubject(String subject, String cStart, String cEnded, String venue){
        this.subject = subject;
        this.cStart = cStart;
        this.cEnded = cEnded;
        this.venue = venue;
    }


    public String getcEnded() {
        return cEnded;
    }

    public String getVenue() {
        return venue;
    }

    public String getcStart() {
        return cStart;
    }

    public String getSubject() {
        return subject;
    }
}
