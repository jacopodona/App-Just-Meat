package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.InfoCardMappe;
import com.example.justmeat.marketview.MarketViewActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class CardMappaAdaprter implements GoogleMap.InfoWindowAdapter{

    private Context context;
    private GoogleMap mMap;

    public CardMappaAdaprter(Context context, GoogleMap mMap) {
        this.context = context;
        this.mMap = mMap;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.card_dettagli_supermercato_mappe, null);

        CardView cardView=view.findViewById(R.id.layout_info_mappe);
        TextView name_tv = view.findViewById(R.id.titolo_info_mappe);
        TextView address_tv = view.findViewById(R.id.indirizzo_info_mappe);
        MaterialButton apri=view.findViewById(R.id.apri_info_mappe);
        ImageView img = view.findViewById(R.id.image_info_mappe);

        final InfoCardMappe infoCardMappe = (InfoCardMappe) marker.getTag();

        if (infoCardMappe != null) {
            name_tv.setText(infoCardMappe.getNomeSupermercato());
            address_tv.setText(infoCardMappe.getIndirizzo());
            int imageId = context.getResources().getIdentifier(infoCardMappe.getImage().toLowerCase(),
                    "drawable", context.getPackageName());
            img.setImageResource(imageId);
        }
        else{
            name_tv.setText("La tua posizione");
            address_tv.setVisibility(View.GONE);
            apri.setVisibility(View.GONE);
            img.setImageResource(R.drawable.user);
        }

        /*cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MarketViewActivity.class);
                i.putExtra("idSupermercato",infoCardMappe.getId());
                context.startActivity(i);
            }
        });*/

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MarketViewActivity.class);
                i.putExtra("idSupermercato",infoCardMappe.getId());
                context.startActivity(i);
            }
        });*/


        return view;
    }
}
