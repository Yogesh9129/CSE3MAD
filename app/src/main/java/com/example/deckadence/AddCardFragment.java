package com.example.deckadence;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deckadence.deck.Flashcard;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCardFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseHelper fb;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DECK_ID = "DECK_ID";

    // TODO: Rename and change types of parameters
    private String deckID;
    private View view;
    private Button addCardButton;

    private EditText answerEditText;
    private EditText questionEditText;
    public AddCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param deckID Parameter 1.
     * @return A new instance of fragment AddCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCardFragment newInstance(String deckID) {
        AddCardFragment fragment = new AddCardFragment();
        Bundle args = new Bundle();
        args.putString(DECK_ID, deckID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deckID = getArguments().getString(DECK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        fb = new FirebaseHelper(db);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_card, container, false);
        setupLayout();

        return view;
    }

    private void setupLayout() {
        answerEditText =  (EditText) view.findViewById(R.id.add_card_question);
        questionEditText = (EditText) view.findViewById(R.id.add_card_answer);
        addCardButton = (Button) view.findViewById(R.id.add_card_button);
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flashcard card = new Flashcard(answerEditText.getText().toString(), questionEditText.getText().toString());
                fb.addCard(card, deckID);
                answerEditText.getText().clear();
                questionEditText.getText().clear();
                Toast.makeText(getContext(), "Added card", Toast.LENGTH_SHORT).show();
            }
        });
    }
}