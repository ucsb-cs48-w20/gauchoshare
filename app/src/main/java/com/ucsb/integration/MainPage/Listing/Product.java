package com.ucsb.integration.MainPage.Listing;

public class Product {
    private String category;
    private String createdBy;
    private String description;
    private String imageURL;
    private String price;
    private String title;
    private Boolean sold;
    private String id;

    public Product() {
    }

    public Product(String category, String createdBy, String description, String imageURL, String price, String title, Boolean sold, String id) {
        this.category = category;
        this.createdBy = createdBy;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
        this.title = title;
        this.sold = sold;
        this.id = id;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}