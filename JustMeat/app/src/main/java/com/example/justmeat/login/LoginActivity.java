package com.example.justmeat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.shop.ShopActivity;
import com.example.justmeat.signup.SignupActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email= findViewById(R.id.email);
        password = findViewById(R.id.password);

        MaterialButton accedi = findViewById(R.id.login_button_send);
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkEmailPassword()){

                    try {
                    /*JSONObject body = new JSONObject();
                    body.put("mail", "davide.farina@gmail.com");
                    body.put("psw", "ciscocisco");*/
                        JSONObject body = new JSONObject();
                        body.put("mail", email.getText().toString());
                        body.put("psw", password.getText().toString());


                        new HttpJsonRequest(getBaseContext(), "/auth/login/local", Request.Method.POST, body,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                        intent.putExtra("user", response.toString());
                                        Log.e("Details-->",response.toString());
                                        startActivity(intent);
                                        finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("asd", error.toString());
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Errore")
                                        .setMessage("Utente non trovato")
                                        .setNegativeButton("Chiudi", null)
                                        .show();
                            }
                        }
                        ).run();
                    } catch(JSONException ex) {
                        return;
                    }
                }


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

        MaterialButton accediShop=findViewById(R.id.welcome_accedi_shop);
        accediShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShopActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkEmailPassword(){
        boolean res= true;

        if(email.getText().toString().equals("") || password.getText().toString().equals("")){
            res=false;
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Errore")
                    .setMessage("Completa tutti i campi")
                    .setNegativeButton("Chiudi", null)
                    .show();

        }



        return res;
    }
}
