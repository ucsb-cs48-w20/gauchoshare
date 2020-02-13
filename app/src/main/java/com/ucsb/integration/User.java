package com.ucsb.integration;

public class User {
    public String id;
    public String email;
    public String nickname;
    public String username;
    public Boolean isUCSBStudent;
    public String imageUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String email, String nickname, String username, Boolean isUCSBStudent, String imageUrl) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.username = username;
        this.isUCSBStudent = isUCSBStudent;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}