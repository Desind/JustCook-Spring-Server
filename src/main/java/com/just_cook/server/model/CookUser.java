package com.just_cook.server.model;

public class CookUser {
    Integer userID;
    String username;
    String email;
    String registrationDate;

    public Integer getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public CookUser(Integer userID, String username, String email, String registrationDate) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}
