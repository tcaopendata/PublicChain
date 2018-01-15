package com.gomsang.lab.publicchain.datas;

/**
 * Created by devkg on 2018-01-15.
 */

public class ChatMessageData {
    private String messageType; // text, share
    private String content;
    private String author;

    public ChatMessageData(String messageType, String content, String author) {
        this.messageType = messageType;
        this.content = content;
        this.author = author;
    }

    public ChatMessageData() {

    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
