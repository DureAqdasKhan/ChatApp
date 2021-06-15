package com.example.a3;

public class Chat {

    String sender;
    String receiver;
    String message;
    String date;
    String draft_content;
    public Chat(String sender, String receiver, String message,String date,String draft_content) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date=date;
        this.draft_content=draft_content;
    }

    public Chat() {
    }
    public  String getDraft_content(){return draft_content;}
    public void setDraft_content(String draft_content){this.draft_content=draft_content;}
    public String getDate(){return  date;}

    public void setDate(String date){this.date=date;}
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
