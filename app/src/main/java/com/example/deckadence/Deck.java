package com.example.cse3mad_flashcards;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Flashcard> cards;
    private String name;

    public Deck(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
