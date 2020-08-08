package com.example.justmeat.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.justmeat.R;

public class DettagliMieiOrdiniFragment  extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_stato_ordine,container,false);

        CustomProgressBar pb;
        pb=view.findViewById(R.id.homepage_customProgressBar);
        pb.setProgress(40);//test



        return view;
    }
}