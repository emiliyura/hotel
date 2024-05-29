package com.example.hotel;

public class Reservation {
    private String id;
    private String hotelName;
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;
    private String userName;
    private String email;
    private String userId; // Добавьте это поле

    // Необходимый пустой конструктор
    public Reservation() {
    }

    public Reservation(String id, String hotelName, String roomNumber, String checkInDate, String checkOutDate, String userName, String email, String userId){
        this.userId = userId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.email = email;
        this.id = id;
        this.roomNumber = roomNumber;
        this.hotelName = hotelName;
        this.userName = userName;
    }

    // Геттеры и сеттеры для всех полей
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
