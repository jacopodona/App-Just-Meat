package com.example.justmeat.ordineSupermercato;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stato_ordine_supermercato);

        id=findViewById(R.id.statoordinesupermercato_id_ordine);
        stato=findViewById(R.id.statoordinesupermercato_stato_ordine);
        numeroProdotti=findViewById(R.id.statoordinesupermercato_num_prodotti);
        modifica=findViewById(R.id.statoordinesupermercato_modifica_button);
        mRecyclerView=findViewById(R.id.statoordinesupermercato_recyclerview_prodotti);

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

    }

    private void setupRecyclerView(){
        mAdapter=new ListaProdottiAdapter(prodottiInLista);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}