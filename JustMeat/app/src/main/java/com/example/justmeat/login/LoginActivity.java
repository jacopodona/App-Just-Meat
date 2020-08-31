package com.example.justmeat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.signup.SignupActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialButton accedi = findViewById(R.id.login_button_send);
        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject body = new JSONObject();
                    body.put("mail", "davide.farina@gmail.com");
                    body.put("psw", "ciscocisco");

                    new HttpJsonRequest(getBaseContext(), "/auth/login/local", Request.Method.POST, body,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                    intent.putExtra("user", response.toString());
                                    startActivity(intent);
                                    finish();
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
