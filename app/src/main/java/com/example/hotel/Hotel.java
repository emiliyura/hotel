package com.example.hotel;

public class Hotel {
    String name;
    String description;
    String country;
    String city;
    Double price;
    int Image;

    public Hotel(){}
    public Hotel(String name, String description, String country, String city, Double price, int Image){
        this.city = city;
        this.country = country;
        this.description = description;
        this.price = price;
        this.Image = Image;
        this.name = name;
    }

    public Hotel(String name, int Image){
        this.name = name;
        this.Image = Image;
    }
    public Hotel(String name, int Image, String description){
        this.name = name;
        this.Image = Image;
        this.description = description;
    }



    //getters
    public String getName() {
        return name;
    }

    public int getImage() {
        return Image;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public Double getPrice() {
        return price;
    }

    //setters
    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
