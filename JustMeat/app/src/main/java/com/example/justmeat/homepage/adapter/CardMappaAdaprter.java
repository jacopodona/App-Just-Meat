package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.InfoCardMappe;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CardMappaAdaprter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private GoogleMap mMap;

    public CardMappaAdaprter(Context context, GoogleMap mMap){
        this.context= context;
        this.mMap=mMap;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.card_dettagli_supermercato_mappe, null);

        TextView name_tv = view.findViewById(R.id.titolo_info_mappe);





        InfoCardMappe infoCardMappe = (InfoCardMappe) marker.getTag();

        name_tv.setText(infoCardMappe.getNomeSupermercato());

        ImageView img = view.findViewById(R.id.image_info_mappe);
        int imageId = context.getResources().getIdentifier(infoCardMappe.getImage().toLowerCase(),
                "drawable", context.getPackageName());
        img.setImageResource(imageId);





        return view;
    }
}
