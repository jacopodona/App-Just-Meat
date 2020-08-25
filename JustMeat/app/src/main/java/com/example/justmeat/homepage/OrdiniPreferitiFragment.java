package com.example.justmeat.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OrdiniPreferitiFragment extends Fragment {

    private ArrayList listaOrdiniPreferiti;

    public OrdiniPreferitiFragment(ArrayList<JSONObject> listaOrdiniPreferiti) {
        this.listaOrdiniPreferiti= listaOrdiniPreferiti;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ordini_preferiti,container,false);

        List lista= new LinkedList();
        OrdinePreferito m= new OrdinePreferito("Aldi","Via Roma 12b", "Ritirato",new Date(),"Pranzo");
        lista.add(m);
        m= new OrdinePreferito("Aldi","Via Roma 12b", "",new Date(),"Pranzo");
        lista.add(m);

        RecyclerView recyclerView = view.findViewById(R.id.homepage_recyclerview_ordinipreferiti);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);


        OrdiniPreferitiAdapter adapter =  new OrdiniPreferitiAdapter(lista, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        return view;
    }
}
