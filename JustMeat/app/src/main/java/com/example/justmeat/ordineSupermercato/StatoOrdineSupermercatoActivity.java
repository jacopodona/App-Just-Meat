package com.example.justmeat.ordineSupermercato;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.shop.OrdineSupermercato;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatoOrdineSupermercatoActivity extends AppCompatActivity {

    private TextView id,stato,numeroProdotti;
    private ArrayList<ProdottoInLista> prodottiInLista;
    private Button modifica;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OrdineSupermercato ordine;
    private ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stato_ordine_supermercato);

        id=findViewById(R.id.statoordinesupermercato_id_ordine);
        stato=findViewById(R.id.statoordinesupermercato_stato_ordine);
        numeroProdotti=findViewById(R.id.statoordinesupermercato_num_prodotti);
        modifica=findViewById(R.id.statoordinesupermercato_modifica_button);
        mRecyclerView=findViewById(R.id.statoordinesupermercato_recyclerview_prodotti);
        backbutton=findViewById(R.id.statoordinesupermercato_backbutton);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ordine= (OrdineSupermercato) bundle.getSerializable("Ordine");

        new HttpJsonRequest(getBaseContext(), "/api/v1/get_order/"+ordine.getId(), Request.Method.GET, "justmeat",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("OK", response.toString());
                        try {
                            JSONObject result= response.getJSONObject("results");
                            String supermarket=result.getString("supermarket");
                            SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            String dateString=result.getString("pickup_time");
                            Date date = format.parse(dateString);
                            ordine.setPickupTime(date);
                            ordine.setSupermarket(supermarket);
                            prodottiInLista=new ArrayList<>();
                            JSONArray array=result.getJSONArray("products");
                            for(int i=0;i<array.length();i++){
                                JSONObject prodotto=array.getJSONObject(i);
                                String nome=prodotto.getString("product_name");
                                Double prezzo=prodotto.getDouble("price");
                                ProdottoInLista prodottoInLista=new ProdottoInLista(nome,prezzo);
                                prodottiInLista.add(prodottoInLista);
                            }
                            //DateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");

                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                        setupRecyclerView();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("err", error.toString());
                    }
                }).run();

        /*listaProdotti=new ArrayList<>();
        listaProdotti.add(new ProdottoInLista("Ananas",2.5));
        listaProdotti.add(new ProdottoInLista("Mango",0.5));*/

        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);

        String valueId= String.valueOf(ordine.getId());
        id.setText(valueId);
        stato.setText(ordine.getStato());
        numeroProdotti.setText(ordine.getNumProdotti());

        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(StatoOrdineSupermercatoActivity.this);
                View mView= getLayoutInflater().inflate(R.layout.statoordinesupermercato_dialog_spinner,null);
                mBuilder.setTitle("Modifica Stato Ordine");
                final Spinner spinner= (Spinner) mView.findViewById(R.id.statoordinesupermercato_spinner);
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(StatoOrdineSupermercatoActivity.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.lista_stati));
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                mBuilder.setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(spinner.getSelectedItem().toString().equalsIgnoreCase("Seleziona stato...")){
                            Toast.makeText(StatoOrdineSupermercatoActivity.this,"Seleziona un valore per lo stato ordine",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            aggiornaStatoOrdine(spinner.getSelectedItem().toString());
                        }
                        dialog.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog=mBuilder.create();
                dialog.show();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void aggiornaStatoOrdine(final String stato){
        int status=0;
        switch (stato){
            case "Ricevuto":
                status=1;
                break;
            case "Pronto per il Ritiro":
                status=2;
                break;
            case "Ritirato":
                status=3;
                break;
        }
        if(status!=0){
            ordine.setStato(stato);
            try {
                JSONObject body = new JSONObject();
                body.put("order_id",ordine.getId());
                body.put("status",status);
                new HttpJsonRequest(this, "/api/v1/set_order_status", Request.Method.POST, body,"justmeat",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ordine.setStato(stato);
                                Log.e("Esito POST","Tutto bene");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Esito POST","Errore");
                    }
                }
                ).run();
            } catch(JSONException ex) {
                return;
            }
        }
        reloadInfo();
    }

    private void setupRecyclerView(){
        numeroProdotti.setText(String.valueOf(prodottiInLista.size()));
        mAdapter=new ListaProdottiAdapter(prodottiInLista);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void reloadInfo(){
        stato.setText(ordine.getStato());
    }
}