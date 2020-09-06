package com.example.justmeat.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.ProdottoPrezzoOrdinePreferitoAdapter;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.MyApplication;
import com.example.justmeat.whithdrawal.WithdrawalActivity;

import java.util.ArrayList;

public class DettagliOrdinePreferitoFragment extends Fragment {

    private OrdinePreferito ordinePreferito;
    private RecyclerView recyclerView;
    private ProdottoPrezzoOrdinePreferitoAdapter adapter;

    public DettagliOrdinePreferitoFragment(OrdinePreferito ordinePreferito){
        this.ordinePreferito=ordinePreferito;
        recyclerView=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ordine_preferito,container,false);

        ImageView logo= view.findViewById(R.id.ordine_preferito_img);
        Glide.with(this.getActivity()).load("http://just-feet.herokuapp.com"+"/images/sl_"+ordinePreferito.getIdSupermercato()+".jpg")

                .into(logo);

        recyclerView = view.findViewById(R.id.ordine_preferito_rec_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProdottoPrezzoOrdinePreferitoAdapter(ordinePreferito.getListaProdotti());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        double totale=0.00;
        for (ProdottoOrdinePreferito p:ordinePreferito.getListaProdotti()) {
            totale= totale+p.getPrezzo();
        }
        TextView importo= view.findViewById(R.id.ordine_preferito_importo_value);
        importo.setText(totale+"");

        TextView nomeOrdine, nomeSupermercato;
        nomeOrdine= view.findViewById(R.id.ordine_preferito_nome_ordine);
        nomeSupermercato = view.findViewById(R.id.ordine_preferito_nome_supermercato);
        nomeOrdine.setText(ordinePreferito.getNomeOrdinePreferito());
        nomeSupermercato.setText(ordinePreferito.getNomeSupermercato());

        Button ordina = view.findViewById(R.id.homepage_btn_ordina);
        ordina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList <ProductItem> carrello = new ArrayList<>();
                for(ProdottoOrdinePreferito currentItem : ordinePreferito.getListaProdotti()){
                    carrello.add(new ProductItem(currentItem.getId(), currentItem.getNome(), currentItem.getPrezzo(), currentItem.getQuantità(), (int) currentItem.getPeso()));
                }
                ((MyApplication)getActivity().getApplication()).setCarrelloListProduct(carrello);
                Intent intent = new Intent(getActivity(), WithdrawalActivity.class);
                startActivity(intent);
            }
        });


return view;
    }
}