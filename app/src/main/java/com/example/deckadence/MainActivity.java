package com.example.deckadence;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean loggedIn = false;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("deckadence", "Reached here");
        // check if logged in here
        if(!loggedIn) { // also check for internet
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(loginIntent);
        }
        setContentView(R.layout.activity_main);
        layoutSetup();
    }

    private void layoutSetup() {
        //this is just for testing, feel free to remove it if you need to
        test = findViewById(R.id.textView);
    }
}