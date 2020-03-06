package com.ucsb.integration.MainPage.Profile;

public class UserInformation implements Comparable<UserInformation> {
    public String username;
    public String fullname;
    public String email;
    public String phonenumber;
    public String id;
    public String imageURL;
    public String venmo;

    public UserInformation() {

    }
    public UserInformation(String username, String fullname, String email, String phonenumber, String id, String imageURL, String venmo) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.id = id;
        this.imageURL = imageURL;
        this.venmo = venmo;
    }
    public String getUsername() {
        return this.username;
    }
    public String getFullname() {
        return this.fullname;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhonenumber() {
        return this.phonenumber;
    }
    public String getId() {
        return this.id;
    }
    public String getImageURL() { return imageURL; }
    public String getVenmo() { return this.venmo; }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setFullname(String fullName) {
        this.fullname = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(String phoneNumber) {
        this.phonenumber = phoneNumber;
    }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public void setVenmo(String venmo) {this.venmo = venmo;}

    @Override
    public int compareTo(UserInformation user) {
        return this.getUsername().toLowerCase().compareTo(user.getUsername().toLowerCase());
    }
}
