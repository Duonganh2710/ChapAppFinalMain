package com.example.chapappfinalmain.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Content {
    private String idContent;
    private String idUser;
    private String userName;
    private String time;
    private String commentOfContent;
    private String urlImage;
    private String urlAvatar;

    public Content() {
    }

    public Content(String idContent, String idUser, String userName, String time, String commentOfContent, String urlImage, String urlAvatar) {
        this.idContent = idContent;
        this.idUser = idUser;
        this.userName = userName;
        this.time = time;
        this.commentOfContent = commentOfContent;
        this.urlImage = urlImage;
        this.urlAvatar = urlAvatar;
    }

    public Content(String idContent, String idUser, String userName, String time, String commentOfContent, String urlAvatar) {
        this.idContent = idContent;
        this.idUser = idUser;
        this.userName = userName;
        this.time = time;
        this.commentOfContent = commentOfContent;
        this.urlAvatar = urlAvatar;
    }

    public String getIdContent() {
        return idContent;
    }

    public void setIdContent(String idContent) {
        this.idContent = idContent;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommentOfContent() {
        return commentOfContent;
    }

    public void setCommentOfContent(String commentOfContent) {
        this.commentOfContent = commentOfContent;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    @Override
    public String toString() {
        return "Content{" +
                "idContent='" + idContent + '\'' +
                ", idUser='" + idUser + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", commentOfContent='" + commentOfContent + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", urlAvatar='" + urlAvatar + '\'' +
                '}';
    }
}
