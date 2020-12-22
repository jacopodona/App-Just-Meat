package com.example.justmeat.homepage.adapter;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justmeat.R;
import com.example.justmeat.homepage.CarteFedeltaFragment;
import com.example.justmeat.homepage.FidCard;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fidCardAdapter extends RecyclerView.Adapter<fidCardAdapter.FidCardViewHolder> {
    ArrayList<FidCard> fidCards;
    CarteFedeltaFragment carteFedeltaFragment;

    public fidCardAdapter(CarteFedeltaFragment carteFedeltaFragment, ArrayList<FidCard> fidCards) {
        this.carteFedeltaFragment = carteFedeltaFragment;
        this.fidCards = fidCards;
    }

    @NonNull
    @Override
    public FidCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FidCardViewHolder fVH;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fidcard, parent, false);
        fVH = new FidCardViewHolder(v);
        return fVH;
    }

    @Override
    public void onBindViewHolder(@NonNull FidCardViewHolder holder, final int position) {
        final FidCard currentItem = fidCards.get(position);
        Glide.with(carteFedeltaFragment.getActivity())
                .load("http://just-feet.herokuapp.com/images/lc_" + currentItem.getChain() + ".jpg")
                .override(558,361)
                .into(holder.imgCard);
        holder.imgCard.setAdjustViewBounds(true);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog infoCardDialog = new Dialog(carteFedeltaFragment.getContext());
                infoCardDialog.setContentView(R.layout.dialog_infocard);

                ImageView infoCardImg = infoCardDialog.findViewById(R.id.fidcard_img_infocard);
                Glide.with(carteFedeltaFragment.getActivity())
                        .load("http://just-feet.herokuapp.com/images/lc_" + currentItem.getChain() + ".jpg")
                        .override(558,361)
                        .into(infoCardImg);
                infoCardImg.setAdjustViewBounds(true);

                TextView infoCardCode = infoCardDialog.findViewById(R.id.fidcard_txt_infocode);
                infoCardCode.setText(""+currentItem.getId());

                ImageView barcode = infoCardDialog.findViewById(R.id.fidcard_img_barcodecard);
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(""+currentItem.getId(), BarcodeFormat.CODE_128, 400, 120);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    barcode.setImageBitmap(bitmap);
                } catch (Exception e){
                    e.printStackTrace();
                }

                ImageView delete = infoCardDialog.findViewById(R.id.fidcard_btn_deletecard);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ulteriore richiesta di conferma
                        JSONObject body = new JSONObject();
                        try {
                            body.put("fidcard_id", currentItem.getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String httpToken = ((MyApplication) carteFedeltaFragment.getActivity().getApplication()).getHttpToken();
                        new HttpJsonRequest(carteFedeltaFragment.getContext(), "/api/v1/del_fidcard", Request.Method.POST, body, httpToken, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                infoCardDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }).run();
                        carteFedeltaFragment.fidCards.remove(currentItem);
                        notifyItemRemoved(position);
                    }
                });

                infoCardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                infoCardDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                infoCardDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fidCards.size();
    }

    public class FidCardViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCard;
        CardView cardView;
        public FidCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCard = itemView.findViewById(R.id.fidcard_img_card);
            cardView = itemView.findViewById(R.id.fidcard_card);
        }
    }
}
