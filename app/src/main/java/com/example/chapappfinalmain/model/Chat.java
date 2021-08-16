package com.example.chapappfinalmain.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Chat {
    private String receiveId;
    private String senderId;
    private String message;

    public Chat(String receiveId, String senderId, String message) {
        this.receiveId = receiveId;
        this.senderId = senderId;
        this.message = message;
    }
    public Chat(){

    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "receiveId='" + receiveId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
