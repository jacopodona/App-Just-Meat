package com.example.justmeat.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.checkout.CheckoutActivity;
import com.example.justmeat.login.LoginActivity;
import com.example.justmeat.marketview.MarketViewActivity;
import com.example.justmeat.signup.SignupActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button marketview_btn = findViewById(R.id.main_btn_marketview);
        marketview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MarketViewActivity.class);
                startActivity(intent);
            }
        });
        Button carrello_btn = findViewById(R.id.main_btn_carrello);
        carrello_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CarrelloActivity.class);
                startActivity(intent);
            }
        });

        Button checkout_btn = findViewById(R.id.main_btn_checkout);
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        Button accedi_btn = findViewById(R.id.welcome_button_accedi);
        accedi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView registrati_btn = findViewById(R.id.welcome_button_registrati);
        registrati_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}
