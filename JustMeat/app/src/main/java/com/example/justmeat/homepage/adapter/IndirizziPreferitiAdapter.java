package com.example.justmeat.homepage.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.homepage.IndirizzoPreferito;
import com.example.justmeat.homepage.MieiOrdini;

import java.util.List;

public class IndirizziPreferitiAdapter extends RecyclerView.Adapter<IndirizziPreferitiHolder> {

    private List<IndirizzoPreferito> listaIndirizziPreferiti;
    Context context;

    public IndirizziPreferitiAdapter(List<IndirizzoPreferito> listaIndirizziPreferiti) {
        this.listaIndirizziPreferiti = listaIndirizziPreferiti;
    }

    @NonNull
    @Override
    public IndirizziPreferitiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_indirizzirpeferiti, parent, false);//era shr_product_card
        context=parent.getContext();
        return new IndirizziPreferitiHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndirizziPreferitiHolder holder, final int position) {
        if (listaIndirizziPreferiti != null && position < listaIndirizziPreferiti.size()) {
            IndirizzoPreferito indirizzoPreferito = listaIndirizziPreferiti.get(position);

            holder.indirizzo.setText(indirizzoPreferito.getIndirizzo());
            holder.nomeIndirizzo.setText(indirizzoPreferito.getNome());

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(context).
                            setTitle("Conferma Rimozione")
                            .setMessage("Vuoi veramente rimuovere l'indirizzo preferito?")
                            .setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listaIndirizziPreferiti.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, listaIndirizziPreferiti.size());
                                }
                            })
                            .setNeutralButton("Annulla",null);
                    dialog=builder.create();
                    dialog.show();
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
                    dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setBackgroundColor(Color.WHITE);

                }
            });
         }
    }

    @Override
    public int getItemCount() {
        return listaIndirizziPreferiti.size();
    }
}
