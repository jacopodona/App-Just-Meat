package com.example.justmeat.homepage;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar=findViewById(R.id.homepage_toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.homepage_drawer_layout);
        NavigationView navigationview= findViewById(R.id.homepage_nav_view);
        navigationview.setNavigationItemSelectedListener(this);//ho aggiunto implements per aggiungere il listener per il cambio di fragment

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {//serve per mantenere il fragment corretto aperto in caso l'activity venga ricostruita (es: cambio di orientamento distrugge e ricrea activity)
            //inizializzo il primo fragment a trova supermercati
            getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new TrovaSupermercatiFragment()).commit();
            navigationview.setCheckedItem(R.id.homepage_nav_trova_supermercati);
        }


        // Get httpToken
        JSONObject user = null;
        this.httpToken = null;
        try {
            JSONObject rawData = new JSONObject(getIntent().getStringExtra("user"));
            user = new JSONObject(rawData.getJSONObject("user").toString());
            httpToken = rawData.getString("token");
            ((MyApplication)this.getApplication()).setHttpToken(httpToken);
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


        new HttpJsonRequest(getBaseContext(), "/api/v1/get_supermarkets", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("asd", response.toString());
                        try {
                            int numElementi=(response.getJSONObject("metadata").getInt("returned"));
                            //Log.d("Num elementi ", numElementi+"");

                            List prova = new ArrayList<JSONObject>();
                            for(int i=0; i<numElementi;i++){
                                try {
                                    prova.add(response.getJSONArray("results").getJSONObject(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            /*for (Object j: prova) {
                                Log.d("Elem",j.toString());
                            }*/


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


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {//metodo che gestisce il cambio fragment
        switch(menuItem.getItemId()){
            case R.id.homepage_nav_trova_supermercati:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new TrovaSupermercatiFragment()).commit();
                break;
            case R.id.homepage_nav_miei_ordini:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new MieiOrdiniFragment(getDataOrdini())).commit();
                break;
            case R.id.homepage_nav_ordini_preferiti:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new OrdiniPreferitiFragment(getDataOrdiniPreferiti())).commit();
                break;
            case R.id.homepage_nav_indirizzi_preferiti:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new IndirizziPreferitiFragment()).commit();
                break;
            case R.id.homepage_nav_impostazioni:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new ImpostazioniFragment()).commit();
                break;
            case R.id.homepage_nav_gestione_ordini:
                getSupportFragmentManager().beginTransaction().replace(R.id.homepage_fragment_container, new GestioneOrdiniFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {//override del back button per chiudere il menu drawer
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    public LinkedList<MieiOrdini> getDataOrdini(){

        final LinkedList ordini= new <MieiOrdini>LinkedList();

        new HttpJsonRequest(getBaseContext(), "/api/v1/get_user_orders", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Orders", response.toString());

                        try {
                            int numElementi=(response.getJSONObject("metadata").getInt("returned"));
                            for(int i=0; i<numElementi;i++){
                                try {

                                    //Log.e("Ciao", response.getJSONArray("results").getJSONObject(i).get("supermarket").toString());

                                    MieiOrdini ordine = new MieiOrdini();
                                    ordine.setNomeSupermercato(response.getJSONArray("results").getJSONObject(i).get("supermarket").toString());
                                    ordine.setIndirizzo("via dasdasdasdaasa");
                                    ordine.setStato(response.getJSONArray("results").getJSONObject(i).get("status").toString());

                                    //Date
                                    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                    String date= response.getJSONArray("results").getJSONObject(i).get("pickup_time").toString();
                                    Date dateFormat = format.parse(date);
                                    ordine.setDataOrdine(dateFormat);
                                    //---------------------

                                    ordini.add(ordine);
                                    Log.e("Lunghezza iiin",ordini.size()+"");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ParseException e) {
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
                        Log.d("Err connessione", error.toString());
                    }
                }).run();




        Log.e("Lunghezza return", ordini.size()+"");
        return ordini;
    }




}
