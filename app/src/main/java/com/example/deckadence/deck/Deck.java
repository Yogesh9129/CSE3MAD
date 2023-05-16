package com.example.deckadence.deck;

import java.util.ArrayList;
import java.util.Date;

public class Deck {
    private ArrayList<Flashcard> cards;
    private String title;
    private Date date;
    private String token;
    private String description;
    public Deck(String title, String description, String token) {
        this.title = title;
        this.description = description;
        cards = new ArrayList<>();
        date = new Date();
        this.token = token;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public Date getLastStudiedDate(){
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
    public void setLastStudiedDate(Date date){
        this.date = date;
    }

    public void addCard(Flashcard card) {
        cards.add(card);
    }

    public void removeCard(Flashcard card) {
        cards.remove(card); // might be worth checking if the card was removed or not
    }

    public Flashcard getCardByQuestion(String question) {
        for (Flashcard card: cards) {
            if(card.getQuestion().equals(question)) {
                return card;
            }
        }
        return null; // null if no card is found
    }

    public Flashcard getCardByAnswer(String answer) {
        for (Flashcard card: cards) {
            if(card.getAnswer().equals(answer)) {
                return card;
            }
        }
        return null; // null if no card is found
    }
}
