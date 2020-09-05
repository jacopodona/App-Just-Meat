package com.example.justmeat.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;
import com.example.justmeat.homepage.adapter.ProdottoPrezzoAdapter;
import com.example.justmeat.homepage.adapter.ProdottoPrezzoOrdinePreferitoAdapter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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


return view;
    }
}