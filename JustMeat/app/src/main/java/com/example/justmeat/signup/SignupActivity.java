package com.example.justmeat.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.login.LoginActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final TextInputLayout tNome = findViewById(R.id.nome);
        final TextInputLayout tLNome = findViewById(R.id.cognome);
        final TextInputLayout tEmail = findViewById(R.id.email);
        final TextInputLayout tPsw = findViewById(R.id.password);
        final TextInputLayout tCPsw = findViewById(R.id.confirm_password);

        (tEmail.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    tEmail.setError("Formato non corretto");
                } else {
                    tEmail.setError(null);
                }
            }
        });
        (tPsw.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<8){
                    tPsw.setError("La tua password deve essere lunga almeno 8 caratteri");
                } else {
                    tPsw.setError(null);
                }
            }
        });
        (tCPsw.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!tPsw.getEditText().getText().toString().equals(tCPsw.getEditText().getText().toString())){
                    tCPsw.setError("Le due password devono essere uguali");
                } else {
                    tCPsw.setError(null);
                }
            }
        });

        MaterialButton registrati = findViewById(R.id.signup_button_registrati);
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name, last_name, email, psw, check_psw;
                name = tNome.getEditText().getText().toString();
                last_name = tLNome.getEditText().getText().toString();
                email = tEmail.getEditText().getText().toString();
                psw = tPsw.getEditText().getText().toString();
                check_psw = tCPsw.getEditText().getText().toString();

                try {
                    JSONObject body = new JSONObject();
                    body.put("name", name);
                    body.put("last_name", last_name);
                    body.put("mail", email);
                    body.put("psw", psw);
                    body.put("check_psw", check_psw);

                    new HttpJsonRequest(getBaseContext(), "/auth/signup", Request.Method.POST, body,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if(response.getString("message").equals("OK.")) {
                                            JSONObject body = new JSONObject();
                                            body.put("mail", email);
                                            body.put("psw", psw);

                                            new HttpJsonRequest(getBaseContext(), "/auth/login/local", Request.Method.POST, body,
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
                                            }).run();
                                        } else {
                                            return;
                                        }
                                    } catch(JSONException ex) {
                                        return;
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("asd", error.toString());
                        }
                    }
                    ).run();
                } catch(JSONException ex) {
                    Log.d("asd", ex.toString());
                }
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
