package com.example.hotel;

public class Reservation {
    private String userName;
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;
    private String email;
    private String hotelName; // новое поле для названия отеля
    private String id;

    public Reservation() {
        // Пустой конструктор требуется для вызовов DataSnapshot.getValue(Reservation.class)
    }

    public Reservation(String id, String userName, String roomNumber, String checkInDate, String checkOutDate, String email, String hotelName) {
        this.userName = userName;
        this.id = id;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.email = email;
        this.hotelName = hotelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Добавьте геттеры и сеттеры для всех полей
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}