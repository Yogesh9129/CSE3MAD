package com.example.deckadence;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements AddFragment.GoogleSignInInterface, DecksFragment.GoogleSignInInterface {

    private FirebaseFirestore db;
    private boolean loggedIn;
    private GoogleSignInAccount account;
    private Button homeButton;
    private Button decksButton;
    private Button addButton;
    private FragmentManager fragmentManager;
    @Override
    public GoogleSignInAccount getGoogleSignIn() {
        return account;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) Log.d("DEBUG", account.getGivenName());
        db = FirebaseFirestore.getInstance();
        FirebaseHelper fb = new FirebaseHelper(db);
        loggedIn = (account != null);
        if(!loggedIn) { // also check for internet maybe
            MainActivity.this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        //getDecksByID(); //debug method for getting decks by id
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        //fb.getCards("sample");
        layoutSetup();
    }

    private void getDecksByID() {
            String id = "";
            if(account != null) {
                id = account.getId();
            }

            db.collection("Deck").whereEqualTo("token", "106100293717197796061").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("DEBUG", document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.d("DEBUG", "Error getting documents: ", task.getException());
                            }
                        }
                    });

    }

    protected GoogleSignInAccount getLoggedInAccount() {
        return this.account;
    }
    private void layoutSetup() {
        homeButton = (Button) findViewById(R.id.home_button);
        decksButton = (Button) findViewById(R.id.decks_button);
        addButton = (Button) findViewById(R.id.add_button);
        // default to home view
        switchFragment(HomeFragment.class);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(HomeFragment.class);
            }
        });
        decksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(DecksFragment.class);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFragment(AddFragment.class);
            }
        });
    }
    public void switchFragment(Class<? extends Fragment> fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment, null);
        transaction.commit();
    }
}