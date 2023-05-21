package com.example.deckadence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deckadence.deck.Deck;
import com.example.deckadence.deck.Flashcard;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StudyActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseHelper fb;
    private TextView question;
    private TextView answer;
    private Button revealButton;
    private Button againButton;
    private Button goodButton;
    private Button easyButton;
    private int cardPointer;
    private Flashcard currentCard;
    private ArrayList<Flashcard> cards;
    private ArrayList<String> cardIDs;
    private String deckID;
    boolean waiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        fb = new FirebaseHelper(db);
        Intent intent = getIntent();
        deckID = intent.getStringExtra("deckID");
        cards = fb.getCards(deckID);
        cardIDs = fb.getCardIds(deckID);
        cardPointer = 0;
        waiting = true;
        setupLayout();
    }
    private void setupLayout() {
        question = (TextView) findViewById(R.id.question_text);
        question.setText(R.string.waiting);
        answer = (TextView) findViewById(R.id.answer_text);
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
        currentCard = new Flashcard((String) question.getText(),"",0);
        question.setText(currentCard.getQuestion());
        answer.setText(currentCard.getAnswer());
    }
    private void answerCard(long exp) {
        // note, finish when deck is exhausted
        currentCard.answered(exp);
        //not for the first card
        if(!waiting) {
            fb.updateCard(deckID, cardIDs.get(cardPointer), currentCard);
            if (cardPointer < cards.size()) {
                Log.d("DEBUG","cardpointer:" +  cardPointer);
                Log.d("DEBUG","cards.size():" +  cards.size());
                currentCard = cards.get(cardPointer);
                cardPointer++;
            } else {
                Log.d("DEBUG","finished study");
                finish();
            }
        } else {
            currentCard = cards.get(cardPointer);
            cardPointer++;
            waiting = false;
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