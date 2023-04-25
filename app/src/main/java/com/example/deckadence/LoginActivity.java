package com.example.deckadence;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView title;
    private EditText username;
    private EditText password;
    private Button googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set up layout
        layoutSetup();
        //mAuth = FirebaseAuth.getInstance();
    }

    private void layoutSetup() {
        title = (TextView) findViewById(R.id.deckadence_title);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        googleLogin = (Button) findViewById(R.id.login_with_google);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}