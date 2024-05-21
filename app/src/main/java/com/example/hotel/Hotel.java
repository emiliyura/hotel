package com.example.hotel;

public class Hotel {
    String image;
    String name;
    String price;
    String description;

    public Hotel(){}

    public Hotel(String name, String description, String price, String image) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}