package com.example.hotel;

public class Reservation {
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;
    private String user;
    private String userName;
    private String userEmail;

//    public Reservation(String user, String roomNumber, String checkInDate, String checkOutDate) {
//        this.checkInDate = checkInDate;
//        this.checkOutDate = checkOutDate;
//        this.roomNumber = roomNumber;
//        this.user = user;
//    }

    public Reservation(String userName, String roomNumber, String checkInDate, String checkOutDate, String userEmail)
    {
        this.roomNumber = roomNumber;
        this.checkOutDate = checkOutDate;
        this.checkInDate = checkInDate;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Reservation(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
