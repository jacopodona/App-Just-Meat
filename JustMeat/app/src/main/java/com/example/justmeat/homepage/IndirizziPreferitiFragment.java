package com.example.justmeat.homepage;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.example.justmeat.homepage.adapter.IndirizziPreferitiAdapter;
import com.example.justmeat.homepage.adapter.MieiOrdiniAdapter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class IndirizziPreferitiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indirizzi_preferiti,container,false);

        List lista= new LinkedList();
        IndirizzoPreferito m= new IndirizzoPreferito("Casa","Via Roma 12b");
        lista.add(m);
        m= new IndirizzoPreferito("Casa","Via Roma 12b");
        lista.add(m);

        RecyclerView recyclerView = view.findViewById(R.id.homepage_recyclerview_indirizzipreferiti);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);


        IndirizziPreferitiAdapter adapter = new IndirizziPreferitiAdapter(lista);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        ImageView imageView= view.findViewById(R.id.homepage_imageview_plusbutton_indirizzi);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomepageActivity)getActivity()).navigateTo(new AggiungiIndirizzoPreferitoFragment(), true);
            }
        });

        return view;
    }
}