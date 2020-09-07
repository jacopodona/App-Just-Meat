package com.example.justmeat.homepage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.adapter.ListaSupermercatiAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrovaSupermercatiFragment extends Fragment {

    private String httpToken;
    private SeekBar seekbar;
    private TextView progressText;
    private ArrayList<IndirizzoPreferito> listaIndirizzoPreferito;
    private ArrayAdapter<IndirizzoPreferito> adapterSpinner;
    private MaterialButton cercaPosizione;
    private Spinner spinner;
    private ImageButton cercaIndirizzo;
    private int range;
    private final double latitudinetrento = 46.0793;
    private final double longitudinetrento = 11.1302;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    public TrovaSupermercatiFragment(String httpToken) {
        this.httpToken = httpToken;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trova_supermercati, container, false);

        listaIndirizzoPreferito = new ArrayList<>();

        seekbar = (SeekBar) view.findViewById(R.id.homepage_seekbar_distanza);
        progressText = (TextView) view.findViewById(R.id.homepage_textview_distanza);
        cercaPosizione = view.findViewById(R.id.homepage_cercasupermercati_posizione_button);
        cercaIndirizzo = view.findViewById(R.id.homepage_cercasupermercati_indirizzi_button);
        spinner = view.findViewById(R.id.homepage_spinner_indirizzi);
        progressText.setText("" + seekbar.getProgress() + " km");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressText.setText("" + progress + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cercaPosizione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                ((HomepageActivity) getActivity()).navigateTo(new ListaSupermercatiFragment(httpToken,latitude,
                        longitude, seekbar.getProgress()*1000),true);
            }
        });

        cercaIndirizzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listaIndirizzoPreferito.size()!=0) {
                    IndirizzoPreferito selezionato = (IndirizzoPreferito) spinner.getSelectedItem();
                    Log.e("Coordinate Indirizzo", selezionato.getLatitude() + " " + selezionato.getLongitude());
                    ((HomepageActivity) getActivity()).navigateTo(new ListaSupermercatiFragment(httpToken, selezionato.getLatitude(),
                            selezionato.getLongitude(), seekbar.getProgress() * 1000), true);
                }
            }
        });

        new HttpJsonRequest(getContext(), "/api/v1/get_favourite_addresses", Request.Method.GET, httpToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("OK", response.toString());
                        try {
                            JSONArray jsonArray=response.getJSONArray("results");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject indirizzo=jsonArray.getJSONObject(i);
                                int id=indirizzo.getInt("id");
                                String nome=indirizzo.getString("name");
                                String address=indirizzo.getString("address");
                                double latitude=indirizzo.getDouble("latitude");
                                double longitude=indirizzo.getDouble("longitude");
                                listaIndirizzoPreferito.add(new IndirizzoPreferito(nome,address,latitude,longitude));
                            }
                            adapterSpinner=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,listaIndirizzoPreferito);
                            adapterSpinner.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                            spinner.setAdapter(adapterSpinner);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Err connessione", error.toString());

                    }
                }).run();


        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode== REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                //Toast.makeText(getContext(), "permissio denied", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Necessari permessi");
                builder.setMessage("Per il corretto funzionamento di questa funzione sono necessari i permessi al GPS");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        }
    }
}
