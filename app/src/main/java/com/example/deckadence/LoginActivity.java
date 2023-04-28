package com.example.deckadence;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    //TODO, add anon login
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private TextView title;
    //private EditText username;
    //private EditText password;
    //private Button login_button;
    private Button googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){
            // suppress
        }


        setContentView(R.layout.activity_login);
        // set up layout
        layoutSetup();
    }

    private void layoutSetup() {
        title = findViewById(R.id.deckadence_title);
        //username = findViewById(R.id.username);
        //password = findViewById(R.id.password);
        //login_button = findViewById(R.id.login_button);
        googleLogin =  findViewById(R.id.login_with_google);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent = googleSignInClient.getSignInIntent();
                    startActivityForResult(intent, 100);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                task.getResult(ApiException.class);
                finish();
            } catch (ApiException e) {
                // error should probably be more specific
                Log.d("deckadence", e.toString());
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
