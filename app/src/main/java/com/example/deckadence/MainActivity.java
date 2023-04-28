package com.example.deckadence;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean loggedIn = false;
    private TextView test;
    private Button logoutButton;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        // googleSignInClient is not null if logged in
        loggedIn = googleSignInClient != null;
        if(!loggedIn) { // also check for internet maybe
            MainActivity.this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        setContentView(R.layout.activity_main);
        layoutSetup();
    }

    private void layoutSetup() {
        //this is just for testing, feel free to remove it if you need to
        //test = findViewById(R.id.textView);
        //logoutButton = findViewById(R.id.logout_button);
        /*logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("deckadence","logging out");
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("deckadence","logged out");
                        MainActivity.this.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                });
            }
        }); */
    }
}