package com.example.justmeat.checkout;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justmeat.R;
import com.example.justmeat.homepage.HomepageActivity;
import com.example.justmeat.homepage.User;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.HttpJsonRequest;
import com.example.justmeat.utilities.MyApplication;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    public double subtotal = 0;
    public double tot;
    ArrayList<ProductItem> productList;
    TextInputLayout code_couponDialog;
    TextView tot_txt;
    ArrayList<Coupon> couponArrayList;
    CouponAdapter couponAdapter;
    String pickup_date;
    String order_favourite;

    int idSupermercato;
    String nomeSpkmt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDarkColor));
        setContentView(R.layout.activity_checkout);
        Intent intent = getIntent();
        pickup_date = intent.getStringExtra("pickup_date");
        idSupermercato = getIntent().getIntExtra("idSupermercato",4);
        nomeSpkmt = getIntent().getStringExtra("nomeSupermercato");
        couponArrayList = new ArrayList<>();
        productList = ((MyApplication)this.getApplication()).getCarrelloListProduct();
        TextView titolo = findViewById(R.id.checkout_txt_nomenegozio);
        titolo.setText(nomeSpkmt);

        setConfirmLayout();
        setProdList();
        setCheckoutLayout();
        setCouponLayout();
    }

    private void setCouponLayout() {
        RecyclerView rv = findViewById(R.id.checkout_rv_coupons);
        RecyclerView.LayoutManager rvLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        couponAdapter = new CouponAdapter(this);

        rv.setAdapter(couponAdapter);
        rv.setLayoutManager(rvLM);
    }

    private void setCheckoutLayout() {
        ImageView backButton = findViewById(R.id.checkout_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView homeButton = findViewById(R.id.checkout_btn_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= ((MyApplication)getApplication()).getUtente();

                JSONObject j= new JSONObject();
                JSONObject userJson= new JSONObject();
                try {
                    userJson.put("id", user.getId())
                            .put("name", user.getName())
                            .put("last_name", user.getLast_name())
                            .put("mail", user.getMail());
                    j.put("user", userJson);
                    j.put("token", ((MyApplication)getApplication()).getHttpToken());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("aasdasd", j.toString());

                Intent intent = new Intent(CheckoutActivity.this , HomepageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                intent.putExtra("user", j.toString());
                startActivity(intent);
                finish();
            }
        });

        ImageView divider = findViewById(R.id.checkout_img_divider);
        divider.setEnabled(false);

        tot_txt = findViewById(R.id.checkout_txt_value_totale);

        final ImageView addCoupon = findViewById(R.id.checkout_btn_add_coupon);
        addCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog couponDialog = new Dialog(v.getContext());
                couponDialog.setContentView(R.layout.dialog_coupon);
                code_couponDialog = couponDialog.findViewById(R.id.checkout_intxt_dialogcoupon);
                code_couponDialog.getEditText().setText("");
                ImageView scanqr_btn = couponDialog.findViewById(R.id.checkout_btn_scanqr_dialogcoupon);
                scanqr_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scanQR();
                    }
                });
                couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button confirm_btn = couponDialog.findViewById(R.id.checkout_btn_positive_dialogcoupon);
                confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkCoupon(code_couponDialog.getEditText(), addCoupon, couponDialog);
                    }
                });
                couponDialog.show();
            }
        });

        for(ProductItem currentPruduct : productList){
            subtotal += (currentPruduct.qt*currentPruduct.getPrezzo())*(1-currentPruduct.getDiscount());
        }

        TextView subtotaltxt = findViewById(R.id.checkout_txt_value_subtot);
        subtotaltxt.setText(String.format("%.2f", subtotal)+" €");
        tot = subtotal;
        tot_txt.setText(String.format("%.2f",tot) + " €");
    }

    private void checkCoupon(final EditText editText, final ImageView addCoupon, final Dialog couponDialog) {
        final String toString = editText.getText().toString();
        new HttpJsonRequest(this, "/api/v1/get_coupon/" + toString, Request.Method.GET, ((MyApplication) getApplication()).getHttpToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Coupon coupon = new Coupon(toString, response.getDouble("percentage"), response.getInt("code"));
                    if(coupon.isAvailable()){
                        editText.setError(null);
                        couponArrayList.add(coupon);
                        addCoupon.setVisibility(View.INVISIBLE);
                        addCoupon.setEnabled(false);
                        tot = subtotal;
                        couponAdapter.notifyDataSetChanged();
                        couponDialog.dismiss();
                    } else {
                        editText.setError("Il buono non è disponibile");
                    }
                }catch (JSONException e){
                    editText.setError("Il buono non è disponibile");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }).run();
        return ;
    }

    private void scanQR() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Inquadra il QR code");
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setCameraId(0);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                code_couponDialog.getEditText().setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setProdList() {
        RecyclerView prodListRV = findViewById(R.id.checkout_rv_product);
        RecyclerView.LayoutManager prodListRVLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        RecyclerView.Adapter prodListRVA = new CheckoutProductAdapter(productList, this);

        prodListRV.setAdapter(prodListRVA);
        prodListRV.setLayoutManager(prodListRVLM);
    }

    private void setConfirmLayout() {
        final Activity activity = this;
        final BottomSheetBehavior confirmDialog = BottomSheetBehavior.from(findViewById(R.id.checkout_bsl_confirm));
        confirmDialog.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View view, int i) {
                TextView swipe = view.findViewById(R.id.checkout_txt_swipe);
                ImageView swipeImg = view.findViewById(R.id.checkout_img_swipe);
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        swipe.setVisibility(View.VISIBLE);
                        swipeImg.setVisibility(View.VISIBLE);
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        swipe.setVisibility(View.INVISIBLE);
                        swipeImg.setVisibility(View.INVISIBLE);
                        confirmDialog.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                            @Override
                            public void onStateChanged(@NonNull View view, int i) {
                                if(i == BottomSheetBehavior.STATE_DRAGGING){
                                    confirmDialog.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                            }
                            @Override
                            public void onSlide(@NonNull View view, float v) {
                            }
                        });
                        final Dialog favDialog = new Dialog(view.getContext());
                        favDialog.setContentView(R.layout.dialog_preferiti);
                        Button confirm_btn = favDialog.findViewById(R.id.carrello_btn_positive_dialogpref);
                        Button dismiss_btn = favDialog.findViewById(R.id.carrello_btn_neutral_dialogpref);
                        final TextInputLayout textInputLayout = favDialog.findViewById(R.id.carrello_txtin_dialogpref);
                        confirm_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String string = textInputLayout.getEditText().getText().toString();
                                order_favourite = string;
                                favDialog.dismiss();
                            }
                        });
                        dismiss_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                favDialog.dismiss();
                            }
                        });
                        favDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                SendOrder();
                                User user= ((MyApplication)getApplication()).getUtente();

                                JSONObject j= new JSONObject();
                                JSONObject userJson= new JSONObject();
                                try {
                                    userJson.put("id", user.getId())
                                            .put("name", user.getName())
                                            .put("last_name", user.getLast_name())
                                            .put("mail", user.getMail());
                                    j.put("user", userJson);
                                    j.put("token", ((MyApplication)getApplication()).getHttpToken());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.e("aasdasd", j.toString());

                                Intent intent = new Intent(activity , HomepageActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                intent.putExtra("user", j.toString());
                                startActivity(intent);
                                finish();
                            }
                        });
                        favDialog.show();



                        Button goHomeButton = view.findViewById(R.id.checkout_btn_swipebackhome);
                        goHomeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                User user= ((MyApplication)getApplication()).getUtente();

                                JSONObject j= new JSONObject();
                                JSONObject userJson= new JSONObject();
                                try {
                                    userJson.put("id", user.getId())
                                            .put("name", user.getName())
                                            .put("last_name", user.getLast_name())
                                            .put("mail", user.getMail());
                                    j.put("user", userJson);
                                    j.put("token", ((MyApplication)getApplication()).getHttpToken());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.e("aasdasd", j.toString());



                                Intent intent = new Intent(activity , HomepageActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                intent.putExtra("user", j.toString());
                                startActivity(intent);
                                finish();
                            }
                        });

                        break;
                    }

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });
    }

    private void SendOrder() {
        try{
            JSONObject body = new JSONObject();
            body.put("pickup_time", pickup_date);
            body.put("is_favourite", order_favourite);
            body.put("supermarket_id", idSupermercato);
            JSONArray carrello = new JSONArray();
            for(ProductItem productItem : productList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fk_product", productItem.getId());
                jsonObject.put("fk_weight", productItem.getFk_weight());
                jsonObject.put("quantity", productItem.getQt());
                carrello.put(jsonObject);
            }
            body.put("shopping_cart", carrello);
            if(couponArrayList.isEmpty()){
                body.put("coupons", JSONObject.NULL);
            } else{
                body.put("coupons", couponArrayList.get(0).code);
            }
            System.out.println("body: "+body);

            new HttpJsonRequest(getBaseContext(), "/api/v1/add_order", Request.Method.POST, body, ((MyApplication) getApplication()).getHttpToken(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.toString());
                }
            }).run();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}