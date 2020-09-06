package com.example.justmeat.homepage.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.homepage.IndirizzoPreferito;
import com.example.justmeat.homepage.ModificaIndirizzoPreferitoFragment;
import com.example.justmeat.utilities.HttpJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class IndirizziPreferitiAdapter extends RecyclerView.Adapter<IndirizziPreferitiHolder> {

    private List<IndirizzoPreferito> listaIndirizziPreferiti;
    Context context;
    private Activity activity;

    public IndirizziPreferitiAdapter(List<IndirizzoPreferito> listaIndirizziPreferiti, Activity activity) {
        this.listaIndirizziPreferiti = listaIndirizziPreferiti;
        this.activity= activity;
    }

    @NonNull
    @Override
    public IndirizziPreferitiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_indirizzirpeferiti, parent, false);//era shr_product_card
        context = parent.getContext();
        return new IndirizziPreferitiHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndirizziPreferitiHolder holder, final int position) {
        if (listaIndirizziPreferiti != null && position < listaIndirizziPreferiti.size()) {
            final IndirizzoPreferito indirizzoPreferito = listaIndirizziPreferiti.get(position);

            holder.indirizzo.setText(indirizzoPreferito.getIndirizzo());
            holder.nomeIndirizzo.setText(indirizzoPreferito.getNome());

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog;
                    AlertDialog.Builder builder=new AlertDialog.Builder(context).
                            setTitle("Conferma Rimozione")
                            .setMessage("Vuoi veramente rimuovere l'indirizzo preferito?")

                            .setNeutralButton("Annulla",null)
                    .setPositiveButton("Conferma", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delIndirizzoPreferito(listaIndirizziPreferiti.get(position));
                            listaIndirizziPreferiti.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, listaIndirizziPreferiti.size());

                        }
                    });
                    dialog=builder.create();
                    dialog.show();

                    ColorStateList myColorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{android.R.attr.state_pressed} //1
                            },
                            new int[] {
                                    Color.WHITE//1
                            }
                    );
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundTintList(myColorStateList);
                    dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setBackgroundColor(Color.WHITE);

                }
            });

            holder.modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomepageActivity) activity).navigateTo(new ModificaIndirizzoPreferitoFragment(((HomepageActivity) activity).getHttpToken(),indirizzoPreferito),true);
                }
            });
         }
    }

    @Override
    public int getItemCount() {
        return listaIndirizziPreferiti.size();
    }

    public void delIndirizzoPreferito(IndirizzoPreferito indirizzoPreferito){
        JSONObject body=new JSONObject();
        try {
            body.put("address_id", indirizzoPreferito.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpJsonRequest(activity.getBaseContext(), "/api/v1/del_favourite_address", Request.Method.POST,body, ((HomepageActivity)activity).getHttpToken(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("OK", response.toString());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Err connessione", error.toString());

                    }
                }).run();
    }
}
