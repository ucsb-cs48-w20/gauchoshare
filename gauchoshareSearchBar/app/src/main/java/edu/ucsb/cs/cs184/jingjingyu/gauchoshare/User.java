package edu.ucsb.cs.cs184.jingjingyu.gauchoshare;

public class User {
    public String username;
    public String email;
    public String nickname;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String nickname) {
        this.username = username;
        this.email = email;
        this.nickname=nickname;
    }
}
