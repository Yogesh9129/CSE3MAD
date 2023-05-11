package com.example.deckadence;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.deckadence.deck.Deck;
import com.example.deckadence.deck.Flashcard;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudyActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        // get the currently studied deck here
        setupLayout();
    }
    private void setupLayout() {
        question = (TextView) findViewById(R.id.question_text);
        answer = (TextView) findViewById(R.id.answer_text);
        answer.setVisibility(View.INVISIBLE);
        revealButton = (Button) findViewById(R.id.reveal_button);
        againButton = (Button) findViewById(R.id.again_button);
        goodButton = (Button) findViewById(R.id.good_button);
        easyButton = (Button) findViewById(R.id.easy_button);
        // hide buttons
        againButton.setVisibility(View.GONE);
        goodButton.setVisibility(View.GONE);
        easyButton.setVisibility(View.GONE);
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

            }
        });
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void answerCard(int exp) {

    }
}