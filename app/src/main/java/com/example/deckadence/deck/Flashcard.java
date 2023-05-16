package com.example.deckadence.deck;

public class Flashcard {
    private String question, answer;
    // effectively measures how well the student understands the card.
    private int experience;
    // maybe add a last used check? In the case of offline study across multiple devices
    public static final int EASY = 20;
    public static final int GOOD = 5;
    public static final int AGAIN = -5;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        experience = 0;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void answered(int exp) {
        experience += exp;
        if(experience < 0) {
            experience = 0;
        }
    }
}
