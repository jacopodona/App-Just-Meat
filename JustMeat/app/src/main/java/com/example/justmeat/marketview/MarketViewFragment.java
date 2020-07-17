package com.example.justmeat.marketview;

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
    public int visualizeProduct = 3;
    public ArrayList<ProductItem> pList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketview,container, false);
        setCategoryBar(view);
        if(pList.isEmpty()){
            getProduct(view);
        } else {
            showProduct(view,3);
        }
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
    private void setView(final View view) {
        final ImageView viewmode = view.findViewById(R.id.marketview_btn_viewmode);
        viewmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewmode.isActivated()){
                    visualizeProduct = 3;
                    viewmode.setActivated(false);
                    viewmode.setSelected(false);
                } else if (viewmode.isSelected()) {
                    visualizeProduct = 1;
                    viewmode.setActivated(true);
                } else {
                    visualizeProduct = 2;
                    viewmode.setSelected(true);
                }
                showProduct(view, visualizeProduct);
                System.out.println(visualizeProduct);
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
    private void showProduct(View view, int column){

        RecyclerView pRV;
        RecyclerView.Adapter pRVA;
        RecyclerView.LayoutManager pRVLM;

        pRV = view.findViewById(R.id.marketview_rv_product);
        pRV.setHasFixedSize(true);
        pRV.setNestedScrollingEnabled(false);

        if(column>1){
            pRVLM = new GridLayoutManager(getContext(), column);
            pRVA = new MarketViewProductGridAdapter(this);
        } else{
            pRVLM= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false );
            pRVA = new MarketViewProductListAdapter(this);
        }



        pRV.setLayoutManager(pRVLM);
        pRV.setAdapter(pRVA);
    }
    private void getProduct(final View view){
        new HttpJsonRequest(getContext(), "/api/v1/get_products/4" , Request.Method.GET, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseProduct(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showProduct(view,3);
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

        for (int i = 0; i< results.length(); i++){
            double prezzo;
            String nome;
            int categoria;
            JSONObject currentJSONObj = results.getJSONObject(i);
            prezzo = currentJSONObj.getDouble("price");
            nome = currentJSONObj.getString("name");
            categoria = currentJSONObj.getInt("department");
            pList.add(new ProductItem(prezzo, nome, 1));
        }

    }
}
