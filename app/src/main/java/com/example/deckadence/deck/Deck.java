package com.example.deckadence.deck;

import java.util.ArrayList;
import java.util.Date;

public class Deck {
    private ArrayList<Flashcard> cards;
    private String title;
    private Date date;

    public Deck(String title) {
        this.title = title;
        cards = new ArrayList<>();
        date = new Date();
    }

    public String getTitle() {
        return title;
    }
    public Date getLastStudiedDate(){
        return date;
    }
    public void setTitle(String name) {
        this.title = name;
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
