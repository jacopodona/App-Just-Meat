package com.example.justmeat.homepage;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String httpToken;
    private User utente;
    private  NavigationView navigationview;
    private Toolbar toolbar;
    private String nomeFile="user.json";
    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        input = intent.getStringExtra("user");
        String name="";
        String last_name="";
        String mail="";
        int id=0;
        try {
            JSONObject jsonObject = new JSONObject(input);
            id = jsonObject.getJSONObject("user").getInt("id");
            name = jsonObject.getJSONObject("user").getString("name");
            last_name = jsonObject.getJSONObject("user").getString("last_name");
            mail = jsonObject.getJSONObject("user").getString("mail");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        salvaUtenteInLocale();

        utente=new User(id,name,last_name,mail);

        ((MyApplication)getApplication()).setUtente(utente);


        toolbar = findViewById(R.id.homepage_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        inizializzaNavigationView();
        caricaInfoHeader();




        // Get httpToken
        JSONObject user = null;
        this.httpToken = null;
        try {
            JSONObject rawData = new JSONObject(getIntent().getStringExtra("user"));
            user = new JSONObject(rawData.getJSONObject("user").toString());
            httpToken = rawData.getString("token");
            ((MyApplication) this.getApplication()).setHttpToken(httpToken);
        } catch (JSONException ex) {
            Log.d("asd", ex.toString());
        }

        Log.d("asd", user.toString());
        Log.d("asd", httpToken);
        Log.d("asd", "----------------------- Test autenticazione -----------------------");
        new HttpJsonRequest(getBaseContext(), "/authenticationtest", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("asd", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("asd", error.toString());
                    }
                }).run();




        if (savedInstanceState == null) {//serve per mantenere il fragment corretto aperto in caso l'activity venga ricostruita (es: cambio di orientamento distrugge e ricrea activity)
            //inizializzo il primo fragment a trova supermercati
            getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new TrovaSupermercatiFragment(httpToken)).commit();
            navigationview.setCheckedItem(R.id.homepage_nav_trova_supermercati);
            getSupportActionBar().setTitle("Trova Supermercati");
        }


    }

    private void salvaUtenteInLocale() {
        try {
            FileOutputStream fOut = openFileOutput(nomeFile, Context.MODE_PRIVATE);
            fOut.write(input.toString().getBytes());
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cancellaUtenteLocale(){
        try {
            FileOutputStream fOut = openFileOutput(nomeFile, Context.MODE_PRIVATE);
            fOut.write("".getBytes());
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inizializzaNavigationView() {
        drawer = findViewById(R.id.homepage_drawer_layout);
        navigationview = findViewById(R.id.homepage_nav_view);
        navigationview.setNavigationItemSelectedListener(this);//ho aggiunto implements per aggiungere il listener per il cambio di fragment

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void caricaInfoHeader() {
        View header = navigationview.getHeaderView(0);
        TextView nome,mail;
        nome=(TextView) header.findViewById(R.id.homepage_textview_nomecognome);
        mail=(TextView) header.findViewById(R.id.homepage_textview_email);
        nome.setText(utente.getName()+" "+utente.getLast_name());
        mail.setText(utente.getMail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {//metodo che gestisce il cambio fragment
        switch (menuItem.getItemId()) {
            case R.id.homepage_nav_trova_supermercati:
                //getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new TrovaSupermercatiFragment()).commit();
                getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                navigateTo(new TrovaSupermercatiFragment(httpToken),true);
                toolbar.setTitle("Trova Supermercati");
                break;
            case R.id.homepage_nav_miei_ordini:
                //getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new MieiOrdiniFragment(httpToken)).commit();

                getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                navigateTo(new MieiOrdiniFragment(httpToken),true);
                toolbar.setTitle("Miei Ordini");
                break;
            case R.id.homepage_nav_ordini_preferiti:
                //getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new OrdiniPreferitiFragment(getDataOrdiniPreferiti())).commit();

                getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                navigateTo(new OrdiniPreferitiFragment(httpToken),true);
                toolbar.setTitle("Ordini Preferiti");
                break;
            case R.id.homepage_nav_indirizzi_preferiti:
                //getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new IndirizziPreferitiFragment()).commit();

                getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                navigateTo(new IndirizziPreferitiFragment(httpToken),true);
                toolbar.setTitle("Indirizzi Preferiti");
                break;
            case R.id.homepage_nav_logout:
                showLogoutDialog();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Sei sicuro di voler eseguire il logout?");
        builder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancellaUtenteLocale();
                finish();
            }
        });
        builder.setNeutralButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {//override del back button per chiudere il menu drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(getSupportFragmentManager().getBackStackEntryCount()== 0){
            showLogoutDialog();
        } else if(getSupportFragmentManager().getBackStackEntryCount()== 1){
            navigationview.setCheckedItem(R.id.homepage_nav_trova_supermercati);
            toolbar.setTitle("Trova Supermercati");
            super.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }


    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(fragment.getClass().getName())
                        .replace(R.id.homepage_fragment_container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public ArrayList<JSONObject> getDataOrdiniPreferiti(){
        final ArrayList ordiniPreferitiList = new ArrayList<JSONObject>();
        new HttpJsonRequest(getBaseContext(), "/api/v1/get_favourite_orders", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Fav Order", response.toString());

                        try {
                            int numElementi=(response.getJSONObject("metadata").getInt("returned"));
                            for(int i=0; i<numElementi;i++){
                                try {
                                    ordiniPreferitiList.add(response.getJSONArray("results").getJSONObject(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("asd", error.toString());
                    }
                }).run();

        return ordiniPreferitiList;
    }

    public String getHttpToken(){
        return this.httpToken;
    }


}
