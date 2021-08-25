package com.example.chapappfinalmain.model;

public class Search {
    private String img;
    private String text;
    private User user;


    public Search(String img, String text, User user) {
        this.img = img;
        this.text = text;
        this.user = user;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
