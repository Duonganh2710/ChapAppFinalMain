package com.example.chapappfinalmain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {
    private String userName;
    private String email;
    private String password;
    private String userId;
    private String imgUri;
    private String phoneNumber;
    public User() {

    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String userName, String email, String password, String userId, String imgUri, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.imgUri = imgUri;
        this.phoneNumber = phoneNumber;
    }



    public User(String userName, String email, String password, String userId) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }


    public User(String userName, String email, String password, String userId, String imgUri) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.imgUri = imgUri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", imgUri='" + imgUri + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
