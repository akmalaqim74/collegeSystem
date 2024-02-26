package com.example.collegesystem;

public class item {


    String itemName;
    String location;
    String status;
    String borrowName;
    String borrowDate;
    String returnDate;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String category;
    public item(String itemName, String location, String status, String borrowName, String borrowDate, String returnDate,String tempCategory) {
        this.itemName = itemName;
        this.location = location;
        this.status = status;
        this.borrowName = borrowName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.category = tempCategory;
    }
    public item(){

    }
    public item(String itemName, String location, String status,String tempCategory) {
        this.itemName = itemName;
        this.location = location;
        this.status = status;
        this.category = tempCategory;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

}
