package com.ucsb.integration.MainPage.Listing;

public class Product {
    private String category;
    private String createdBy;
    private String description;
    private String imageUrl;
    private String price;
    private String title;

    public Product() {
    }

    public Product(String category, String createdBy, String description, String imageUrl, String price, String title) {
        this.category = category;
        this.createdBy = createdBy;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}