package com.example.deckadence;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean loggedIn;
    private GoogleSignInAccount account;
    private Button homeButton;
    private Button decksButton;
    private Button addButton;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = GoogleSignIn.getLastSignedInAccount(this);
        loggedIn = (account != null);
        if(!loggedIn) { // also check for internet maybe
            MainActivity.this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        layoutSetup();
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