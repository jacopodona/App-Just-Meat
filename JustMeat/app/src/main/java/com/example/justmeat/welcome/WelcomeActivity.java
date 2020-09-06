package com.example.justmeat.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.carrello.CarrelloActivity;
import com.example.justmeat.checkout.CheckoutActivity;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.login.LoginActivity;
import com.example.justmeat.marketview.MarketViewActivity;
import com.example.justmeat.signup.SignupActivity;
import com.example.justmeat.whithdrawal.WithdrawalActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {

    private String nomeFile="user.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            try {
                JSONObject body = new JSONObject();
                body.put("mail", account.getEmail());
                body.put("id", account.getId());
                body.put("name", account.getGivenName());
                body.put("last_name", account.getFamilyName());

                new HttpJsonRequest(getBaseContext(), "/auth/login/other", Request.Method.POST, body,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                intent.putExtra("user", response.toString());
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("asd", error.toString());
                    }
                }
                ).run();
            } catch(JSONException ex) {
                return;
            }
        }
    }

    private void caricaUtente() {//carica utente che ha usato l'app di recente
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
