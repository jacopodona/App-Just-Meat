package com.example.justmeat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.signup.SignupActivity;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialButton accedi = findViewById(R.id.login_button_send);
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });

        MaterialButton creaNuovoUtente = findViewById(R.id.login_button_crea_nuovo_account);
        creaNuovoUtente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
