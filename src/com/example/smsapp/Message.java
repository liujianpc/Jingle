package com.example.smsapp;


public class Message {
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_SENDED = 2;
    private String messageContent;
    private int type;

    public Message(String messageContent, int type) {
        //super();
        this.messageContent = messageContent;
        this.type = type;
    }


    public String getMessageContent() {
        return messageContent;
    }


    public int getType() {
        return type;
    }


}
