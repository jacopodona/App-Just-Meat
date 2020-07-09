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
import com.example.justmeat.homepage.adapter.OrdiniPreferitiAdapter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DettagliOrdinePreferitoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ordine_preferito,container,false);





        return view;
    }
}