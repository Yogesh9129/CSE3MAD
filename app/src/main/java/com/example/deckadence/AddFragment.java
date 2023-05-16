package com.example.deckadence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.deckadence.deck.Deck;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    FirebaseFirestore db;

    FirebaseHelper fb;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private Button addDeckButton;

    private EditText titleEditText;

    private EditText descriptionEditText;

    private GoogleSignInInterface googleSignInInterface;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //interface used for getting the user of the current google account (autehnticated user)

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            googleSignInInterface = (GoogleSignInInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement GoogleSignInInterface");
        }
    }
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(getLayoutInflater(), (ViewGroup) view, savedInstanceState);

        setupLayout();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance();

        fb = new FirebaseHelper(db);

        view = inflater.inflate(R.layout.fragment_add, container, false);

        titleEditText =  (EditText) view.findViewById(R.id.add_title);
        descriptionEditText = (EditText) view.findViewById(R.id.add_description);
        addDeckButton = (Button) view.findViewById(R.id.add_deck);

        setupLayout();

        return view;
    }

    private void setupLayout() {

        addDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GoogleSignInAccount googleSignInAccount = googleSignInInterface.getGoogleSignIn();

                Deck d = new Deck(titleEditText.getText().toString(), descriptionEditText.getText().toString(), googleSignInAccount.getId().toString());

                fb.addDeck(d);


                Log.d("DEBUG", d.toString());
                Boolean hasDecks = false;
                //Intent addInt = new Intent(getActivity().getApplicationContext(), com.example.deckadence.deck.Deck.class);
                //startActivity(addInt);
            }
        });
    }

    public interface GoogleSignInInterface {
        GoogleSignInAccount getGoogleSignIn();
    }

}