package com.steszyngagne.draw.Structs;

public class User {

    public String username;
    public String email;
    public String token;

    public User() {}

    public User(String username, String email, String token) {
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
