package com.example.deckadence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.deckadence.deck.Deck;
import com.example.deckadence.deck.Flashcard;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudyActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseHelper fh;
    private TextView question;
    private TextView answer;
    private Button revealButton;
    private Button againButton;
    private Button goodButton;
    private Button easyButton;
    private Deck deck;
    private Flashcard currentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        fh = new FirebaseHelper(db);
        Intent intent = getIntent();
        String deckID = intent.getStringExtra("deckID");
        // get the currently studied deck here
        // also get the current card too
        String[] deckInfo = fh.getDeckInfo(deckID);
        deck = new Deck(deckInfo[0]);
        Flashcard[] cards = fh.getDeckCards(deckID);
        deck.addCards(cards);
        currentCard = deck.getNextCard();
        setupLayout();
    }
    private void setupLayout() {
        question = (TextView) findViewById(R.id.question_text);
        question.setText(currentCard.getQuestion());
        answer = (TextView) findViewById(R.id.answer_text);
        answer.setText(currentCard.getAnswer());
        answer.setVisibility(View.INVISIBLE);
        revealButton = (Button) findViewById(R.id.reveal_button);
        againButton = (Button) findViewById(R.id.again_button);
        goodButton = (Button) findViewById(R.id.good_button);
        easyButton = (Button) findViewById(R.id.easy_button);
        hideButtons();
        revealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setVisibility(View.VISIBLE);
                revealButton.setVisibility(View.GONE);
                againButton.setVisibility(View.VISIBLE);
                goodButton.setVisibility(View.VISIBLE);
                easyButton.setVisibility(View.VISIBLE);
            }
        });
        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerCard(Flashcard.AGAIN);
                hideButtons();
            }
        });
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerCard(Flashcard.GOOD);
                hideButtons();
            }
        });
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerCard(Flashcard.EASY);
                hideButtons();
            }
        });
    }
    private void answerCard(int exp) {
        // note, finish when deck is exhausted
        currentCard.answered(exp);
        currentCard = deck.getNextCard();
        if(currentCard == null) {
            finish();
        }
        question.setText(currentCard.getQuestion());
        answer.setText(currentCard.getAnswer());
        answer.setVisibility(View.INVISIBLE);
    }

    private void hideButtons() {
        againButton.setVisibility(View.GONE);
        goodButton.setVisibility(View.GONE);
        easyButton.setVisibility(View.GONE);
        revealButton.setVisibility(View.VISIBLE);
    }
}