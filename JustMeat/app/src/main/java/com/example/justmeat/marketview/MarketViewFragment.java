package com.example.justmeat.marketview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarketViewFragment extends Fragment {
    public int activeFilter = -1;
    ArrayList<ProductItem> pList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketview,container, false);
        setCategoryBar(view);
        getProduct(view);
        setView(view);
        setCategoryFilter(view);
        return view;
    }

    private void setCategoryFilter(View view) {
        final ImageView filter_btn = view.findViewById(R.id.marketview_btn_filter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortModal sortModal = new SortModal();
                sortModal.show(getActivity().getSupportFragmentManager(), "filtri");
            }
        });
    }
    private void setView(View view) {
        final ImageView viewmode = view.findViewById(R.id.marketview_btn_viewmode);
        viewmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewmode.isActivated()){
                    System.out.println("1");
                    viewmode.setActivated(false);
                    viewmode.setSelected(false);
                } else if (viewmode.isSelected()) {
                    System.out.println("2");
                    viewmode.setActivated(true);
                } else {
                    System.out.println("3");
                    viewmode.setSelected(true);
                }
            }
        });
    }

    private void setCategoryBar(View view){
        ArrayList<CategoriaItem> catList = new ArrayList<>();
        catList.add(new CategoriaItem(R.drawable.category_frutta, "Frutta", 0));
        catList.add(new CategoriaItem(R.drawable.category_carne, "Carne", 1));
        catList.add(new CategoriaItem(R.drawable.category_lattine, "Lattine", 2));
        catList.add(new CategoriaItem(R.drawable.category_verdura, "Verdura", 3));
        catList.add(new CategoriaItem(R.drawable.category_pesce, "Pesce", 4));
        catList.add(new CategoriaItem(R.drawable.category_sapone, "Sapone", 5));
        catList.add(new CategoriaItem(R.drawable.category_alcool, "Bibite", 6));
        catList.add(new CategoriaItem(R.drawable.category_congelati, "Congelati", 7));
        catList.add(new CategoriaItem(R.drawable.category_condimenti, "Condimenti",8));
        catList.add(new CategoriaItem(R.drawable.category_pasta, "Pasta e Riso", 9));

        RecyclerView cRV;
        RecyclerView.Adapter cRVA;
        RecyclerView.LayoutManager cRVLM;

        cRV = view.findViewById(R.id.marketview_rv_categorie);
        cRV.setHasFixedSize(true );
        cRVLM= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        cRVA = new CategoriaAdapter(catList, this);
        cRV.setLayoutManager(cRVLM);
        cRV.setAdapter(cRVA);
    }
    private void showProduct(View view){

        System.out.println("3");

        RecyclerView pRV;
        RecyclerView.Adapter pRVA;
        RecyclerView.LayoutManager pRVLM;

        pRV = view.findViewById(R.id.marketview_rv_product);
        pRV.setHasFixedSize(true);
        pRV.setNestedScrollingEnabled(false);
        pRVLM = new GridLayoutManager(getContext(), 3);
        pRVA = new MarketViewProductAdapter(this, pList);

        pRV.setLayoutManager(pRVLM);
        pRV.setAdapter(pRVA);
    }
    private void getProduct(final View view){

        new HttpJsonRequest(getContext(), "/api/v1/get_products/2" , Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("1");
                try {
                    parseProduct(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showProduct(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }).run();

    }

    private void parseProduct(JSONObject jsonObject) throws JSONException {
        JSONArray results = jsonObject.getJSONArray("results");
        System.out.println("2");
        for (int i = 0; i< results.length(); i++){
            double prezzo;
            String nome;
            String categoria;
            JSONObject currentJSONObj = results.getJSONObject(i);
            prezzo = currentJSONObj.getDouble("price");
            nome = currentJSONObj.getString("name");
            categoria = currentJSONObj.getString("category");
            pList.add(new ProductItem(prezzo, nome, 1));
        }

    }
}
