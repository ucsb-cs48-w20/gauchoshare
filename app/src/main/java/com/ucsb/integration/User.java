package com.ucsb.integration;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class User {
    public String id;
    public String email;
    public String nickname;
    public String username;
    public String fullname;
    public String phonenumber;
    public Boolean isUCSBStudent;
    public String imageURL;
    //public DatabaseReference Items;
    public HashMap<String, Object> Items;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String email, String nickname, String username, String fullname, String phonenumber,
                Boolean isUCSBStudent, String imageURL, HashMap<String, Object> items) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.username = username;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.isUCSBStudent = isUCSBStudent;
        this.imageURL = imageURL;
        Items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getUCSBStudent() {
        return isUCSBStudent;
    }

    public void setUCSBStudent(Boolean UCSBStudent) {
        isUCSBStudent = UCSBStudent;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /*public HashMap<String, Object> getItems() {
        return Items;
    }

    public void setItems(HashMap<String, Object> items) {
        Items = items;
    }*/
}