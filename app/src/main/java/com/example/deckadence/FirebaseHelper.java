package com.example.deckadence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.deckadence.deck.Flashcard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class FirebaseHelper {
    private FirebaseFirestore db;
    private String TAG = "FireStore";

    public FirebaseHelper(FirebaseFirestore db) {
        this.db = db;
    }
    // returns string array with deck title and description
    public String[] getDeckInfo(String deckID) {
        DocumentReference deckRef = db.collection("Deck").document(deckID);
        String[] returnArray = new String[2];
        Log.d(TAG, "d");
        deckRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Deck exists");
                        returnArray[0] = (String) document.getData().get("title");
                        returnArray[1] = (String) document.getData().get("description");
                    } else {
                        Log.d(TAG, "No such deck");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        if(returnArray[0] != null) {
            return returnArray;
        } else {
            return null;
        }
    }
    public Flashcard[] getDeckCards(String deckID) {
        db.collection("Deck")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

            return null;
    }
}
