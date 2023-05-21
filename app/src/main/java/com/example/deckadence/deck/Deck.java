package com.example.deckadence.deck;

import java.util.ArrayList;
import java.util.Date;

public class Deck {
    private String title;
    // has to be a string for firestore reasons
    private String date;
    private String token;
    private String description;
    public Deck() {
        // empty constructor for firebase
    }
    public Deck(String title, String description, String token) {
        this.title = title;
        this.description = description;
        this.token = token;
        this.date = new Date().toString().substring(0,10);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate(){
        return date;
    }
    public String getToken() {
        return token;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setDate(String date){
       this.date = date;
    }
}
