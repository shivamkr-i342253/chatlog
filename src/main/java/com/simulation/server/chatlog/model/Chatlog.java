package com.simulation.server.chatlog.model;

import javax.persistence.*;

@Entity
@Table(name = "chatlog")
public class Chatlog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer messageID;

    @Column(name = "userID")
    private Integer userID;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "is_sent")
    private Integer isSent;

    public Integer getMessageID() {
        return messageID;
    }

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }
}
