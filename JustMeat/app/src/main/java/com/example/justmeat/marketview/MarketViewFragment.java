package com.example.justmeat.marketview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarketViewFragment extends Fragment {
    public int activeFilter = -1; //indica che categoria è selezionata al momento, -1 è utilizzato per indicare che nessuna cat è selezionata
    public int visualizeProduct = 3; // tipo di visualizzazione prodotti 3->griglia 3 colonne; 2-> 2colonne; 1->lista
    int id_negozio = 4; //indica quale negozio è stato selezionato, al momento la scelta è statica poi verrà utilizzato intentExtra
    public ArrayList<ProductItem> pListFull = new ArrayList<>(); //array contente tutti i prodotti del supermercato
    public ArrayList<ProductItem> pList; //array contente la lista dei prodotti filtrati attraverso la search view
    ArrayList<CategoriaItem> catList = new ArrayList<>(); //array contente i departments del supermercato
    MarketViewProductGridAdapter productGridAdapter;
    MarketViewProductListAdapter productListAdapter;
    SortModal sortModal = new SortModal(this);
    String httpToken;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView pRV;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketview,container, false);
        //create shimmmer effect
        shimmerFrameLayout = view.findViewById(R.id.marketview_shim_placeholder);
        //save user token
        this.httpToken = ((MyApplication)this.getActivity().getApplication()).getHttpToken();
        //avoid useless http call
        if(catList.isEmpty()){
            getCategory(view);
        } else {
            setCategoryBar(view);
        }
        if(pListFull.isEmpty()){
             getProduct(view);
        } else {
            showProduct(view,3);
        }

        setView(view);
        setSorting(view);
        setSearchView(view);
        setBarcodeReader(view);
        return view;
    }

    private void setBarcodeReader(View view) { //il barcode contiene il nome intero del prodotto, attraverso la stringa letta viene fatta una ricerca
        final Activity activity = this.getActivity();
        final Fragment fragment = this;
        ImageView barcodeBtn = view.findViewById(R.id.marketview_btn_barcode);
        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Inquadra il Barcode del prodotto");
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.setCameraId(0);
                intentIntegrator.forSupportFragment(fragment).initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                System.out.println("cancel");
            } else {
                ((MarketViewActivity)getActivity()).barcodeActive = true;
                searchView.setQuery(result.getContents(), true);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MarketViewActivity marketViewActivity = (MarketViewActivity) getActivity();
        Glide.with(this.getActivity())
                .load("http://just-feet.herokuapp.com/images/si_"+id_negozio+".jpg")
                .override(720, 480)
                .into(marketViewActivity.marketImage);
    }

    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }
    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmerAnimation();
    }



    private void setSearchView(View view) {
        searchView = (SearchView) view.findViewById(R.id.marketview_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (visualizeProduct > 1){
                    productGridAdapter.getFilter().filter(newText);
                } else{
                    productListAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    private void setSorting(View view) {
        final MarketViewFragment marketViewFragment = this;
        final ImageView filter_btn = view.findViewById(R.id.marketview_btn_filter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortModal.show(getActivity().getSupportFragmentManager(), "sort");
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
        RecyclerView cRV;
        RecyclerView.Adapter cRVA;
        RecyclerView.LayoutManager cRVLM;

        cRV = view.findViewById(R.id.marketview_rv_categorie);
        cRVLM= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        cRVA = new CategoriaAdapter(this);
        cRV.setLayoutManager(cRVLM);
        cRV.setAdapter(cRVA);
    }
    private void getCategory(final View view){
        new HttpJsonRequest(getContext(), "/api/v1/get_departments/" + id_negozio, Request.Method.GET, httpToken,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    parseCategory(response);
                } catch (JSONException e){
                    e.printStackTrace();
                } setCategoryBar(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }).run();
    }
    private void parseCategory(JSONObject jsonObject) throws JSONException{
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++){
            String name;
            int id;
            JSONObject currentJSONObj = results.getJSONObject(i);
            name = currentJSONObj.getString("name");
            id = currentJSONObj.getInt("id");
            switch (name){
                case "Ortofrutta" : {
                    catList.add(new CategoriaItem(R.drawable.category_verdura, name, id));
                    break;
                }
                case "Macelleria": {
                    catList.add(new CategoriaItem(R.drawable.category_carne, name, id));
                    break;
                }
                case "Pescheria" : {
                    catList.add(new CategoriaItem(R.drawable.category_pesce, name, id));
                    break;
                }
                case "Pane, pizza e sostitutivi" : {
                    catList.add(new CategoriaItem(R.drawable.category_pasta, name, id));
                    break;
                }
                /* to draw icon
                case "Formaggi, salumi e gastronomia" : {
                    catList.add(new CategoriaItem(R.drawable.category_, name, id));
                }
                case "Prodotti freschi" : {

                }
                case "Dispensa salata" : {

                }
                case "Dispensa dolce" : {

                }*/
                case "Bevande" : {
                    catList.add(new CategoriaItem(R.drawable.category_alcool, name, id));
                    break;
                }
                case "Surgelati" : {
                    catList.add(new CategoriaItem(R.drawable.category_congelati, name, id));
                    break;
                }
                default: catList.add(new CategoriaItem(R.drawable.category_carne, name, id));
            }
        }
        catList.add(new CategoriaItem(R.drawable.love, "Preferiti", 0 ));
    }

    private void showProduct(View view, int column){
        RecyclerView.LayoutManager pRVLM;

        pRV = view.findViewById(R.id.marketview_rv_product);
        pRV.setHasFixedSize(true);
        pRV.setNestedScrollingEnabled(true);

        if(column>1){
            pRVLM = new GridLayoutManager(getContext(), column);
            pRV.setAdapter(productGridAdapter);
        } else {
            pRVLM= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false );
            pRV.setAdapter(productListAdapter);
        }
        pRV.setLayoutManager(pRVLM);
        shimmerFrameLayout.setVisibility(View.GONE);
        pRV.setVisibility(View.VISIBLE);
    }
    private void getProduct(final View view){
        final MarketViewFragment marketViewFragment = this;
        new HttpJsonRequest(getContext(), "/api/v1/get_products/"+id_negozio, Request.Method.GET, httpToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseProduct(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pList = new ArrayList<>(pListFull);
                productGridAdapter = new MarketViewProductGridAdapter(marketViewFragment);
                productListAdapter = new MarketViewProductListAdapter(marketViewFragment);
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
        for (int i = 0; i < results.length(); i++){
            boolean favourite;
            double prezzo, discount;
            String nome, manufacturer, description, um, image;
            int department, id, weight, fk_weight;
            JSONObject currentJSONObj = results.getJSONObject(i);
            id = currentJSONObj.getInt("id");
            nome = currentJSONObj.getString("name");
            prezzo = currentJSONObj.getDouble("price");
            discount = currentJSONObj.getDouble("discount");
            image = currentJSONObj.getString("image");
            description = currentJSONObj.getString("description");
            department = currentJSONObj.getInt("department");
            manufacturer = currentJSONObj.getString("manufacturer");
            um = currentJSONObj.getString("um");
            weight = currentJSONObj.getInt("weight");
            fk_weight = currentJSONObj.getInt("fk_weight");
            if( currentJSONObj.optInt("fk_user", 0) == 1){
                favourite = true;
            } else favourite = false;
            pListFull.add(new ProductItem(id, nome, prezzo, discount, image, description, department, manufacturer, um, weight, fk_weight, favourite));
        }
    }
    public void filter(){
        System.out.println("category" + this.activeFilter);
        SearchView searchView = (SearchView) this.getView().findViewById(R.id.marketview_search);
        if (visualizeProduct > 1){
            productGridAdapter.getFilter().filter(searchView.getQuery());
        } else{
            productListAdapter.getFilter().filter(searchView.getQuery());
        }
    }
}
