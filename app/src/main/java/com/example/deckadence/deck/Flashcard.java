package com.example.deckadence.deck;

public class Flashcard {
    private String question, answer;
    // effectively measures how well the student understands the card.
    private long experience;
    // maybe add a last used check? In the case of offline study across multiple devices
    public static final long EASY = 20;
    public static final long GOOD = 5;
    public static final long AGAIN = -5;
    public Flashcard() {
        // empty constructor for firebase
    }
    public Flashcard(String question, String answer, long experience) {
        this.question = question;
        this.answer = answer;
        this.experience = experience;
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

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public String getAnswer() {
        return answer;
    }

    public void answered(long exp) {
        experience += exp;
        if(experience < 0) {
            experience = 0;
        }
    }
}
