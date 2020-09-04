package com.example.justmeat.homepage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.justmeat.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class AggiungiIndirizzoPreferitoFragment extends Fragment {

    private TextInputEditText nome, città, numero, via;
    private MaterialButton conferma;
    private String nomeval,indirizzoval;
    private LocationManager locationManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungi_indirizzi_salvati, container, false);

        nome = view.findViewById(R.id.homepage_inserisciindirizzo_nome);
        città = view.findViewById(R.id.homepage_inserisciindirizzo_città);
        numero = view.findViewById(R.id.homepage_inserisciindirizzo_numero);
        via = view.findViewById(R.id.homepage_inserisciindirizzo_via);
        conferma = view.findViewById(R.id.homepage_inserisciindirizzo_conferma);

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeval = nome.getText().toString();
                indirizzoval = via.getText().toString() + ", " + numero.getText().toString() + ", " + città.getText().toString();
                if(!(TextUtils.isEmpty(nomeval)) && !(TextUtils.isEmpty(via.getText().toString())) && !(TextUtils.isEmpty(città.getText().toString()))
                        && !(TextUtils.isEmpty(numero.getText().toString()))){
                    //Dalle API 23 sei forzato a controllare i permessi del GPS del manifest
                    locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{//se non hai i permessi chiedi all utente
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        },10);
                        return;
                    }
                    else {
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        setIndirizzo(longitude,latitude);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Compilare tutti i Campi",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){//controllo se utente accetta i permessi
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{//se non hai i permessi chiedi all utente
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET
                        },10);
                        return;
                    }
                    else {
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        setIndirizzo(longitude,latitude);
                    }
                }
        }
    }


    private void setIndirizzo(double longitude,double latitude){
        Log.e("Details-->",nomeval+" "+indirizzoval+" "+latitude+" "+" "+longitude);
        IndirizzoPreferito indirizzo=new IndirizzoPreferito(nomeval,indirizzoval,latitude,longitude);

        //POST AL SERVER

    }
}