package com.example.chapappfinalmain.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Content {
    private String idUser;
    private String userName;
    private String time;
    private String commentOfContent;
    private String urlImage;
    private int numberLike;
    private int staticContent;
    private String urlAvatar;

    public Content(String idUser, String userName, String time, String commentOfContent, String urlImage, int numberLike, int staticContent, String urlAvatar) {
        this.idUser = idUser;
        this.userName = userName;
        this.time = time;
        this.commentOfContent = commentOfContent;
        this.urlImage = urlImage;
        this.numberLike = numberLike;
        this.staticContent = staticContent;
        this.urlAvatar = urlAvatar;
    }



    public Content(String idUser, String userName, String time, String commentOfContent, int numberLike, int staticContent, String urlAvatar) {
        this.idUser = idUser;
        this.userName = userName;
        this.time = time;
        this.commentOfContent = commentOfContent;
        this.numberLike = numberLike;
        this.staticContent = staticContent;
        this.urlAvatar = urlAvatar;
    }

    public Content() {
    }

    @Override
    public String toString() {
        return "Content{" +
                "idUser='" + idUser + '\'' +
                ", userName='" + userName + '\'' +
                ", time='" + time + '\'' +
                ", commentOfContent='" + commentOfContent + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", numberLike=" + numberLike +
                ", staticContent=" + staticContent +
                ", urlAvatar='" + urlAvatar + '\'' +
                '}';
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

    public int getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(int numberLike) {
        this.numberLike = numberLike;
    }

    public int getStaticContent() {
        return staticContent;
    }

    public void setStaticContent(int staticContent) {
        this.staticContent = staticContent;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
}
