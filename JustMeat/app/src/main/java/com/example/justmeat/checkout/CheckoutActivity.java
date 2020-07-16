package com.example.justmeat.checkout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justmeat.R;
import com.example.justmeat.marketview.ProductItem;
import com.example.justmeat.utilities.MyApplication;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    public double subtotal = 0;
    ArrayList<ProductItem> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        productList = ((MyApplication)this.getApplication()).getCarrelloListProduct();

        setConfirmLayout();
        setProdList();
        setCheckoutLayout();
    }

    private void setCheckoutLayout() {
        ImageView divider = findViewById(R.id.checkout_img_divider);
        divider.setEnabled(false);

        ImageView addCoupon = findViewById(R.id.checkout_btn_add_coupon);
        addCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog couponDialog = new Dialog(v.getContext());
                couponDialog.setContentView(R.layout.dialog_coupon);
                couponDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button confirm_btn = couponDialog.findViewById(R.id.checkout_btn_positive_dialogcoupon);
                confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        couponDialog.dismiss();
                    }
                });
                couponDialog.show();
            }
        });

        for(ProductItem currentPrudect : productList){
            subtotal += currentPrudect.qt*currentPrudect.getPrezzo();
        }

        TextView subtotaltxt = findViewById(R.id.checkout_txt_value_subtot);
        subtotaltxt.setText(String.format("%.2f", subtotal)+" €");
    }
    private void setProdList() {
        RecyclerView prodListRV = findViewById(R.id.checkout_rv_product);
        RecyclerView.LayoutManager prodListRVLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false );
        RecyclerView.Adapter prodListRVA = new CheckoutProductAdapter(productList, this);

        prodListRV.setAdapter(prodListRVA);
        prodListRV.setLayoutManager(prodListRVLM);
    }

    private void setConfirmLayout() {
        final BottomSheetBehavior confirmDialog = BottomSheetBehavior.from(findViewById(R.id.checkout_bsl_confirm));
        confirmDialog.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View view, int i) {
                TextView swipe = view.findViewById(R.id.checkout_txt_swipe);
                ImageView swipeImg = view.findViewById(R.id.checkout_img_swipe);
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        swipe.setText(R.string.swipe);
                        swipeImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                        break;
                    }
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //la scelta migliore sarebbe disabilitare la possibilità di richiudere lo sheet
                        swipe.setText("");
                        swipeImg.setImageDrawable(null);
                        break;
                    }

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });
    }
}
