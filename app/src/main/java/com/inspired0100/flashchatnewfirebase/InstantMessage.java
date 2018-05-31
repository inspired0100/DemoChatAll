package com.inspired0100.flashchatnewfirebase;

public class InstantMessage {
    private String mmessage;
    private String mauthor;


    public InstantMessage(String mauthor, String mmessage ) {
        this.mmessage = mmessage;
        this.mauthor = mauthor;

    }

    public InstantMessage() {


    }



    public String getMmessage() {
        return mmessage;
    }

    public String getMauthor() {
        return mauthor;
    }
}
