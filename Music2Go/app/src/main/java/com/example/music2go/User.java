package com.example.music2go;

public class User {
    private String fname;
    private String lname;
    private String username;
    private String password;
    private String email;
    private String key;

    public User() {
    }

    public User(String fname, String lname, String username, String password, String email, String key) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.key = key;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
