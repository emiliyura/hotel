package com.example.hotel;

public class User {
    public String uid, type, email, name;

    public User(){}

    public User(String uid, String email, String type, String name){
        this.type = type;
        this.uid = uid;
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
