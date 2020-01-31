package com.ucsb.integration;

public class User {
    public String email;
    public String nickname;
    public Boolean isUCSBStudent;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String nickname, Boolean isUCSBStudent) {
        this.email = email;
        this.nickname = nickname;
        this.isUCSBStudent = isUCSBStudent;
    }
}