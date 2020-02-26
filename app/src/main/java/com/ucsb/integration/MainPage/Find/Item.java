package com.ucsb.integration.MainPage.Find;

public class Item {
    private String name;
    private int photo;
    private int price;


    public Item(String name, int photo, int price) {
        this.name = name;
        this.photo = photo;
        this.price = price;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
