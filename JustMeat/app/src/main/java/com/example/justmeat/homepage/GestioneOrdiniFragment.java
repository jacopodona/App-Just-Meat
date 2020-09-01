package com.example.justmeat.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.Java.OrdineSupermercato;
import com.example.justmeat.homepage.adapter.OrdineSupermercatoAdapter;

import java.util.ArrayList;
import java.util.Random;

public class GestioneOrdiniFragment extends Fragment {
    @Nullable

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gestione_ordini,container,false);

        Random rand=new Random();
        ArrayList<OrdineSupermercato> arrayList=new ArrayList<OrdineSupermercato>();
        arrayList.add(new OrdineSupermercato("101","5"));
        arrayList.add(new OrdineSupermercato("102","6"));
        arrayList.add(new OrdineSupermercato("103","18"));
        arrayList.add(new OrdineSupermercato("104","7"));
        arrayList.add(new OrdineSupermercato("105","10"));
        arrayList.add(new OrdineSupermercato("106","15"));
        arrayList.add(new OrdineSupermercato("107","9"));

        recyclerView=view.findViewById(R.id.homepage_recyclerview_gestioneordini);
        layoutManager=new LinearLayoutManager(getContext());
        adapter=new OrdineSupermercatoAdapter(arrayList,getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
