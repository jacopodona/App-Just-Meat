package com.example.justmeat.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.login.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        MaterialButton registrati = findViewById(R.id.signup_button_registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });

        MaterialButton accedi = findViewById(R.id.signup_button_accedi);
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
