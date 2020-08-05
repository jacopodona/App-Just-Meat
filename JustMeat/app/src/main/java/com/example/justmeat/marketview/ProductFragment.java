package com.example.justmeat.marketview;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    ProductItem prodotto;
    int counter = 1;
    int actualWeight;
    TextView prezzo;
    double actualPrezzo;

    ProductFragment(ProductItem prodotto){
        this.prodotto = prodotto;
        actualWeight = prodotto.getWeight();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prodotto, container, false);
        setLayout(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MarketViewActivity marketViewActivity = (MarketViewActivity) getActivity();
        marketViewActivity.marketImage.setImageResource(R.drawable.tagliata);
    }

    private void setLayout(View view){
        if(prodotto.getAllWeights().isEmpty()){
            getWeights(view);
        } else{
            setWeight(view, prodotto.allWeights);
        }

        TextView manufacturer = view.findViewById(R.id.marketview_txt_produttore);
        manufacturer.setText(prodotto.getManufacturer());

        TextView desctiption = view.findViewById(R.id.marketview_txt_descrizione);
        desctiption.setText(prodotto.getDescription());

        TextView nome = view.findViewById(R.id.marketview_txt_prodotto);
        nome.setText(prodotto.getNome());

        prezzo = view.findViewById(R.id.marketview_txt_prezzo);
        updatePrezzo(actualWeight);

        final TextView txt_qt = view.findViewById(R.id.marketview_txt_qt);
        txt_qt.setText(""+counter);

        ImageView more = view.findViewById(R.id.marketview_btn_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                txt_qt.setText(""+counter);
            }
        });

        ImageView less = view.findViewById(R.id.marketview_btn_less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>1){
                    counter--;
                    txt_qt.setText(""+counter);
                }
            }
        });

        final ImageView love = view.findViewById(R.id.marketview_btn_pref);

        if(prodotto.pref){
            love.setSelected(true);
        } else{
            love.setSelected(false);
        }

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prodotto.pref){
                    love.setSelected(false);
                    prodotto.pref = false;
                    //informazione da salvare
                } else {
                    love.setSelected(true);
                    prodotto.pref = true;
                }
            }
        });

        CardView aggiungi = view.findViewById(R.id.marketview_btn_add);
        aggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = false;
                MarketViewActivity marketViewActivity = (MarketViewActivity) ProductFragment.super.getActivity();
                for(ProductItem currentItem : marketViewActivity.carrello){
                    if(currentItem.getId() == prodotto.getId() && currentItem.getWeight() == actualWeight){
                        currentItem.qt += counter;
                        check = true;
                    }
                } if(!check){
                    if (prodotto.getWeight() == actualWeight){
                        prodotto.qt = counter;
                        marketViewActivity.carrello.add(prodotto);
                    } else {
                         ProductItem actualItem = new ProductItem(prodotto.getId(), prodotto.getNome(), actualPrezzo, prodotto.getDiscount(), prodotto.getDescription(), prodotto.getCategoria(), prodotto.getManufacturer(), prodotto.getUm(), actualWeight);
                         actualItem.qt = counter;
                         marketViewActivity.carrello.add(actualItem);

                    }
                }
            }
        });
    }

    private void setWeight(View view, ArrayList<Integer> weights) {
        RecyclerView rv = view.findViewById(R.id.marketview_rv_weight_product);
        RecyclerView.LayoutManager wRVLM= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.Adapter wRVA = new WeightAdapter(weights, prodotto.getUm(), this);
        rv.setAdapter(wRVA);
        rv.setLayoutManager(wRVLM);
    }

    private void getWeights(final View view){
        new HttpJsonRequest(getContext(), "/api/v1/get_weights/" + prodotto.getId(), Request.Method.GET, ((MyApplication) this.getActivity().getApplication()).getHttpToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
                    try {
                       setWeight(view, parseWeight(response)); ;
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error.toString());
            }
        }).run();
    }

    private ArrayList<Integer> parseWeight(JSONObject jsonObject) throws JSONException {
        ArrayList<Integer> retArray = new ArrayList<>();
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i<results.length(); i++){
            JSONObject currentJSONObj = results.getJSONObject(i);
            retArray.add(currentJSONObj.getInt("value"));
        }
        return retArray;
    }

    public void updatePrezzo(int newPeso){
        actualPrezzo = prodotto.getPrezzo() * newPeso / prodotto.getWeight();

        actualWeight = newPeso;
        if(prodotto.getDiscount()>0){
            double discountPrezzo = actualPrezzo*(1-prodotto.getDiscount());
            String defaultPrezzo = String.format("%.2f",actualPrezzo)+" €";
            SpannableString stringPrezzo = new SpannableString(defaultPrezzo + " " +String.format("%.2f",discountPrezzo)+" €" );
            stringPrezzo.setSpan(new StrikethroughSpan(), 0, defaultPrezzo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            prezzo.setText(stringPrezzo);
        } else {
            prezzo.setText(String.format("%.2f",prodotto.getPrezzo())+" €");
        }
    }

}
