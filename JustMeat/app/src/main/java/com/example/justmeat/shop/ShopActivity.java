package com.example.justmeat.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.ordineSupermercato.StatoOrdineSupermercatoActivity;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrdineSupermercatoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private ImageView close;
    private ArrayList<OrdineSupermercato> listaOrdini;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //listaOrdini = new ArrayList<>();

        progressBar=findViewById(R.id.shop_loading);

        context = this;
        listaOrdini=new ArrayList<>();

        recyclerView = findViewById(R.id.shop_recyclerview_listaordini);
        layoutManager = new LinearLayoutManager(this);
        //richiestaOrdini(this);




        Log.e("Lunghezza array","Numero oggetti: "+listaOrdini.size());
        for(int i=0;i<listaOrdini.size();i++){
            Log.e("Contenuto Lista Ordini:", listaOrdini.get(i).getId()+" "+listaOrdini.get(i).getStato()+"\n");
        }







        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void richiestaOrdini(final Activity a) {
        listaOrdini=new ArrayList<>();
        new HttpJsonRequest(getBaseContext(), "/api/v1/get_orders", Request.Method.GET, "justmeat",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("OK", response.toString());
                        try {
                            JSONArray results = response.getJSONArray("results");
                            Log.e("Details-->", "Ordini visti: " + results.length());
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject ordine = results.getJSONObject(i);
                                OrdineSupermercato o = new OrdineSupermercato(ordine.getInt("id"), ordine.getString("status"));
                                listaOrdini.add(o);
                            }
                            Log.e("Details-->", "Ordini aggiunti: " + listaOrdini.size());
                            for(int i=0;i<listaOrdini.size();i++){
                                Log.e("Contenuto Lista Ordini:", listaOrdini.get(i).getId()+" "+listaOrdini.get(i).getStato()+"\n");
                            }
                            setupRecyclerView(a);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("err", error.toString());
                    }
                }).run();
    }

    void setupRecyclerView(Activity a){
        Collections.sort(listaOrdini);
        adapter = new OrdineSupermercatoAdapter(listaOrdini, a);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        adapter.setOnItemClickListener(new OrdineSupermercatoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, StatoOrdineSupermercatoActivity.class);
                OrdineSupermercato ordine = listaOrdini.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Ordine", ordine);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        richiestaOrdini(this);
    }
}