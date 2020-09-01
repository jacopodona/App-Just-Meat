package com.example.justmeat.homepage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.ListaSupermercatiAdapter;

import java.util.LinkedList;
import java.util.List;

public class TrovaSupermercatiFragment extends Fragment {

    private static final int REQUEST_CODE_LOCATION_PERMISSION=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trova_supermercati,container,false);


        SeekBar seekbar= (SeekBar) view.findViewById(R.id.homepage_seekbar_distanza);
        final TextView textView= (TextView) view.findViewById(R.id.homepage_textview_distanza);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(""+progress+" km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        List lista= new LinkedList();
        Supermercato m= new Supermercato("Aldi","Via Roma 12b");
        lista.add(m);
        for (int i=10 ; i>0;i--){
            m= new Supermercato("Aldi","Via Roma 12b");
            lista.add(m);
        }


        final RecyclerView recyclerView = view.findViewById(R.id.homepage_recyclerview_listasupermercati);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);

        //RecyclerView.LayoutManager layoutManager = (LinearLayout)view.findViewById(R.id.homepage_linearlayout_listasupermercati);

        recyclerView.setLayoutManager(layoutManager);



        final ListaSupermercatiAdapter adapter = new ListaSupermercatiAdapter(lista, getActivity());
        recyclerView.setAdapter(adapter);
        //recyclerView.setNestedScrollingEnabled(false);

        ImageButton searchOnMapsButton = view.findViewById(R.id.homepage_imagebutton_search_on_maps);
        searchOnMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.clear();
                //((HomepageActivity)getActivity()).navigateTo(new MapFragment(),true);


                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);

                }else{
                    getCurrentLocation();
                }

                /*Intent i = new Intent(getActivity(), MapFragment.class);
                getActivity().startActivity(i);*/

            }


        });


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else{
                Toast.makeText(getContext(), "permissio denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation(){
        Intent i = new Intent(getActivity(), MapFragment.class);
        i.putExtra("Latitudine", "45.4420061");
        i.putExtra("Longitudine", "10.9954850");
        i.putExtra("NomeSupermercato", "Ciao");
        getActivity().startActivity(i);
    }
}
