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
import com.example.justmeat.shop.ShopActivity;
import com.example.justmeat.signup.SignupActivity;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.justmeat.utilities.Constants.GOOGLE_SIGNIN;

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
                                    Log.e("Details-->", response.toString());
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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        MaterialButton google = findViewById(R.id.google);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGNIN);
            }
        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
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
            } catch (JSONException ex) {
                return;
            }
        }

        MaterialButton accediShop=findViewById(R.id.welcome_accedi_shop);
        accediShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShopActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGNIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String name = account.getGivenName();
            String email = account.getEmail();
            String password = account.getId();
            String lastName = account.getFamilyName();

            try {
                JSONObject body = new JSONObject();
                body.put("name", name);
                body.put("last_name", lastName);
                body.put("mail", email);
                body.put("id", password);

                new HttpJsonRequest(this, "/auth/login/other", Request.Method.POST, body,
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
            } catch (JSONException ex) {
                return;
            }
        } catch (ApiException e) {
            Log.d("asd", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
