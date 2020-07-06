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
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;

import org.json.JSONException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MieiOrdiniFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_miei_ordini,container,false);

        List lista= new LinkedList();
        MieiOrdini m= new MieiOrdini("Aldi","Via Roma 12b", "Ritirato",new Date());
        lista.add(m);
        m= new MieiOrdini("Aldi","Via Roma 12b", "Ritirato",new Date());
        lista.add(m);

        RecyclerView recyclerView = view.findViewById(R.id.homepage_recyclerview_mieiordini);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);


        MieiOrdiniAdapter adapter = null;
        adapter = new MieiOrdiniAdapter(lista);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }
}
