package com.example.justmeat.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.login.LoginActivity;
import com.example.justmeat.signup.SignupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {

    private String nomeFile="user.json";
    ImageView nome, logo, change_back, logoyellow;
    ConstraintLayout base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        setContentView(R.layout.activity_welcome);

        caricaUtente();


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



    private void caricaUtente() {//carica utente che ha usato l'app di recente
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String utente=readFromMemory();
        if(utente!=""){
            Intent intent=new Intent(this,HomepageActivity.class);
            intent.putExtra("user",utente);
            startActivity(intent);
            finish();
        }
    }

    public String readFromMemory(){
        String temp="";
        try {
            FileInputStream fin = openFileInput(nomeFile);
            int c;
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("Errore read from memory","Non ho trovato il file utente");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
