package com.example.deckadence.deck;

public class Flashcard {
    private String question, answer;
    // effectively measures how well the student understands the card.
    private int experience;
    // maybe add a last used check? In the case of offline study across multiple devices
    public enum Level {
        EASY (20),
        GOOD (5),
        AGAIN (-5);
        private final int factor;
        Level(int factor) {
            this.factor = factor;
        }
        int getFactor() {return factor;}
    }

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

    public void answered(Level level) {
        experience += level.getFactor();
        if(experience < 0) {
            experience = 0;
        }
    }
}
