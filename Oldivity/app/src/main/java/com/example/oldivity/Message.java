package com.example.oldivity;

public class Message {
    private String time;
    private String message;
    private String uID;
    public Message(String time, String message, String uID){
        this.time = time;
        this.message = message;
        this.uID = uID;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
}
