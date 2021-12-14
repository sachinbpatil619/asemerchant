package com.shimoga.asesolMerchant.Model;

public class Mobile {
    private String Name,Price,Ram,Release_date,Storage,Image;

    public Mobile() {
    }

    public Mobile(String name, String price, String ram, String release_date, String storage, String image) {
        Name = name;
        Price = price;
        Ram = ram;
        Release_date = release_date;
        Storage = storage;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRam() {
        return Ram;
    }

    public void setRam(String ram) {
        Ram = ram;
    }

    public String getRelease_date() {
        return Release_date;
    }

    public void setRelease_date(String release_date) {
        Release_date = release_date;
    }

    public String getStorage() {
        return Storage;
    }

    public void setStorage(String storage) {
        Storage = storage;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
