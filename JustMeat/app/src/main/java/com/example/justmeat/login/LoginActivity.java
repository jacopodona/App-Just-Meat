package com.example.justmeat.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.shop.ShopActivity;
import com.example.justmeat.signup.SignupActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email, password;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email= findViewById(R.id.email);
        password = findViewById(R.id.password);

        //sezione accesso tramite google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.google);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        //normal login

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
                finish();
            }
        });

        MaterialButton accediShop=findViewById(R.id.welcome_accedi_shop);
        accediShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void updateUI(final GoogleSignInAccount account) {
        if(account != null){
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
                                intent.putExtra("google-account", account);
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
            } catch (JSONException ex) {
                return;
            }
        } else {
            System.out.println("not signed");
        }
    }

    public boolean checkEmailPassword() {
        boolean res = true;

        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            res = false;
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Errore")
                    .setMessage("Completa tutti i campi")
                    .setNegativeButton("Chiudi", null)
                    .show();

        }
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
         }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            updateUI(account);
        }catch (ApiException e){
            Log.d("error1","fail code="+e.getStatusCode());
            updateUI(null);
        }
    }
}
