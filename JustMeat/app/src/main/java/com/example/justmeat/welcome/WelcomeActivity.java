package com.example.justmeat.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.login.LoginActivity;
import com.example.justmeat.marketview.MarketViewActivity;
import com.example.justmeat.signup.SignupActivity;
import com.example.justmeat.whithdrawal.WithdrawalActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        Button accedi_btn = findViewById(R.id.welcome_button_accedi);
        accedi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView registrati_btn = findViewById(R.id.welcome_button_registrati);
        registrati_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
