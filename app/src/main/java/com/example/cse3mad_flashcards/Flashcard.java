package com.example.cse3mad_flashcards;

public class Flashcard {
    private String question, answer;
    // effectively measures how well the student understands the card.
    private int experience;
    // change in experience based on how well the user remembered the card.
    private static final int EASY_FACTOR = 20;
    private static final int GOOD_FACTOR = 10;
    private static final int AGAIN_FACTOR = -10;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        experience = 0;
    }
}
