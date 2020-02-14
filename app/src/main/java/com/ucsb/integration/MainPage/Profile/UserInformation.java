package com.ucsb.integration.MainPage.Profile;

public class UserInformation {
    public String username;
    public String fullname;
    public String email;
    public String phonenumber;
    public String id;
    public String imageUrl;

    public UserInformation() {

    }
    public UserInformation(String username, String fullname, String email, String phonenumber, String id, String imageUrl) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.id = id;
        this.imageUrl = imageUrl;
    }
    public String getUserName() {
        return this.username;
    }
    public String getFullName() {
        return this.fullname;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhoneNumber() {
        return this.phonenumber;
    }
    public String getId() {
        return this.id;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phonenumber = phoneNumber;
    }
}