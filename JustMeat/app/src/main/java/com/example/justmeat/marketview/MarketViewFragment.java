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

import com.example.justmeat.R;

import java.util.ArrayList;

public class MarketViewFragment extends Fragment {
    public int activeFilter = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketview,container, false);
        setCategoryBar(view);
        showProduct(view);
        setView(view);
        return view;
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
        ArrayList<ProductItem> pList = getProduct();

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

    private ArrayList<ProductItem> getProduct(){
        ArrayList<ProductItem> pList = new ArrayList<>();
        pList.add(new ProductItem( 14.32, "Tagliata di Manzo",5));
        pList.add(new ProductItem(14.32, "Tagliata di Manzo",0));
        pList.add(new ProductItem( 14.32, "Tagliata di Manzo",5));
        pList.add(new ProductItem(14.32, "Tagliata di Manzo",0));
        pList.add(new ProductItem( 14.32, "Tagliata di Manzo",5));
        pList.add(new ProductItem(14.32, "Tagliata di Manzo",0));
        pList.add(new ProductItem( 14.32, "Tagliata di Manzo",5));
        pList.add(new ProductItem(14.32, "Tagliata di Manzo",0));
        pList.add(new ProductItem( 14.32, "Tagliata di Manzo",5));
        pList.add(new ProductItem(14.32, "Tagliata di Manzo",0));
        return pList;
    }
}
