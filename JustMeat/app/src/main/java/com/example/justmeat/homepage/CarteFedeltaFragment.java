package com.example.justmeat.homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.addCard.AddCardDialog;
import com.example.justmeat.homepage.adapter.fidCardAdapter;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarteFedeltaFragment extends Fragment {
    String httpToken;
    public RecyclerView.Adapter fRVA;
    public ArrayList <FidCard> fidCards = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.httpToken = ((MyApplication)this.getActivity().getApplication()).getHttpToken();
        View view = inflater.inflate(R.layout.fragment_fidcard, container, false);
        setView(view);
        return view;
    }

    private void setView(final View view) {
        getCard(view);

        final ImageView addCard = view.findViewById(R.id.fidcard_img_addcard);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCardDialog addCardDialog= new AddCardDialog(view.getContext(), httpToken, CarteFedeltaFragment.this);
                addCardDialog.show();
            }
        });
    }

    private void getCard(final View view) {
        new HttpJsonRequest(getContext(), "/api/v1/get_user_fidcards", Request.Method.GET, httpToken, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseCard(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setRecyclerView(view);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }).run();

    }

    private void parseCard(JSONObject response) throws JSONException {
        JSONArray results = response.getJSONArray("results");
        for (int i = 0; i < results.length(); i++){
            JSONObject currentItem = results.getJSONObject(i);

           int id, chain;

           id = currentItem.getInt("id");
           chain = currentItem.getInt("chain");

           fidCards.add(new FidCard(id, chain));
        }
    }

    private void setRecyclerView(View view) {
        RecyclerView fRV;
        RecyclerView.LayoutManager fRVLM;

        fRV = view.findViewById(R.id.fidcard_rv);
        fRVLM = new LinearLayoutManager(getContext());
        fRVA = new fidCardAdapter(this, fidCards);

        fRV.setLayoutManager(fRVLM);
        fRV.setAdapter(fRVA);
    }
}
