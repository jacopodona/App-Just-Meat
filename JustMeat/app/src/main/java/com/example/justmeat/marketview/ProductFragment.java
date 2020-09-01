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
import com.bumptech.glide.Glide;
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
    Weight actualWeight;
    TextView prezzo;
    double actualPrezzo;

    ProductFragment(ProductItem prodotto){
        this.prodotto = prodotto;
        actualWeight = new Weight(prodotto.getFk_weight(), prodotto.getWeight(), prodotto.getUm());
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
        Glide.with(this.getActivity())
                .load("http://just-feet.herokuapp.com"+prodotto.getImage())
                .override(720, 480)
                .into(marketViewActivity.marketImage);
    }

    private void setLayout(View view){
        getWeights(view);

        TextView manufacturer = view.findViewById(R.id.marketview_txt_produttore);
        manufacturer.setText(prodotto.getManufacturer());

        TextView desctiption = view.findViewById(R.id.marketview_txt_descrizione);
        desctiption.setText(prodotto.getDescription());

        TextView nome = view.findViewById(R.id.marketview_txt_prodotto);
        nome.setText(prodotto.getNome());

        prezzo = view.findViewById(R.id.marketview_txt_prezzo);
        updatePrezzo(new Weight(actualWeight.fk_weight, actualWeight.value, actualWeight.um));

        final TextView txt_qt = view.findViewById(R.id.marketview_txt_qt);
        txt_qt.setText(""+counter);

        final TextView totale = view.findViewById(R.id.marketview_txt_totale);
        totale.setText(String.format("%.2f",actualPrezzo*(1-prodotto.getDiscount())*counter)+" €");

        ImageView more = view.findViewById(R.id.marketview_btn_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                txt_qt.setText(""+counter);
                totale.setText(String.format("%.2f",actualPrezzo*(1-prodotto.getDiscount())*counter)+" €");
            }
        });

        ImageView less = view.findViewById(R.id.marketview_btn_less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>1){
                    counter--;
                    txt_qt.setText(""+counter);
                    totale.setText(String.format("%.2f",actualPrezzo*(1-prodotto.getDiscount())*counter)+" €");
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
                MarketViewActivity marketViewActivity = (MarketViewActivity)getActivity();
                if(prodotto.pref){
                    love.setSelected(false);
                    prodotto.pref = false;
                } else {
                    love.setSelected(true);
                    prodotto.pref = true;
                }
                if (!marketViewActivity.editFavoriteProd.remove(prodotto)){
                    marketViewActivity.editFavoriteProd.add(prodotto);
                    System.out.println("add");
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
                    if(currentItem.getId() == prodotto.getId() && currentItem.getFk_weight() == actualWeight.fk_weight){
                        currentItem.qt += counter;
                        check = true;
                    }
                } if(!check){
                    ProductItem actualItem =
                            new ProductItem(prodotto.getId(), prodotto.getNome(), actualPrezzo, prodotto.getDiscount(), prodotto.getImage(), prodotto.getDescription(), prodotto.getCategoria(), prodotto.getManufacturer(), prodotto.getUm(),
                                    actualWeight.value, actualWeight.getFk_weight(), false);
                    actualItem.qt = counter;
                    marketViewActivity.carrello.add(actualItem);
                }
            }
        });
    }

    private void setWeight(View view, ArrayList<Weight> weights) {
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
                try {

                        setWeight(view, parseWeight(response));
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

    private ArrayList<Weight> parseWeight(JSONObject jsonObject) throws JSONException {
        ArrayList<Weight> retArray = new ArrayList<>();
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i<results.length(); i++){
            JSONObject currentJSONObj = results.getJSONObject(i);
            retArray.add(new Weight(currentJSONObj.getInt("id"), currentJSONObj.getInt("value"), currentJSONObj.getString("um")));
        }
        return retArray;
    }

    public void updatePrezzo(Weight newPeso){
        actualPrezzo = prodotto.getPrezzo() * newPeso.getValue() / prodotto.getWeight();
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

    public class Weight{
        int fk_weight;
        int value;
        String um;
        Weight(int fk_weight, int value, String um){
            this.fk_weight = fk_weight;
            this.value = value;
            this.um = um;
        }

        public int getFk_weight() {
            return fk_weight;
        }

        public int getValue() {
            return value;
        }

        public String getUm() {
            return um;
        }
    }

}
