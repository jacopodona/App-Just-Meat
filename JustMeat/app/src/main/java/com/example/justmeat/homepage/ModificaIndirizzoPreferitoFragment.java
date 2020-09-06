package com.example.justmeat.homepage;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class ModificaIndirizzoPreferitoFragment extends Fragment {

    private TextInputEditText nome, città, numero, via;
    private MaterialButton conferma;
    private String nomeval,indirizzoval;
    private String httpToken;

    private IndirizzoPreferito indirizzoPreferito;


    public ModificaIndirizzoPreferitoFragment(String httpToken, IndirizzoPreferito indirizzoPreferito){
        this.httpToken= httpToken;
        this.indirizzoPreferito=indirizzoPreferito;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungi_indirizzi_salvati, container, false);

        TextView titolo= view.findViewById(R.id.aggiungi_indirizzi_preferito_text);
        titolo.setText("Modifica Indirizzo");

        MaterialButton confermaButton= view.findViewById(R.id.homepage_inserisciindirizzo_conferma);
        confermaButton.setText("Conferma modifica");

        nome = view.findViewById(R.id.homepage_inserisciindirizzo_nome);
        città = view.findViewById(R.id.homepage_inserisciindirizzo_città);
        numero = view.findViewById(R.id.homepage_inserisciindirizzo_numero);
        via = view.findViewById(R.id.homepage_inserisciindirizzo_via);
        conferma = view.findViewById(R.id.homepage_inserisciindirizzo_conferma);

        nome.setText(indirizzoPreferito.getNome());
        String string="";
        int contIndirizzo=0;//indica se sta cercando la via, la città o il numero civico
        for(int i=0; i<indirizzoPreferito.getIndirizzo().length();i++){

            if(indirizzoPreferito.getIndirizzo().charAt(i)==','){
                switch (contIndirizzo){
                    case 0:
                        via.setText(string);
                        string="";
                        contIndirizzo++;
                        break;
                    case 1:
                        numero.setText(string);
                        string="";
                        contIndirizzo++;
                        break;
                    default:break;
                }
            }else if(i==(indirizzoPreferito.getIndirizzo().length()-1)){
                città.setText(string+indirizzoPreferito.getIndirizzo().charAt(i++));
            }else{
                string=string+indirizzoPreferito.getIndirizzo().charAt(i);
            }
        }

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeval = nome.getText().toString();
                indirizzoval = via.getText().toString() + ", " + numero.getText().toString() + ", " + città.getText().toString();
                if(!(TextUtils.isEmpty(nomeval)) && !(TextUtils.isEmpty(via.getText().toString())) && !(TextUtils.isEmpty(città.getText().toString())) && !(TextUtils.isEmpty(numero.getText().toString()))){//Contreolla se i campi sono compilati


                    delIndirizzoPreferito();
                    postIndirizzoPreferito();
                    getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    ((HomepageActivity)getActivity()).navigateTo(new IndirizziPreferitiFragment(httpToken), true);

                }
                else {
                    Toast.makeText(getContext(),"Compilare tutti i Campi",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    private void setIndirizzo(double longitude,double latitude){
        Log.e("Details-->",nomeval+" "+indirizzoval+" "+latitude+" "+" "+longitude);
        IndirizzoPreferito indirizzo=new IndirizzoPreferito(nomeval,indirizzoval,latitude,longitude);

        //POST AL SERVER

    }

    public void postIndirizzoPreferito(){
        JSONObject body=new JSONObject();
        try {
            body.put("name", nome.getText().toString());
            body.put("address", indirizzoval);
            body.put("latitude",46.0793);
            body.put("longitude", 11.1302);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpJsonRequest(getContext(), "/api/v1/add_favourite_address", Request.Method.POST,body, httpToken,
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

    public void delIndirizzoPreferito(){
        JSONObject body=new JSONObject();
        try {
            body.put("address_id", indirizzoPreferito.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpJsonRequest(getContext(), "/api/v1/del_favourite_address", Request.Method.POST,body, httpToken,
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