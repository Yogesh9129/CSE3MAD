package com.example.deckadence.deck;

import junit.framework.TestCase;

public class FlashcardTest extends TestCase {

    public void testSetAnswer() {
        Flashcard card = new Flashcard("","Hello",0);
        card.setAnswer("Test");
        assertEquals("Test",card.getAnswer());
    }

    public void testSetQuestion() {
        Flashcard card = new Flashcard("Goodbye","",0);
        card.setQuestion("Tester");
        assertEquals("Tester",card.getQuestion());
    }

    public void testGetAnswer() {
        Flashcard card = new Flashcard("","Hello",0);
        assertEquals("Hello",card.getAnswer());
    }

    public void testGetQuestion() {
        Flashcard card = new Flashcard("Goodbye","",0);
        assertEquals("Goodbye",card.getQuestion());
    }

    public void testGetExperience() {
        Flashcard card = new Flashcard("","",0);
        assertEquals(0,card.getExperience());
    }

    public void testSetExperience() {
        Flashcard card = new Flashcard("","",0);
        card.setExperience(100);
        assertEquals(100,card.getExperience());
    }

    public void testAnswered() {
        Flashcard card = new Flashcard("","",0);
        card.answered(10);
        assertEquals(10,card.getExperience());
        card.answered(-20);
        assertEquals(0,card.getExperience());
    }
}